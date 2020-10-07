package com.example.demo.service;

import com.example.demo.po.Blog;
import com.example.demo.po.Tag;
import com.example.demo.po.Type;
import com.example.demo.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SSSSS extends JpaRepository<Tag, Long> {

    public List<Tag> findAllByNameContains(String name);

}
