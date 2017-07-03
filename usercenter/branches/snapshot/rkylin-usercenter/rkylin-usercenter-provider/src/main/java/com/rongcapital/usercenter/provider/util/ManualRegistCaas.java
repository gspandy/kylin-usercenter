package com.rongcapital.usercenter.provider.util;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rkylin.usercenter.util.CaasUserServiceProxy;
import com.rkylin.usercenter.util.vo.RegiesterResponse;
import com.rongcapital.usercenter.api.vo.BaseResultVo;

public class ManualRegistCaas {
    private static final Log logger = LogFactory.getLog(UserCenterConfig.class.getName());
    public static void main(String[] args) {
      String appCode="2";
      ClassPathXmlApplicationContext ctx = new  ClassPathXmlApplicationContext("applicationContext.xml");
      String[] names = ctx.getBeanDefinitionNames();
      for(String name:names){
          System.out.println(name);
      }
      String configfile = CaasConfigPathUtils.getConfigfile(appCode);
      CaasUserServiceProxy caasUserServiceProxy =(CaasUserServiceProxy) ctx.getBean("caasUserServiceProxy");
      File file = new File("F:/excel/坚果_未授信_原始数据.xls");
      ArrayList<ArrayList<Object>> result = ExcelUtil.readExcel(file);
      ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
      ArrayList<Object>  firstData = new ArrayList<Object>();
      firstData.add("用户名");
      firstData.add("密码");
      firstData.add("userId");
      firstData.add("usercode");
      firstData.add("caas编码");
      data.add(0,firstData);
      
      for (int i = 1; i < result.size(); i++) {
          ArrayList<Object>  str = new ArrayList<Object>();
          
          String userName=result.get(i).get(2).toString();
          String password="4e2158b8de7c3eaa1848c980e0da76a97bf11668b0bfe18c427d5d3cc5a44e1e";
          String userId=result.get(i).get(4).toString();
          String usercode=result.get(i).get(5).toString();
         str.add(userName);
         str.add("4e2158b8de7c3eaa1848c980e0da76a97bf11668b0bfe18c427d5d3cc5a44e1e");
         str.add(userId);
         str.add(usercode);
         
       BaseResultVo<RegiesterResponse> regiest = caasUserServiceProxy.regiest(configfile, appCode, userName+"M000029", password, null, null);
       if(regiest.isSuccess()){
       RegiesterResponse resultData = regiest.getResultData();
       String cassCode = resultData.getUserCode();
       str.add(cassCode);
       logger.info("manual regest success------------------------------------------------------------------"+userName);
       }
       data.add(str);
      }
      ExcelUtil.writeExcel(data, "F:/excel/坚果_未授信_原始数据_result——online.xls");
      
    }

}
