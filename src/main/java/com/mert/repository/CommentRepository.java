package com.mert.repository;

import com.mert.entity.Comment;
import com.mert.utility.Icrud;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class CommentRepository implements Icrud<Comment,Long> {
    //sürekli aynı kod tekrarını önlemek için EntityManagerFactory için bir method yazalım
    private final EntityManagerFactory emf;
    private EntityManager em;

    public CommentRepository() {
        emf = Persistence.createEntityManagerFactory("CRM");
    }
    private void openSession() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    private void closeSession() {
        em.getTransaction().commit();
        em.close();
    }

    private void Rollback() {
        em.getTransaction().rollback();
        em.close();
    }


    @Override
    public Comment save(Comment entity) {
//        // bana adı CRM olan bir bağlantı aç ve entityleri manage edebileyim
//        //bağlantı açar
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRM");
//        // bağlantıları yönetir
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(entity); // kaydetme , id verilme işlemi burada oluyor
//        em.getTransaction().commit(); // id bilgisi entity içine eklenir
//        //transaction işlemleri takip eder, her şey yolundaysa commit yapıp kalıcı olmasını sağlanır.
//        em.close();
//        emf.close();
        openSession();
        em.persist(entity);
        closeSession();
        return entity;
    }

    @Override
    public Iterable<Comment> saveAll(Iterable<Comment> entities) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRM");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        entities.forEach(entity->em.persist(entity));
//        em.getTransaction().commit();
//        em.close();
//        emf.close();
        try {
            openSession();
            entities.forEach(em::persist);  //entities.forEach(entity->em.persist(entity));
            closeSession();

        }catch (Exception e){
            Rollback();
        }
        return entities;
    }

    @Override
    public Optional<Comment> findyById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public List<Comment> findByColumnAndValue(String columnName, Object value) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<Comment> findAllByEntity(Comment entity) {
        return null;
    }
}
