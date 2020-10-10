package com.example.demo.po

import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_blog")
data class Blog(
        @Id
        @GeneratedValue
        var id: Long?,
        var title: String,
        @Lob
        @Column(columnDefinition = "TEXT")
        var content: String,
        @Lob
        @Column(columnDefinition = "TEXT")
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
        @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
        var tags: List<Tag>
) {


    fun getTagsString(): String {

        return tags.joinToString("_", transform = {
            return@joinToString it.name
        })
    }


    fun getFormatedUpdateTime(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return simpleDateFormat.format(updateTime)
    }

    fun contentToHtml(): String {
        val parser: Parser = Parser.builder().build()
        val document = parser.parse(content)
        val renderer = TextContentRenderer .builder().build()
        return renderer.render(document) // "<p>This is <em>Sparta</em></p>\n"

    }
}