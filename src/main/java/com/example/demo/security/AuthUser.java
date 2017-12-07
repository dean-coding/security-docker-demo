package com.example.demo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import com.example.demo.dao.SysRole;

import lombok.Data;

/**
 * @author fuhw
 * @date 2017年11月29日
 */
@Data
public class AuthUser implements UserDetails {

	private static final long serialVersionUID = -3736232859964766205L;

	private Long id;

	private String username;

	private String password;

	private List<SysRole> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (!CollectionUtils.isEmpty(roles)) {
			for (SysRole role : roles) {
				authorities.add(new SimpleGrantedAuthority(role.getRolename().toString()));
			}
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
