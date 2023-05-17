package com.killiangodet.recette.comment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmt_id")
    private Integer id;

    @Column(name = "cmt_rating")
    private Integer rating;

    @Column(name = "cmt_description")
    private String description;

    @Column(name = "cmt_userId")
    private Integer userId;

    @Column(name = "cmt_recipeId")
    private Integer recipeId;
}
