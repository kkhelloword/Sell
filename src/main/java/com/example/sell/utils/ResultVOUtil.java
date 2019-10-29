package com.example.sell.utils;

import com.example.sell.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object obj){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setData(obj);
        return resultVO;
    }

    public static  ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(msg);
        return resultVO;
    }
}
