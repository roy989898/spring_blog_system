package com.example.demo.po

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_comment")
data class Comment(
        @Id
        @GeneratedValue
        var id: Long?,
        var nickname: String,
        var email: String,
        var content: String,
        var avatar: String,
        @Temporal(TemporalType.TIMESTAMP)
        var createTime: Date

)