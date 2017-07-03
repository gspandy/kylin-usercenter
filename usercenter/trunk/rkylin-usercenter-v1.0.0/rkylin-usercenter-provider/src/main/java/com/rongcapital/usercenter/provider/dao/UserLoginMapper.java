/**
 * <p>Title: ${file_name}</p>
 * <p>Description:${project_name} </p>
 * <p>Copyright:${date} </p>
 * <p>Company: rongshu</p>
 * <p>author: ${user}</p>
 * <p>package: ${package_name}</p>
 * @version v1.0.0
 */
package com.rongcapital.usercenter.provider.dao;

import com.rongcapital.usercenter.provider.po.UserLogin;

/**
 * 用户登陆 Description:
 * 
 * @author: libi
 * @CreateDate: 2016年8月24日
 * @version: V1.0
 */
public interface UserLoginMapper {

    int deleteByPrimaryKey(Long loginId);

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(Long loginId);
    
    UserLogin selectByUserName(String userName);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);
    int updateUserPwd(UserLogin record);
}