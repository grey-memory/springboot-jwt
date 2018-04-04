package com.akera.factory;

import java.util.ArrayList;
import java.util.List;

import com.akera.model.po.UserInfo;
import com.akera.model.vo.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public final class JwtUserFactory {
	
	private JwtUserFactory() {
    }

    public static JwtUser create(UserInfo user) {
        return new JwtUser(
                user.getUserId(),
                user.getUserName(),
                user.getUserPwd(),
                mapToGrantedAuthorities()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities() {
    	List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
    	list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    	return list;
    }

}
