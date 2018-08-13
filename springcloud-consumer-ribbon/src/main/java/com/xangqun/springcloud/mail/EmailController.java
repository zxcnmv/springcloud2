/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private MailService mailService;//注入发送邮件的各种实现方法
    @Autowired
    private TemplateEngine templateEngine;//注入模板引擎

    @RequestMapping
    public JsonResult index(){
        try {
            mailService.sendSimpleMail("xxx@126.com","SpringBoot Email","这是一封普通的SpringBoot测试邮件");
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }

    @RequestMapping("/htmlEmail")
    public JsonResult htmlEmail(){
        try {
            mailService.sendHtmlMail("xxx@126.com","test subject","<body style=\"text-align: center;margin-left: auto;margin-right: auto;\">\n"
                    + " <div id=\"welcome\" style=\"text-align: center;position: absolute;\" >\n"
                    +"      <h3>test</h3>\n"
                    + " </div>\n" + "</body>");
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }

    @RequestMapping("/attachmentsMail")
    public JsonResult attachmentsMail(){
        try {
            String filePath = "test.png";
            mailService.sendAttachmentsMail("xxx@126.com", "这是一封带附件的邮件", "邮件中有附件，请注意查收！", filePath);
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }

    @RequestMapping("/resourceMail")
    public JsonResult resourceMail(){
        try {
            String rscId = "test";
            String content = "<html><body>这是有图片的邮件<br/><img src=\'cid:" + rscId + "\' ></body></html>";
            String imgPath = "test.png";
            mailService.sendResourceMail("xxx@126.com", "这邮件中含有图片", content, imgPath, rscId);

        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }

    @RequestMapping("/templateMail")
    public JsonResult templateMail(){
        try {
            Context context = new Context();
            context.setVariable("project", "test");
            context.setVariable("author", "xangqun");
            context.setVariable("url", "https://www.baidu.com");
            String emailContent = templateEngine.process("emailTep.html", context);

            mailService.sendHtmlMail("xxx@126.com", "这是模板邮件", emailContent);
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }
}

