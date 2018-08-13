/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerfeign.kaptcha;

import com.google.code.kaptcha.Producer;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @author laixiangqun
 * @since 2018-8-13
 */
@Controller
public class KaptchaController {
    @Autowired
    private Producer producer;
    /**
     * 创建验证码
     *
     * @param request request
     * @throws Exception
     */
    @GetMapping("kaptcha/{randomStr}")
    public void createCode(@PathVariable String randomStr, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Assert.isNull(randomStr, "机器码不能为空");
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        response.setContentType( MediaType.IMAGE_JPEG_VALUE);
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
        IOUtils.closeQuietly(out);
    }
}
