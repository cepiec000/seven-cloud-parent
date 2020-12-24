package com.seven.comm.core.config;

import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常拦截
 *
 * @author v_chendongdong
 * @date 2020/12/21
 */
@ControllerAdvice
public class AbstractExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExceptionAdvice.class);

    @ExceptionHandler({SevenException.class})
    @ResponseBody
    public ApiResponse handleException(SevenException ex) {
        ApiResponse jr = ApiResponse.failed(ex.getCode(), ex.getMessage());
        if (null != ex.getData()) {
            jr.setData(ex.getData());
        }
        return jr;
    }
}
