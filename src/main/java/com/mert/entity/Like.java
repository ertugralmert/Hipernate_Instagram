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
@Table(name = "tbllike")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id için otomatik artan bir HB sequence oluşturur
    Long id;
    Long postid;
    Long userid;
    Long date;
    boolean repeated;
    boolean islike;
    /**
     * repeated = true ve islike = false ise;
     * önceden beğenip, beğenisini geri almış
     *
     * repeated = false ve islike = false ise;
     * hiç like yapmamış
     */
}
