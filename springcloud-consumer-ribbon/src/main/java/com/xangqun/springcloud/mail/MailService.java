/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.mail;

import javax.mail.MessagingException;

/**
 * @author laixiangqun
 * @since 2018-8-13
 */
public interface MailService {
    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content);

    public void sendSimpleMail(String to, String subject, String content, String... cc);

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException;

    public void sendHtmlMail(String to, String subject, String content, String... cc)throws MessagingException;

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException;

    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc)throws MessagingException;

    /**
     * 发送正文中有静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @throws MessagingException
     */
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException;

    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc)throws MessagingException;

}
