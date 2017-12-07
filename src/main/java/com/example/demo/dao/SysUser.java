package com.example.demo.dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

/**
 * @author fuhw
 * @date 2017年11月29日
 */
@Data
@Entity
public class SysUser {
	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<SysRole> roles;

	public SysUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public SysUser() {
		super();
	}

	
	
}
