package com.rongcapital.usercenter.provider.service.dubbo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;










import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rkylin.usercenter.util.JsonUtil;
import com.rkylin.wheatfield.api.AccountManageDubboService;
import com.rkylin.wheatfield.api.FinanacePersonApi;
import com.rkylin.wheatfield.bean.OpenAccountPerson;
import com.rkylin.wheatfield.model.CommonResponse;
import com.rongcapital.usercenter.api.vo.BindAccountVo;
import com.rongcapital.usercenter.provider.constant.ResponseConstants;
import com.rongcapital.usercenter.provider.po.UserInfo;



public class AccountDubbo {

    private static Logger logger = LoggerFactory.getLogger(AccountDubbo.class);




    @Autowired
    FinanacePersonApi finanacePersonApi;  //开户


    @Autowired
    AccountManageDubboService accountManageDubboService;//绑卡

    


    public CommonResponse openPersonAccount(com.rongcapital.usercenter.api.vo.OpenAccountPerson po) throws IllegalAccessException, InvocationTargetException {
        OpenAccountPerson person = new OpenAccountPerson();
    //      person.setPersonChnName(po.getUserName()); //可选	中文姓名
//        person.setPersonEngName(); //可选	英文姓名	　
//        person.setPersonType(); //可选	个人类别 默认1	　
//        person.setPersonSex();	 //可选	性别（1：男，2：女）	　
//        person.setBirthday(); //可选	出生日期,YYYYMMDD
//        person.setCertificateType();  //可选	证件类型,0身份证;1护照;2军官证;3士兵证;4回乡证;5户口本;6外国护照;7其它	如果证件类型有值则证件号必须有值　
//        person.setCertificateNumber(); //可选	证件号
//        person.setMobileTel();	 //	　可选	手机号码
//        person.setFixTel();	　//可选	固定电话号码	　
//        person.setEmail();	//可选	电子邮箱
//        person.setPost(); //	可选	邮编	　
//        person.setAddress();　//可选	地址	　
//        person.setRemark();	//可选	备注	　
//        person.setRoleCode(); //可选	角色号
//        person.setUserName(); //可选	用户姓名	　

        // person.setWhetherRealName(); //可选	是否实名(0：非实名，1：已实名)默认0
     //   person.setUserId(po.getUserId()); //必须	用户ID
     //   person.setRootInstCd(po.getOrgCode());    //必须	机构码	　
//        person.setProductId(UserPresetConstants.PRO_ID);    //必须	产品号	一期必填，二期非必填
    //    person.setProductId("");    //必须	产品号	
     //  person.setAccountCode(po.getUserCode()); //必须	户口号
        BeanUtils.copyProperties(person, po);
        logger.info("AccountDubbo.openPersonAccount dubbo入参:{" + JsonUtil.bean2JsonStr(person) + "}");
        CommonResponse response = finanacePersonApi.accountoprPerRealNameOpenByDubbo(person);
        logger.info("AccountDubbo.openPersonAccount dubbo反回:{" + JsonUtil.bean2JsonStr(response) + "}");

        //1成功
        if (ResponseConstants.SUCCESS.equals(response.getCode())) {
          //     vo.setSuccess(true);
        } else {
           // vo.setCode(response.getCode());
           // vo.setMsg(response.getMsg());
        }
        return response;
    }

    public CommonResponse  bindcard(BindAccountVo bindAccountVo){
       Map<String,String[]>  map = new HashMap<String, String[]>();
       CommonResponse bindCard = accountManageDubboService.bindCard(map);
       return bindCard;
    }


}
