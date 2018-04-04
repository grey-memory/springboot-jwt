package com.akera.web.controller;

import com.akera.model.global.SysResult;
import com.akera.model.po.UserInfo;
import com.akera.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class OauthController {
    @Autowired
    private OauthService oauthService;
    @RequestMapping("/login")
    public SysResult login(@RequestBody UserInfo user){
        return oauthService.login(user);
    }
}
