package com.example.demo.errorHandle

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest


@ControllerAdvice

class ControllerExceptionHandler {

    private val LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)

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

    @ExceptionHandler(value = [Exception::class])
    fun handleException(request: HttpServletRequest, e: Exception, model: Model): ModelAndView {
        LOGGER.error("Request Url :${request.requestURI}, Exception: ${e.message}")
//        TODO handle the @ResponseStatus
        if (AnnotationUtils.findAnnotation(e.javaClass,
                        ResponseStatus::class.java) != null) {
            throw e
        }
        val mav = ModelAndView();
        mav.addObject("url", request.requestURI)
        mav.addObject("exception", e)


        mav.viewName = "error/error"
        return mav

    }

}