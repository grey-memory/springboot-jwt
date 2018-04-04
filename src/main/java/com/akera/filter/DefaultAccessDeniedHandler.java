package com.akera.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akera.model.global.RetConstant;
import com.akera.model.global.SysResult;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        OutputStream outputStream = response.getOutputStream();  
        outputStream.write(JSON.toJSON(SysResult.ERROR(RetConstant.UserInfo.INVALID_LOGIN_CODE, RetConstant.UserInfo.PERMISSION_DENIED_MSG)).toString().getBytes());
        outputStream.flush();  
        outputStream.close();

	}

}
