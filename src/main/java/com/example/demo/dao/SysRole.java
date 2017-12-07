package com.example.demo.dao;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author fuhw
 * @date 2017年11月29日
 */
@Data
@Entity
public class SysRole {
	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private RoleTypeEnum rolename;

	public SysRole(RoleTypeEnum rolename) {
		super();
		this.rolename = rolename;
	}

	public SysRole() {
		super();
	}
}

