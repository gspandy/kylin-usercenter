package com.rongcapital.usercenter.service;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

















import com.rongcapital.usercenter.api.exception.RegiesterException;
import com.rongcapital.usercenter.api.po.UserInfoPo;
import com.rongcapital.usercenter.api.service.UserCenterService;
import com.rongcapital.usercenter.api.service.UserRegiestAndLoginService;
import com.rongcapital.usercenter.api.vo.BaseResultVo;
import com.rongcapital.usercenter.api.vo.OpenAccountPerson;
import com.rongcapital.usercenter.api.vo.ResponseResultVo;
import com.rongcapital.usercenter.api.vo.WeChatRegisterInfo;
import com.rongcapital.usercenter.util.JsonUtil;
import com.rongcapital.usercenter.util.ParamValidateUtils;
import com.ropapi.constants.ErrorCodeConstants;
import com.ropapi.response.BaseResponse;
import com.ropapi.response.ErrorResponse;
import com.ropapi.response.Response;

@Service("usercentermanage")
public class UsercenterManageWebService {
    
    private static Logger logger = LoggerFactory.getLogger(UsercenterManageWebService.class);
    
    private static final String WECHAT_REGIST_PLATFORM="WX";

    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private UserRegiestAndLoginService userRegiestAndLoginService;
    
    @Value("${userCenter.rop.isEmpty.code}")
    private String paramIsEmptyErrorCode;
    
    @Value("${userCenter.rop.success.code}")
    private String successResponseCode;
    
    @Value("${userCenter.rop.param.invalide.code}")
    private String paramInValide;
    
    @Value("${userCenter.rop.system.error.code}")
    private String copyPropertiesError;
    
    
    
    
    
    public Response registandopenacc(Map<String, String[]> requestParams){
        
        logger.info("UsercenterManageWebService.registandopenacc入参requestParams：{}",JsonUtil.bean2JsonStr(requestParams));
        BaseResponse<String> response = new BaseResponse<String>();
        String[] publicnumberid = requestParams.get("publicnumberid");
        String[] openid = requestParams.get("openid");
        String[] certificatetype = requestParams.get("certificatetype");
        String[] certificatenumber = requestParams.get("certificatenumber");
        String[] telnum = requestParams.get("telnum");
        String[] orgid = requestParams.get("orgid");
        String[] proid=requestParams.get("proid");
        ErrorResponse erpublicnumberid = ParamValidateUtils.validateParamIsEmpty(publicnumberid,paramIsEmptyErrorCode,"publicnumberid不能为空");
        ErrorResponse eropenid = ParamValidateUtils.validateParamIsEmpty(openid,paramIsEmptyErrorCode,"openid不能为空");
        ErrorResponse ercertificatetype = ParamValidateUtils.validateParamIsEmpty(certificatetype,paramIsEmptyErrorCode,"certificatetype不能为空");
        ErrorResponse ercertificatenumber = ParamValidateUtils.validateParamIsEmpty(certificatenumber,paramIsEmptyErrorCode,"certificatenumber不能为空");
     //   ErrorResponse ertelnum = ParamValidateUtils.validateParamIsEmpty(certificatenumber,paramIsEmptyErrorCode,"telnum不能为空");
        ErrorResponse erorgid = ParamValidateUtils.validateParamIsEmpty(orgid,paramIsEmptyErrorCode,"orgid不能为空");
        ErrorResponse erproid = ParamValidateUtils.validateParamIsEmpty(proid,paramIsEmptyErrorCode,"proid不能为空");
        String userId="";
        String userCode="";
        if(null != erpublicnumberid){
            return erpublicnumberid;
        }
        if(null != eropenid){
            return eropenid;
        }
        if(null != ercertificatetype){
            return ercertificatetype;
        }
        if(null != ercertificatenumber){
            return ercertificatenumber;
        }
//        if(null != ertelnum){
//            return ertelnum;
//        }
        if(null != erorgid){
            return erorgid;
        }
        if(null != erproid){
            return erproid;
        }
        WeChatRegisterInfo weChatRegisterInfo = new WeChatRegisterInfo();
        weChatRegisterInfo.setOpenId(openid[0]);
        weChatRegisterInfo.setOrgCode(orgid[0]);
        if(null!=telnum&&telnum.length>0){
            weChatRegisterInfo.setTeleNum(telnum[0]);
        }
        weChatRegisterInfo.setPublicNumberId(publicnumberid[0]);
        weChatRegisterInfo.setCertificateType(certificatetype[0]);
        weChatRegisterInfo.setCertificateNumber(certificatenumber[0]);
        BaseResultVo<ResponseResultVo> weChatRegist;
        try {
            weChatRegist = userRegiestAndLoginService.weChatRegist(weChatRegisterInfo);
            
            if(weChatRegist.isSuccess()){
                userId=weChatRegist.getResultData().getUserId();
                userCode=weChatRegist.getResultData().getUserCode();
            }
            else if(ErrorCodeConstants.USERCENTER_EXSIT_USERNAME.equals(weChatRegist.getResponseCode())){//用户存在
             // 微信平台，公众号，openId作为登录名
                String userLoginName = WECHAT_REGIST_PLATFORM + publicnumberid[0] + openid[0];
                BaseResultVo<UserInfoPo> data = userCenterService.getUserInfoByUserNameAndOrgCode(userLoginName, orgid[0]);
                userId=data.getResultData().getUserId();
                userCode=data.getResultData().getUserCode();
            }
            else{
                ErrorResponse errorResponse = new ErrorResponse(weChatRegist.getResponseCode(), weChatRegist.getErrorMsg());
                logger.error(errorResponse.getMsg());
                return errorResponse;
            }
        } catch (RegiesterException e) {
            logger.error("UsercenterManageWebService.registandopenacc注册开户失败",e);
            ErrorResponse errorresult = new ErrorResponse(copyPropertiesError, "系统错误");
            return errorresult;
        }
        OpenAccountPerson openAccountPerson= new OpenAccountPerson();
         
        openAccountPerson.setRootInstCd(orgid[0]);
        openAccountPerson.setProductId(proid[0]);
        openAccountPerson.setUserId(userId);
        openAccountPerson.setAccountCode(userCode);
        openAccountPerson.setCertificateType(certificatetype[0]);
        openAccountPerson.setCertificateNumber(certificatenumber[0]);
        if(null!=telnum&&telnum.length>0){
            openAccountPerson.setMobileTel(telnum[0]);
        }
       
        BaseResultVo<Boolean> openAccountPerson2 = userCenterService.openAccountPerson(openAccountPerson);
        if(openAccountPerson2.isSuccess()){
            response.setData(userId);
            response.setResponsecode(successResponseCode);
        }else{
            ErrorResponse errorResponse = new ErrorResponse(openAccountPerson2.getResponseCode(), openAccountPerson2.getErrorMsg());
            logger.error("小V铺账户开户失败",errorResponse.getMsg());
            return errorResponse;
        }
     return response;

    }
   
}
