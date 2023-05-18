package com.killiangodet.recette.comment.model;

import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.model.User;
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

    @ManyToOne
    @JoinColumn(name = "usr_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "rcp_id", insertable = false, updatable = false)
    private Recipe recipe;
}
