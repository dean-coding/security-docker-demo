package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.dao.RoleTypeEnum;
import com.example.demo.dao.SysRole;
import com.example.demo.dao.SysUser;
import com.example.demo.dao.SysUserRepo;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initAccounts(SysUserRepo sysUserRepo) {
		return (args) -> {
			if (sysUserRepo.count() == 0) {
				List<SysUser> entitys = new ArrayList<>();
				SysUser superAdmin = new SysUser("super-admin", "superadmin");
				superAdmin.setRoles(Arrays.asList(new SysRole(RoleTypeEnum.ROLE_SUPER_ADMIN)));
				SysUser user = new SysUser("user", "user");
				user.setRoles(Arrays.asList(new SysRole(RoleTypeEnum.ROLE_USER)));
				entitys.add(superAdmin);
				entitys.add(user);
				sysUserRepo.save(entitys);
			}
		};
	}
}
