package com.example.demo.api.Response

import com.example.demo.po.Blog
import javax.persistence.CascadeType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotBlank

class RestTypeListResponse(


        var id: Long?,

        var name: String


) {
}