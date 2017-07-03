package com.rongcapital.usercenter.provider.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rongcapital.usercenter.provider.dao.CompanyInfoMapper;
import com.rongcapital.usercenter.provider.dao.PersonInfoMapper;
import com.rongcapital.usercenter.provider.dao.UserInfoMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginHistoryMapper;
import com.rongcapital.usercenter.provider.dao.UserLoginMapper;
import com.rongcapital.usercenter.provider.po.CompanyInfo;
import com.rongcapital.usercenter.provider.po.PersonInfo;
import com.rongcapital.usercenter.provider.po.UserInfo;
import com.rongcapital.usercenter.provider.po.UserInfo.Status;
import com.rongcapital.usercenter.provider.po.UserLogin;
import com.rongcapital.usercenter.provider.service.LocalUserService;

@Service

public class LocalUserServiceImpl implements LocalUserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalUserServiceImpl.class);

    
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserLoginHistoryMapper userLoginHistoryMapper;

    @Override
    @Transactional
    public void regiestPersonInfo(UserInfo userInfo,PersonInfo personInfo,UserLogin userLogin) {
        LOGGER.debug("开始往数据库中插入用户信息");
        userInfoMapper.insertSelective(userInfo);
        Long userInfoId = userInfo.getUserInfoId();
        personInfo.setUserInfoId(userInfoId);
        userLogin.setUserInfoId(userInfoId);
        personInfoMapper.insertSelective(personInfo);
        userLoginMapper.insertSelective(userLogin);
    }
    
    @Transactional
    public void modifyCaasUserCode(Long userInfoId,String caasUserCode) {
        LOGGER.debug("开始往数据库中插入用户信息");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserInfoId(userInfoId);
        userInfo.setCaasUserCode(caasUserCode);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    @Transactional
    public void regiestCompanyInfo(UserInfo userInfo,CompanyInfo companyInfo,UserLogin userLogin) {
        // TODO Auto-generated method stub
        userInfoMapper.insertSelective(userInfo);
        Long userInfoId = userInfo.getUserInfoId();
        companyInfo.setUserInfoId(userInfoId);
        userLogin.setUserInfoId(userInfoId);
        companyInfoMapper.insertSelective(companyInfo);
        userLoginMapper.insertSelective(userLogin);
        
    }
    @Transactional
    public void changeUserInfoStatus(Long userInfoId,Status status){
        if(null != status){
            UserInfo userInfo = new UserInfo();
            userInfo.setUserInfoId(userInfoId);
            userInfo.setStatus(status.value());
            userInfoMapper.updateByPrimaryKeySelective(userInfo );
        }
    }
    @Override
    @Transactional
    public void modifyPwd(UserInfo userInfo,String newPassword){
        UserLogin userLogin= new UserLogin();
        if(userInfo.getUserInfoId()!=null){
            userLogin.setUserInfoId(userInfo.getUserInfoId());
        }
        if(!StringUtils.isEmpty(userInfo.getUserId())){
            userLogin.setLoginName(userInfo.getUserId());
        }
        userLogin.setPwdMd(newPassword);
        userLoginMapper.updateUserPwd(userLogin);
    }
    @Override
    public UserInfo  getUserInfoByLoginName(String loginName){
        return userInfoMapper.selectByUserId(loginName);
    }

    
}
