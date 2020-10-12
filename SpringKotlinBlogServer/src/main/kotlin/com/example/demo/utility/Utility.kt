package com.example.demo.utility

import com.example.demo.po.Comment
import j2html.TagCreator.*
import j2html.tags.ContainerTag
import j2html.tags.DomContent
import java.util.*

fun createCommentHtmlFullList(): DomContent {
    val fakeComments = fakeComments()
    val dom = fakeComments.map {
        createCommentElement(it)
    }.toTypedArray()
    return div(
            attrs(".container.bootstrap.snippets.bootdey"),
            div(
                    attrs(".row"),
                    div(
                            attrs(".col-md-12"),
                            div(
                                    attrs(".blog-comment"),
                                    ul(attrs(".comments"), *dom)
                            )
                    )
            )
    )
}


fun createCommentElement(comment: Comment): DomContent {

    return li(
            attrs(".clearfix"), img(attrs(".avatar")).withSrc(comment.avatar), div(
            attrs(".post-comments"),
            div(attrs(".meta"),
                    span(comment.createTime.toString()),
                    a(" " + comment.nickname + " ").withHref("*"),
                    span("says :"),
                    i(attrs(".pull-right"),
                            a(
                                    small("Reply")
                            ).withHref("*")
                    )
            ),
            p(comment.content)

    )
    )

}

fun fakeComments(): Array<Comment> {
    return arrayOf(aFakeComment(), aFakeComment())
}

fun aFakeComment(): Comment {
    val img = "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip"
    return Comment(
            null, "Roy", "dfsdfsdf@adasd.com", "i am content", img, Date(), null, emptyList(), null
    )
}

