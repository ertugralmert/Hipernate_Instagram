package com.mert.entity;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Named Query
 * Önbellekleme yapabilirler. Bu nedenle aynı sorgu
 * tekrar çağrıldığında veriler önbellikten çekilir.
 * Entity üzerine yazılır.
 * Named query yazmak için 3 farklı dil kullanılabilir.
 * HQL -> Hibernate Query Language
 * JPQL -> Jakarta Persistence Query Language
 * Native SQL
 * -------- Sorgu Yazma Şekilleri-----------
 * NativeSQL   -> select * from tblpost
 * JPQL        -> select p from Post p (Post p-> alians kullanılır. yani post ksaca p ile gösterilir.
 * HQL         -> from Post
 * --------------
 * Named Query ler ilgili sınıfın üzerine bir anatasyon yardımı ile yazılırlar.
 * Eğer tek bir Query kullanılacak ise tek tek yazılabilir. birden fazla sorgu yazılacak ise
 * array şeklinde query ler eklenemebilir.
 * Integer[] {12,123,334}
 */
@NamedQueries({
        /**
         * NamedQuery lere isimlemdirme yaparken şu formatta yazmak uygundur [Entity_Name].[Query_Name]
         */
        @NamedQuery(name = "Post.findAll", query = "select p from Post p"),
        @NamedQuery(name = "Post.countAll", query = "select count(p) from Post p"),
        /**
         * Dikkat!!! Eğer NamedQuery içerisine bir değer girmemiz gerekiyor ise bunu
         * eklemek için değişken tanımlamalıyız. NamedQuery içerisine değişken tanımlamak
         * için ":[DEĞİŞKEN_ADI]" şeklinde kullanmalıyız.
         */
        @NamedQuery( name = "Post.findAllByUserId", query = "select p from Post p where p.userid = :userid")
})
@Data //get set vs.içinde barındırıyor
@AllArgsConstructor //parametreli constructorların tümü
@NoArgsConstructor //default constructor
@Builder
@Entity
@Table(name = "tblpost")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id için otomatik artan bir HB sequence oluşturur
    Long id;
    Long userid;
    Long shareddate;
    String comment;
    String imageurl;
    String location;
    Integer likes;
    Integer commentcount;



}