package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepo extends JpaRepository<SysUser, Long> {
	
	SysUser findByUsername(String username);
}
