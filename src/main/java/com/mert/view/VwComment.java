package com.mert.view;

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

public class VwComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id için otomatik artan HB sequence oluşturur
    Long id;
    Long userid;
    String comment;
}
