package com.example.demo.service;

import com.example.demo.po.Blog;
import com.example.demo.po.Type;
import com.example.demo.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SSSSS extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {


}
