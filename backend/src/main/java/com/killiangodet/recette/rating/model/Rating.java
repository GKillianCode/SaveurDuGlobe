package com.killiangodet.recette.rating.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"note", "name"})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer id;

    @Column(name = "rating_note")
    private Integer note;

    @Column(name = "rating_name")
    private String name;

    public Rating(Integer note, String name) {
        this.note = note;
        this.name = name;
    }

}
