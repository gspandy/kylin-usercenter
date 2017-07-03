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

import com.rongcapital.usercenter.provider.po.UserLoginHistory;

/**
 * Description:
 * 
 * @author: Administrator
 * @CreateDate: 2016-8-23
 * @version: V1.0
 */
public interface UserLoginHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserLoginHistory record);

    int insertSelective(UserLoginHistory record);

    UserLoginHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLoginHistory record);

    int updateByPrimaryKey(UserLoginHistory record);
}