package com.is.smartlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "energy")
public class Energy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Float price;

    private String date;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

}
