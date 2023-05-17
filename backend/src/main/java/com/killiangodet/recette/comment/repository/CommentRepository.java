package com.killiangodet.recette.comment.repository;

import com.killiangodet.recette.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
