package com.akera.mapper;

import com.akera.model.po.UserInfo;
import com.akera.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends MyMapper<UserInfo> {
    UserInfo selectByUserName(String username);
}