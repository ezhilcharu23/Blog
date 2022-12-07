package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Login;

public interface LoginRepository extends JpaRepository<Login, String>{

}
