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
@Table(name = "tblfollow")
public class Follow {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long followerid;
    Long followingid;
    Long date;
    Integer state;
}
