package com.example.demo.po

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_user")
data class User(
        @Id
        @GeneratedValue
        var id: Long?,
        var nickname: String?,
        var username: String,
        var password: String,
        var email: String?,
        var phone: String?,
        @Lob
        @Column(columnDefinition = "TEXT")
        var aboutMe: String?,
        @Lob
        @Column(columnDefinition = "TEXT")
        var avatar: String?,
        @Lob
        @Column(columnDefinition = "TEXT")
        var picture: String?,
        var type: Int?,
        @Temporal(TemporalType.TIMESTAMP)
        var createTime: Date,
        @Temporal(TemporalType.TIMESTAMP)
        var updateTime: Date,

        @OneToMany(mappedBy = "user", cascade = [CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH])
        var blogs: List<Blog>

) {
    fun copyRightString(): String {
        val startYear = 2019
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val cr = "© $startYear-$currentYear $nickname All Rights Reserved"
        return cr

    }
}