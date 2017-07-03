package com.rongcapital.usercenter.provider.po;

import java.io.Serializable;

public class UcSyetemConfig implements Serializable{
    
    /**
     * Description:
     */
    private static final long serialVersionUID = 1977170154059193661L;
    private Long  id;
    private String configKey;
    private String configValue;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getConfigKey() {
        return configKey;
    }
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
    public String getConfigValue() {
        return configValue;
    }
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    @Override
    public String toString() {
        return "UcSyetemConfig [id=" + id + ", configKey=" + configKey + ", configValue=" + configValue + "]";
    }
   
    

}
