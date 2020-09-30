package com.example.demo.po

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "t_tag")
data class Tag(
        @Id
        @GeneratedValue
        var id: Long?,
        var name: String

)