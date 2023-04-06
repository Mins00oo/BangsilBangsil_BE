package com.bangsil.bangsil.comment.infrastructure;

import com.bangsil.bangsil.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository <Comment, Long>{
}
