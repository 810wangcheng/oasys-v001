package com.cy.common.utils;

import com.cy.common.vo.ResultEnum;
import com.cy.common.vo.ResultVO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class BindingResultUtils {

    public static ResultVO hasError(BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            //封装错误信息
            List<String> messageList;
            //k:出错区域,v:错误信息
            Map<String,List<String>> map = new HashMap<>();
            for (FieldError fieldError : fieldErrors) {
                if (!map.containsKey(fieldError.getField())){
                    messageList = new ArrayList<>();
                }else {
                    messageList = map.get(fieldError.getField());
                }
                messageList.add(fieldError.getDefaultMessage());
                map.put(fieldError.getField(),messageList);
            }
            return LocalVerifyError(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMessage(),map);
        }
        return success();
    }

    private static ResultVO success() {
        return success(null);
    }

    private static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg("success");
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        return resultVO;
    }

    private static ResultVO LocalVerifyError(Integer code, String message, Map<String, List<String>> map) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(message);
        resultVO.setData(map);
        return resultVO;
    }
}
