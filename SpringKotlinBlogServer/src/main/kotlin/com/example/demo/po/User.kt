package com.example.demo.po

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_user")
class User(
        @Id
        @GeneratedValue
        var id: Long?,
        var nickname: String?,
        private var username: String,
        private var password: String,

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

) : UserDetails {
    fun copyRightString(): String {
        val startYear = 2019
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val cr = "© $startYear-$currentYear $nickname All Rights Reserved"
        return cr

    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val result = mutableListOf<SimpleGrantedAuthority>(
        )
        result.add(SimpleGrantedAuthority("ROLE_" + "USER"))
        return result
    }

    override fun getPassword(): String {
        return password
    }


    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return username
    }


    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}