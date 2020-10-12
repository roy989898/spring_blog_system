package com.example.demo.service

import com.example.demo.po.Comment
import javax.transaction.Transactional

interface CommentService {
    @Transactional
    fun addComment(comment: Comment): Comment
}