package com.example.demo.errorHandle

import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.core.annotation.Order
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest


@RestControllerAdvice
@Order(1)
class RestControllerExceptionHandler {

    private val LOGGER = LoggerFactory.getLogger(RestControllerExceptionHandler::class.java)

    /*  @ExceptionHandler(value = [NotFoundException::class])
      fun handleNotFoundException(request: HttpServletRequest, e: Exception, model: Model): ModelAndView {
          LOGGER.error("Request Url :${request.requestURI}, Exception: ${e.message}")
          *//*  if (AnnotationUtils.findAnnotation(e.javaClass,
                          ResponseStatus::class.java) != null) {
              throw e
          }*//*
        val mav = ModelAndView();
        mav.addObject("url", request.requestURI)
        mav.addObject("exception", e)


        mav.viewName = "error/error"
        return mav

    }*/

    @ExceptionHandler(value = [RestException::class])
    fun handleException(request: HttpServletRequest, e: RestException): RestErrorResponse {
        LOGGER.error("Request Url :${request.requestURI}, Exception: ${e.message}")


        return RestErrorResponse(e.message?:"")

    }

}