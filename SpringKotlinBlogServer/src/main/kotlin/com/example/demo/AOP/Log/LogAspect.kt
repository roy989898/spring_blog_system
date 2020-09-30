package com.example.demo.AOP.Log

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


@Aspect
@Component
open class LogAspect {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("execution(* com.example.demo.web.*.*(..))")
    fun log() {


    }

    @Before("log()")
    fun doBefore(joinPoint: JoinPoint) {
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val request = attributes!!.request
        val classMethod: String = joinPoint.signature.declaringTypeName.toString() + "." +
                joinPoint.signature.name
        val reqeustLog = ReqeustLog(
                request.requestURL.toString(),
                request.remoteAddr,
                classMethod,
                joinPoint.args
        )
        logger.info("Rquest ----- {}", reqeustLog)
    }

    @After("log()")
    fun doAfter() {
//        logger.info("!!!!!!doAfter")
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    fun doAfterReturning(result: Any) {

        logger.info("Return ------ {}", result);

    }
}