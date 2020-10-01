package com.example.demo.po

import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
@Table(name = "t_type")
data class Type(
        @Id
        @GeneratedValue
        var id: Long?,
        var name: String,
        @OneToMany(mappedBy = "type", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        var blogs: List<Blog>


) {


}