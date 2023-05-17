package com.killiangodet.recette.membership.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "membership")
@Getter
@NoArgsConstructor
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mbp_id")
    private Integer id;

    @Column(name = "mbp_name")
    private String name;
}
