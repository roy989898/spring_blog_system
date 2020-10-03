package com.example.demo.service

import com.example.demo.NotFoundException
import com.example.demo.dao.BlogRepository
import com.example.demo.form.BlogSearchForm
import com.example.demo.po.Blog
import com.example.demo.po.Type
import com.example.demo.unwrap
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root


@Service
class BlogServiceImp(val blogRepository: BlogRepository) : BlogService {
    override fun getBlog(id: Long): Blog? {
        return blogRepository.findById(id).unwrap()
    }

    override fun listBlog(pageable: Pageable, blogForm: BlogSearchForm): Page<Blog> {
        return blogRepository.findAll(Specification { root: Root<Blog>, criteriaQuery: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
            val predicates = mutableListOf<Predicate>()


            if (!blogForm.title.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get<String>("title"), "%" + blogForm.title + "%"))
            }

            if (blogForm.type?.id != null) {
                predicates.add(criteriaBuilder.equal(root.get<Type?>("type").get<Long?>("id"), blogForm.type?.id ?: 0))
            }
            if (blogForm.recommend == true) {
                predicates.add(criteriaBuilder.equal(root.get<Boolean>("recommend"), blogForm.recommend))
            }

            criteriaQuery.where(*predicates.toTypedArray())
//            TODO
            return@Specification null

        }, pageable)
    }

    override fun saveBlog(blog: Blog): Blog {
        return blogRepository.save(blog)
    }

    override fun updateBlog(id: Long, blog: Blog): Blog {
        val findBlog = blogRepository.findById(id).unwrap()
        if (findBlog == null) {
            throw NotFoundException("Blog not found")
        } else {
            blog.id = id


            return blogRepository.save(blog)


        }
    }

    override fun deleteBlog(id: Long) {
        blogRepository.deleteById(id)
    }
}