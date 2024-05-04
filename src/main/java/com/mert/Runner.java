package com.mert;

import com.mert.entity.User;
import com.mert.utility.enums.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.text.html.parser.Entity;

public class Runner {
    public static void main(String[] args) {

      User  user = User.builder()
                .username("Bengü")
                .password("123456")
                .email("mertugral@gmail.com")
                .isActive(true)
                .phone("0555 555 55 55")
                .state(State.ACTIVE)
                .build();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRM");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        /**
         * persist -> ekleme ve güncelleme
         * id null ise ekleme işlemi yapar
         * id null değil ise var olan kayıt güncellenir. Ancak ilgili id
         * daha önce kullanılmamış ise ekleme yapar
         *
         * remove(Entity) -> silme işlemi yapar
         */
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

}
