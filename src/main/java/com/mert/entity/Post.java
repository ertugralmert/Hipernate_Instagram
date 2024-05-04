package com.mert.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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