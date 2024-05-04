package com.mert.criteriaQuery;

import com.mert.entity.Comment;
import com.mert.entity.Post;
import com.mert.view.VwComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CriteriaOrnekleri {
    private EntityManagerFactory emf;
    private  EntityManager em;
    private CriteriaBuilder criteriaBuilder;
    public CriteriaOrnekleri() {
        emf = Persistence.createEntityManagerFactory("CRM");
        em = emf.createEntityManager();
        criteriaBuilder = em.getCriteriaBuilder();
    }
    /**
     * Select * from tblcomment
     */
    public List<Comment> findAll() {
        /**
         * mutlaka bir entity sınıfı verilir. Burada reflection kullanılarak sınıf analiz edilir.
         */
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);

        /**
         * Select işlemi için seçilecek alanları belirleyebilmek önemlidir.
         * Elle yazarken alanları biz belirleriz. Ancak bu işlem belirsiz sınıflar
         * üzerinden yapılırken Generic Type olarak alınır ve Reflection ile çözülür.
         */
        Root<Comment> root = criteriaQuery.from(Comment.class);
        /**
         * select * from
         */
        criteriaQuery.select(root);
        /**
         * oluşturduğumuz sorguyu çalıştırıyoruz
         */
        return em.createQuery(criteriaQuery).getResultList();
    }

        /**
         * select * from tblcomment -> ilgili entity içinde ki tüm alanlar gelir.
         * Ancak bazen tek bir alan almak ihtiyacı olacaktır. böyle durumlarda
         * select comment from tblcomment
         */

        public List<String> selectOneColumn(){
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Comment> root = criteriaQuery.from(Comment.class);
            criteriaQuery.select(root.get("comment")); //Comment içindeki String comment alıyoruz.
                // List<String> result = em.createQuery(criteriaQuery).getResultList();
//        return result;
        return em.createQuery(criteriaQuery).getResultList();
        }

    /**
     * select comment from tblcomment where postid = ? bunu yazmak istiyoruz
     */
    public List<String> selectOneColumnByPostId(Long postId) {
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        // ben hangi tablo ihtiyacım var veya hangi alanı ihtiyacım varsak onu yazıyoruz -> root budur
        Root<Comment> root = criteriaQuery.from(Comment.class);
        //select comment
        criteriaQuery.select(root.get("comment"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("postid"), postId));
        return em.createQuery(criteriaQuery).getResultList();
    }
    /**
     * select * from tblcomment where id = ?
     * Bu sorgu ya tek bir değer döner ya da boş gönder
     */
    public Optional<Comment> findById(Long id) {
        CriteriaQuery<Comment>  criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        // iki sonuç alıyoruz ya var ya yok. Var olmayan bir nesneyi çekmeye çalışırsak patlar. O yüzden
        // değer ekledik.
        Comment result = em.createQuery(criteriaQuery).getSingleResult();
   if (result != null) {
       return Optional.of(result);
   }else {
       return Optional.empty();
   }
    }
    /**
     * select userid, comment from tblcomment where postid = ?
     * List döner.
     */
    public List<Object[]> selectManyColumn (Long postid){
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        // aşağıdaki opsiyon 1 olsun
        //criteriaQuery.select(criteriaBuilder.array(root.get("userid"),root.get("comment")));
        // opsiyon 2 olsun
        Path<Long> userid = root.get("userid");
        Path<String> comment = root.get("comment");
        criteriaQuery.select(criteriaBuilder.array(userid,comment));
        criteriaQuery.where(criteriaBuilder.equal(root.get("postid"), postid));
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * select * from tblcomment where postid= ? and userid>60 and comment like '%ankara%'
     */
    public List<Comment> findByAllByPostIdAndUserIdGetAndCommentLike(Long posId,Long userId,String comment) {
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.select(root);
        Predicate predicatePostId = criteriaBuilder.equal(root.get("postid"), posId);
        Predicate predicateUserId = criteriaBuilder.greaterThan(root.get("userid"), 60);
        Predicate predicateComment = criteriaBuilder.like(root.get("comment"), "%"+comment+"%");
        criteriaQuery.where(criteriaBuilder.and(predicatePostId,predicateUserId,predicateComment));
        return em.createQuery(criteriaQuery).getResultList();
    }
    /**
     * Native Query -> Hibernate üzerinden direkt SQL komutlarını çalıştırabildiğimiz yapıdır.
     */
    public List<Comment> findAllNativeSQL(){
        List<Comment> result = em.createNativeQuery("select * from tblcomment", Comment.class).getResultList();
        return result;
    }
    /**
     * select comment from tblcomment
     */
    public List<String> getOneColumnNativeSQL(){
        return em.createNativeQuery("select comment from tblcomment",String.class).getResultList();
    }
    /**
     * select userid,comment from tblcomment
     */

    // bir tane view package açtık. içine görüntelemek istediğimiz değişkenleri verdik.
    public  List<VwComment> getViewNativeSQL(){
        return em.createNativeQuery("select id,userid,comment from tblcomment", VwComment.class).getResultList();
    }
    public List<Post> findAllNamedQuery(){
        return em.createNamedQuery("Post.findAll", Post.class).getResultList();
    }
    public BigDecimal countPostsize (){
        return em.createNamedQuery("Post.countAll", BigDecimal.class).getSingleResult();
    }
    public List<Post> findAllByUserId(Long userId){
        TypedQuery<Post> typedQuery = em.createNamedQuery("Post.findAllByUserId", Post.class);
        typedQuery.setParameter("userid", userId);
        return typedQuery.getResultList();
    }
}

