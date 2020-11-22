package com.example.demo.service

import com.example.demo.dao.BlogRepository
import com.example.demo.dao.TagRepository
import com.example.demo.po.Tag
import com.example.demo.unwrap
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.ArrayList

@Service
class TagServiceImp(private val tagRepository: TagRepository, private val blogRepository: BlogRepository) : TagService {
    override fun getTag(id: Long): Optional<Tag> {
        return tagRepository.findById(id)
    }

    override fun getTag(name: String): Optional<Tag> {
        return tagRepository.findTagByNameEquals(name)
    }

    override fun listTag(): List<Tag> {
        return tagRepository.findAll()
    }

    override fun getTagLike(name: String): ArrayList<Tag> {
        return tagRepository.findAllByNameContains(name)
    }

    override fun getTagsTop(size: Int): List<Tag> {
        val usedPageNUm = 0
        val sort = Sort.by(Sort.Direction.DESC, "blogs.size")
        val pb = PageRequest.of(usedPageNUm, size, sort)
        return tagRepository.findAllBySQl(pb)

    }

    @Transactional
    override fun deleteTagByName(name: String) {
        val tag = getTag(name).unwrap()
        tag?.let { tag ->
            val tagHoldBlog = tag.blogs
            tagHoldBlog.forEach {
                it.tags.remove(tag)

            }
            blogRepository.saveAll(tagHoldBlog)

            tagRepository.delete(tag)
        }

//        remove the tag from the blog
//        save
//delete the tag!!!


//        tagRepository.deleteInBatch(name)

    }


}