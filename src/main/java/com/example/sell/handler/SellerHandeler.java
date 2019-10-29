package com.example.sell.handler;

import com.example.sell.VO.ResultVO;
import com.example.sell.exception.ResponseBankException;
import com.example.sell.exception.SellAuthorException;
import com.example.sell.exception.SellException;
import com.example.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SellerHandeler {

    @Value("${logintUrl}")
    private String logintUrl;
    /**
     * 拦截登录异常
    * 捕捉异常证明未登录跳转带登录页面
    *@param
    *@return
    */
    @ExceptionHandler(value = SellAuthorException.class)
    public String handlerException(){

        /*跳转带微信登录页面，并携带returnurl是 sell/seller/login
        * 这样微信接口会返回sell/seller/login?openid=用户openid
        * 因为没有微信登录接口，所以使用的伪代码*/
        return "redirect:"+logintUrl+"?openid=123";
    }
    
    /**
    * 异常捕捉 返回自定义状态码
    *@param
    *@return 
    */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO CatchResult(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    /**
     * 异常捕捉 返回自定义状态码
     *@param
     *@return
     */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void CatchResponseBankException(ResponseBankException e){

    }
}
