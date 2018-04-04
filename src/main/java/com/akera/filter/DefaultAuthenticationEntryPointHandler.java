package com.akera.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akera.model.global.RetConstant;
import com.akera.model.global.SysResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class DefaultAuthenticationEntryPointHandler implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
	        OutputStream outputStream = response.getOutputStream();  
	        outputStream.write(JSON.toJSON(SysResult.ERROR(RetConstant.UserInfo.INVALID_LOGIN_CODE, RetConstant.UserInfo.INVALID_LOGIN_MSG)).toString().getBytes());
	        outputStream.flush();  
	        outputStream.close();
	}

}
