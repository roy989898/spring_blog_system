package com.example.demo.service;

import com.example.demo.po.Type;
import com.example.demo.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SSSSS extends JpaRepository<Type, Long> {

   /* Optional<Type> findByName(String name);*/
}
