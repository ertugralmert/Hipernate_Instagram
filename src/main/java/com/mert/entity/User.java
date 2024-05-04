package com.mert.entity;

import com.mert.utility.enums.State;
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
@Table(name = "tbluser")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Id için otomatik artan HB sequence oluşturur
  Long id;
//    /**
//     * Kullanılan DB de hibernate için bir sequnce oluşturur. Ve id nin bu sequence ile çalışması sağlanır.
//     * name -> Hibernate için tanımlanan isimdir. GeneretedValue için seçici isimdir.
//     */
//
//    @Id
//            @SequenceGenerator(name = "sq_user_id",sequenceName = "sq_userid", initialValue = 1000, allocationSize = 20)
//    Long id2;

    @Column (name="user_name", unique = true,nullable = false,length = 64, updatable = false)
    String username;
    @Column(nullable = false,length = 128)
    String password;
    @Column(length = 64)
    String email;
    @Column(length = 20)
    String phone;
    Boolean isActive;
    /**
     * Enum'lar şu şekilde çalışır:
     * String -> Integer
     * Active -> 1.. bu şekilde çalışır.
     * Eğer enum için bir kullanım tipi belirlemezsek Integer olarak DB'ye aktarır.
     */
    @Enumerated(EnumType.STRING)
    State state;
}
