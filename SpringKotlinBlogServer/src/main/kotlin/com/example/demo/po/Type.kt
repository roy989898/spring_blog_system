package com.example.demo.po

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "t_type")
data class Type(
        @Id
        @GeneratedValue
        var id: Long?,
        var name: String

)