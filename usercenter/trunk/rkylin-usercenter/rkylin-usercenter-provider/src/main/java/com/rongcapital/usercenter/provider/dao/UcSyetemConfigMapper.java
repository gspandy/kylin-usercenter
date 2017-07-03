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

import org.apache.ibatis.annotations.Param;

import com.rongcapital.usercenter.provider.po.UcSyetemConfig;
/**
 * 
 * Description:
 * @author: bihf
 * @CreateDate: 2016年12月23日
 * @version: V1.0
 */
public interface UcSyetemConfigMapper {

    

    UcSyetemConfig selectByConfig(@Param(value="configKey") String configKey);
    
    
}