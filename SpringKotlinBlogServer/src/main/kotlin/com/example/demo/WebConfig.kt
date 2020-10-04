package com.example.demo

import com.example.demo.Interceptor.LoginInterceptor
import com.example.demo.Interceptor.StaticResourceInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.*


@Configuration
class WebConfig(val staticResourceInterceptor: StaticResourceInterceptor, val loginInterceptor: LoginInterceptor) : WebMvcConfigurer {
    //    http://localhost:8080/static/lib/editormd/lib/codemirror/addon/dialog/dialog.css
//    http://localhost:8080/lib/editormd/lib/codemirror/codemirror.min.css

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(staticResourceInterceptor)
//                .addPathPatterns("/static/lib/**")

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login", "/admin/logout", "/admin")
    }
}