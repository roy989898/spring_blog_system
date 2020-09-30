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
        var firstPicture: String,
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

        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        var type: Type?,

        @OneToMany(mappedBy = "blog")
        var comments: List<Comment>,

        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        var user: User,
        @ManyToMany
        var tags: List<Tag>
)