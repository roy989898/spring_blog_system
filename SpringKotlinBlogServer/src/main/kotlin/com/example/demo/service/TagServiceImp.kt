package com.example.demo.service

import com.example.demo.dao.TagRepository
import com.example.demo.po.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class TagServiceImp(private val tagRepository: TagRepository) : TagService {
    override fun getTag(id: Long): Optional<Tag> {
        return tagRepository.findById(id)
    }

    override fun getTag(name: String): Optional<Tag> {
        return tagRepository.findTagByNameEquals(name)
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

    override fun deleteTags(tags: List<Tag>) {
        tagRepository.deleteInBatch(tags)

    }


}