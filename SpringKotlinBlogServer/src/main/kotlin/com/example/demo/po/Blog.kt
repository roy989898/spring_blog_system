package com.example.demo.po

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_blog")
data class Blog(
        @Id
        @GeneratedValue
        var id: Long?,
        var title: String,
        var content: String,
        var firstPicture: String?,
        var flag: String,
        var vies: Int,
        var appreciation: Boolean,
        var shareStatement: Boolean,
        var commentabled: Boolean,
        var published: Boolean,
        var recommend: Boolean,
        @Temporal(TemporalType.TIMESTAMP)
        var createTime: Date,
        @Temporal(TemporalType.TIMESTAMP)
        var updateTime: Date,


//        TODO can delete the blog without delete the type???
        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        var type: Type?,

        @OneToMany(mappedBy = "blog", cascade = [CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH])
        var comments: List<Comment>,

        @ManyToOne(cascade = [])
        var user: User?,
        @ManyToMany
        var tags: List<Tag>
)