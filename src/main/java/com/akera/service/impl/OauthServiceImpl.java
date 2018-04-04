package com.akera.service.impl;

import com.akera.model.global.RetConstant;
import com.akera.model.global.SysResult;
import com.akera.model.po.UserInfo;
import com.akera.model.vo.JwtUser;
import com.akera.service.OauthService;
import com.akera.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class OauthServiceImpl implements OauthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public SysResult login(UserInfo userInfo) {
        String username = userInfo.getUserName();
        String password = userInfo.getUserPwd();
        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            return SysResult.ERROR(RetConstant.UserInfo.PASSWORD_NOVALID_MSG);
        }
        final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        userDetails.setToken(token);
        return SysResult.OK(userDetails);
    }
}
