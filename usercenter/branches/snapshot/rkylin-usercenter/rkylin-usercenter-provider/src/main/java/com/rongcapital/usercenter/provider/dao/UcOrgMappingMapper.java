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

import com.rongcapital.usercenter.provider.po.UcOrgMapping;
import com.rongcapital.usercenter.provider.po.UcOrgMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Description:
 * @author: Administrator
 * @CreateDate: 2017-2-22
 * @version: V1.0
 */
public interface UcOrgMappingMapper {
    int countByExample(UcOrgMappingExample example);

    int deleteByExample(UcOrgMappingExample example);

    /**
     * Discription:
     *
     * @param id* @return int ${return_type}
     * @author Administrator
     * @since 2017-2-22
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Discription:
     *
     * @param record* @return int ${return_type}
     * @author Administrator
     * @since 2017-2-22
     */
    int insert(UcOrgMapping record);

    /**
     * Discription:
     *
     * @param record* @return int ${return_type}
     * @author Administrator
     * @since 2017-2-22
     */
    int insertSelective(UcOrgMapping record);

    List<UcOrgMapping> selectByExample(UcOrgMappingExample example);

    /**
     * Discription:
     *
     * @param id* @return com.rongcapital.usercenter.provider.po.UcOrgMapping ${return_type}
     * @author Administrator
     * @since 2017-2-22
     */
    UcOrgMapping selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UcOrgMapping record, @Param("example") UcOrgMappingExample example);

    int updateByExample(@Param("record") UcOrgMapping record, @Param("example") UcOrgMappingExample example);

    /**
     * Discription:
     *
     * @param record* @return int ${return_type}
     * @author Administrator
     * @since 2017-2-22
     */
    int updateByPrimaryKeySelective(UcOrgMapping record);

    /**
     * Discription:
     *
     * @param record* @return int ${return_type}
     * @author Administrator
     * @since 2017-2-22
     */
    int updateByPrimaryKey(UcOrgMapping record);
}