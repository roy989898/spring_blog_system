package com.example.demo.service

import com.example.demo.dao.CommentRepository
import com.example.demo.po.Comment
import org.springframework.stereotype.Service


@Service
class CommentServiceImp(val commentRepository: CommentRepository) : CommentService {
    override fun addComment(comment: Comment): Comment {
        return commentRepository.save(comment)
    }

    override fun listOnlyParentComments(blogId: Long): List<Comment> {
        return commentRepository.getCommentsByParentCommentIsNullAndBlog_IdOrderByCreateTimeDesc(blogId)

    }
}