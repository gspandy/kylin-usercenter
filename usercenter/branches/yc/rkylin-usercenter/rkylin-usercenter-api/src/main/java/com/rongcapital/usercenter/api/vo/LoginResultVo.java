package com.rongcapital.usercenter.api.vo;

import java.io.Serializable;

public class LoginResultVo<T extends Serializable> extends BaseResultVo<T> {

    /**
     * Description:
     */
    private static final long serialVersionUID = 1L;
    
    private Integer faildCount;

    public Integer getFaildCount() {
        return faildCount;
    }

    public void setFaildCount(Integer faildCount) {
        this.faildCount = faildCount;
    }
    

}
