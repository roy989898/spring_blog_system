package com.example.demo.po

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_tag")
data class Tag(
        @Id
        @GeneratedValue
        var id: Long?,
        var name: String,
        @ManyToMany(mappedBy = "tags",cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        var blogs: List<Blog>

)