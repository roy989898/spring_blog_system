package com.example.demo.po

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import kotlin.collections.ArrayList

@Entity
@Table(name = "t_type")
data class Type(
        @Id
        @GeneratedValue
        var id: Long?,
        @field:NotBlank(message = "could not empty")
        var name: String,
        @OneToMany(mappedBy = "type", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        var blogs: List<Blog>?


) {


        fun removeAllRelation(): Type {
                this.blogs=null
                return this
        }


}