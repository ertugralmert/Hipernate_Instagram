package com.mert.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Getter
public class Repository<T, ID> implements Icrud<T, ID> {

    private final EntityManagerFactory emf;
    private EntityManager em;
    private T t;
    T result;

    public Repository(T entity) {
        emf = Persistence.createEntityManagerFactory("CRM");
        em = emf.createEntityManager();
        this.t = entity;
    }

    private void openSession() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    private void closeSession() {
        em.getTransaction().commit();
        em.close();
    }

    private void rollback() {
        em.getTransaction().rollback();
        em.close();
    }

    @Override
    public T save(T entity) {
        openSession();
        em.persist(entity);
        closeSession();
        return entity;
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
        try {
            openSession();
            entities.forEach(em::persist);
            closeSession();
        } catch (Exception e) {
            rollback();
        }
        return entities;
    }

    /**
     * select * from tbl?? where id = ?
     *
     * @param id
     * @return bu sorguda transaction'a gerek yok
     */
    @Override
    public Optional<T> findyById(ID id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass()); //cast etmemiz lazım
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); //select * from
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id)); //where id = ?

        try {
            //burada sorgumuz tek bir sonuç dönecek. Hiç dönmezse ilk bulduğu sonucu dönecek.
            result = em.createQuery(criteriaQuery).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException exception) {
            return Optional.empty(); // boş döneceğiz
        }

    }

    @Override
    public boolean existsById(ID id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass()); //cast etmemiz lazım
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); //select * from
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id)); //where id = ?
        try {
            result = em.createQuery(criteriaQuery).getSingleResult();
            return true;
        } catch (NoResultException exception) {
            return false;
        }
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass()); //cast etmemiz lazım
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); //select * from
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<T> findByColumnAndValue(String columnName, Object value) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass()); //cast etmemiz lazım
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get(columnName), value));
        return em.createQuery(criteriaQuery).getResultList();
    }


    @Override
    public void deleteById(ID id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass()); //cast etmemiz lazım
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); //select * from
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id)); //where id = ?
        T removeElement;

        try {
            removeElement = em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            removeElement = null;
        }
        try {
            openSession();
            em.remove(removeElement);
            closeSession();
        } catch (Exception e) {
            if (em.isOpen())
                rollback();
        }

    }

    /**
     * reflaction ile sınıf parçalanır.  Java Reflection
     * Long userid -> if(userid!=null) srguya dahil et -> userid,value
     *
     * @param entity
     * @return
     */
    @Override
    public List<T> findAllByEntity(T entity) {
        List<T> result;
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass()); //cast etmemiz lazım
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); //select * from
        /**
         * where
         * içeriği null olmayan değişkenlerin where içerisine predicate olarak eklenmesini sağlamak.
         */
        List<Predicate> predicateList = new ArrayList<>();
        for (int i = 1; i < fields.length; i++) { //entity içinden aldığımız alanları dönüyoruz.
            /**
             * Dikkat!!! bir field erişim belirteçleri ile erişime kapalı olabilir.
             * Bu nedenle öncelikle bunları açmak gerekir.
             */
            fields[i].setAccessible(true);
            try {
                /**
                 * Erişime açtığımız field'ların adlarını ve değerlerini okuyoruz.
                 */
                String column = fields[i].getName();
                Object value = fields[i].get(entity);
                if (value != null) {
                    if (value instanceof String) {
                        predicateList.add(criteriaBuilder.like(root.get(column), "%" + value + "%"));
                    }
                } else {
                    predicateList.add(criteriaBuilder.equal(root.get(column), value));
                }
            } catch (Exception exception) {
                System.out.println("Hata oluştu: " + exception);

            }
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[]{}));
        result = em.createQuery(criteriaQuery).getResultList();
        return result;
    }
}
