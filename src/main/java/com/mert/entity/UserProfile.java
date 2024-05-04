package com.mert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //
@AllArgsConstructor //
@NoArgsConstructor // parametreli constructor ların tümü
@Builder //default constructor
@Entity
@Table(name = "tbluserprofile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id için otomatik artan HB sequence oluşturur
    Long id;
    Long userid;
    String name;
    String surname;
    String address;
    String image;
    String avatar;
    String about;
    String website;
    Boolean ishidden;


}
