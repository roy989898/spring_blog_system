package com.example.demo.po

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_tag")
data class User(
        @Id
        @GeneratedValue
        var id: Long?,
        var nickname: String,
        var username: String,
        var password: String,
        var email: String,
        var avatar: String,
        var type: Int,
        @Temporal(TemporalType.TIMESTAMP)
        var createTime: Date,
        @Temporal(TemporalType.TIMESTAMP)
        var updateTime: Date

)