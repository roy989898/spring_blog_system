package com.example.demo.form

import com.example.demo.po.Comment
import java.util.*

data class CommentInputForm(val message: String?, val name: String?,val email: String?,val blogID:Long?) {


    fun toComment(): Comment {
        return Comment(null,name?:"",email?:"",message?:"","", Date(),null, emptyList(),null)
    }


}