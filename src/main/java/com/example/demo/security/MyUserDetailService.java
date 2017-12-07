	package com.example.demo.security;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SysUser;
import com.example.demo.dao.SysUserRepo;

/**
 * @author fuhw
 * @date 2017年11月29日
 */
@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private SysUserRepo repo;

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser findByUsername = repo.findByUsername(username);
		if (findByUsername == null) {
			throw new UsernameNotFoundException("the username not found");
		}
		AuthUser authUser = new AuthUser();
		BeanUtils.copyProperties(findByUsername, authUser);
		return authUser;
	}

}
