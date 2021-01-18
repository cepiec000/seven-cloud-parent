package com.seven.admin.controller;

import com.google.code.kaptcha.Producer;
import com.seven.admin.constant.AdminConstants;
import com.seven.comm.core.redis.RedisService;
import com.seven.comm.core.response.ApiResponse;
import com.seven.comm.core.utils.sign.Base64Utils;
import com.seven.comm.core.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description 验证码生成
 * @date 2021/1/7 10:19
 */
@Controller
public class CaptchaController extends BaseController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Value("${seven.captchaType}")
    private String captchaType;

    @Autowired
    private RedisService redisService;

    @GetMapping("/captchaImage")
    @ResponseBody
    public ApiResponse createCaptcha() {
        String uuid = IdUtils.simpleUUID();
        String verifyKey = AdminConstants.CAPTCHA_CODE_KEY + uuid;

        BufferedImage image = null;
        String capStr, code = null;
        if ("math".equals(captchaType)) {
            String mathText = captchaProducerMath.createText();
            capStr = mathText.substring(0, mathText.lastIndexOf("@"));
            code = mathText.substring(mathText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        redisService.setCacheObject(verifyKey, code, AdminConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return ApiResponse.failed(e.getMessage());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("uuid", uuid);
        data.put("image", Base64Utils.encode(os.toByteArray()));
        return ApiResponse.success(data);
    }
}
