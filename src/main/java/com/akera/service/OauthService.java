package com.akera.service;

import com.akera.model.global.SysResult;
import com.akera.model.po.UserInfo;

public interface OauthService {

    public SysResult login(UserInfo user) ;
}
