package com.example.demo.service

import com.example.demo.NotFoundException
import com.example.demo.dao.TypeRepository
import com.example.demo.po.Type
import com.example.demo.unwrap
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class TypeServiceImp(val typeRepository: TypeRepository) : TypeService {

    @Transactional
    override fun save(type: Type): Type {
        return typeRepository.save(type)
    }

    override fun getType(id: Long): Type? {
        return typeRepository.findById(id).unwrap()
    }

    override fun getTypeNyName(name: String): Type? {
        return typeRepository.findByName(name).unwrap()
    }

    override fun listType(pageable: Pageable): Page<Type> {


        return typeRepository.findAll(pageable)

    }

    override fun listType(): List<Type> {
        return typeRepository.findAll()
    }

    override fun listTypeTop(size: Int): List<Type> {
        val usedPageNUm = 0
        val sort = Sort.by(Sort.Direction.DESC, "blogs.size")
        val pb = PageRequest.of(usedPageNUm, size, sort)
        return typeRepository.findAllBySQl(pb)
    }

    @Transactional
    override fun updateType(id: Long, type: Type) {
        val foundType = typeRepository.findById(id).unwrap()
        if (foundType == null) {
            throw NotFoundException("type not found")
        } else {
            foundType.blogs = type.blogs
            foundType.name = type.name
            typeRepository.save(foundType)
        }
    }

    @Transactional
    override fun deleteType(id: Long) {
        typeRepository.deleteById(id)
    }
}