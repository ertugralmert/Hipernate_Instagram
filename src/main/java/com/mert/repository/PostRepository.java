package com.mert.repository;

import com.mert.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class PostRepository {
    private EntityManagerFactory emf;
    private EntityManager em;
    private CriteriaBuilder criteriaBuilder;
    public PostRepository() {
        emf = Persistence.createEntityManagerFactory("CRM");
        em = emf.createEntityManager();
        criteriaBuilder = em.getCriteriaBuilder();
    }
    public void openSession(){
        this.em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    public void closeSession(){
        em.getTransaction().commit();
        em.close();
    }
    public void rollBack(){
        em.getTransaction().rollback();
        em.close();
    }

    public Post save(Post post){
        openSession();
        em.persist(post);
        closeSession();
        return post;
    }

    public List<Post> findAll(){
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root);
        return em.createQuery(criteriaQuery).getResultList();
    }

}
