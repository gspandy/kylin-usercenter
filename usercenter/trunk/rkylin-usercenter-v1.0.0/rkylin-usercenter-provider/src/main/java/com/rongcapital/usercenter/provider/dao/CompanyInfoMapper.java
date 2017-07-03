
package com.rongcapital.usercenter.provider.dao;

import com.rongcapital.usercenter.provider.po.CompanyInfo;


/**
 * Description:
 * @author: Administrator
 * @CreateDate: 2016-9-1
 * @version: V1.0
 */
public interface CompanyInfoMapper {
 

    /**
     * Discription:
     *
     * @param id* @return int ${return_type}
     * @author Administrator
     * @since 2016-9-1
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Discription:
     *
     * @param record* @return int ${return_type}
     * @author Administrator
     * @since 2016-9-1
     */
    int insert(CompanyInfo record);

    /**
     * Discription:
     *
     * @param record* @return int ${return_type}
     * @author Administrator
     * @since 2016-9-1
     */
    int insertSelective(CompanyInfo record);

   
    /**
     * Discription:
     *
     * @param id* @return com.rongcapital.wallet.po.CompanyInfo ${return_type}
     * @author Administrator
     * @since 2016-9-1
     */
    CompanyInfo selectByPrimaryKey(Long id);

  
    /**
     * Discription:
     *
     * @param record* @return int ${return_type}
     * @author Administrator
     * @since 2016-9-1
     */
    int updateByPrimaryKeySelective(CompanyInfo record);

    /**
     * Discription:
     *
     * @param record* @return int ${return_type}
     * @author Administrator
     * @since 2016-9-1
     */
    int updateByPrimaryKey(CompanyInfo record);
}