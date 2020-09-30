package com.example.demo.AOP.Log

import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class ReqeustLog(
        val url: String,
        val ip: String,
        val classMethod: String,
        val args: Array<Any>
) {


    override fun toString(): String {
        return "ReqeustLog{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args=" + args.contentToString() +
                '}';
    }
}