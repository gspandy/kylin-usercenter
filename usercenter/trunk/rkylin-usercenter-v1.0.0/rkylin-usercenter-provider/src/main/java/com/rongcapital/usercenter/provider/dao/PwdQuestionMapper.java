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

import com.rongcapital.usercenter.provider.po.PwdQuestion;

/**
 * Description:
 * 
 * @author: Administrator
 * @CreateDate: 2016-8-23
 * @version: V1.0
 */
public interface PwdQuestionMapper {

    int deleteByPrimaryKey(Long questionId);

    int insert(PwdQuestion record);

    int insertSelective(PwdQuestion record);

    PwdQuestion selectByPrimaryKey(Long questionId);

    int updateByPrimaryKeySelective(PwdQuestion record);

    int updateByPrimaryKey(PwdQuestion record);
}