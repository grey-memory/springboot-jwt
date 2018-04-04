package com.akera.service.impl;

import com.akera.factory.JwtUserFactory;
import com.akera.mapper.UserInfoMapper;
import com.akera.model.po.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user  = userInfoMapper.selectByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
		
		return JwtUserFactory.create(user);
	}

}
