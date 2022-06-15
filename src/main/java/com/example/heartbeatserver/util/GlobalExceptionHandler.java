package com.example.heartbeatserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 忽略参数异常处理器
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        logger.error("", e);
        return ResultGenerator.genFailResult("请求参数" + e.getParameterName() + "不能为空");
    }

    /**
     * 缺少请求体异常处理器
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        logger.error("", e);
        return ResultGenerator.genFailResult("请求体不能为空");
    }

    /**
     * 参数校验异常处理器
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result parameterExceptionHandler(MethodArgumentNotValidException e) {
        logger.error("", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                FieldError fieldError = (FieldError) errors.get(0);
                return ResultGenerator.genFailResult(fieldError.getDefaultMessage());
            }
        }
        return ResultGenerator.genFailResult(null);
    }

    /**
     * 其他异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ParamaErrorException.class})
    public Result otherExceptionHandler(Exception e) {
        logger.error("其他异常", e);
        if(!StringUtils.isEmpty(e.getMessage())) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genFailResult(null);
    }

}
