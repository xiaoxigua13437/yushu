package com.zhaofang.yushu.common;

import com.zhaofang.yushu.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * 邮件工具类
 *
 * @author yushu
 * @create 2019-09-23 15:24
 */
@Component
@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);


    /*//当前对象实例
    private static MailUtil mailUtil = new MailUtil();

    //获取当前对象实例
    public static MailUtil getInstance(){
        return mailUtil;
    }*/

    //不能初始化该interface
    /*private JavaMailSender mailSender = SpringContextHolder.getBean(JavaMailSender.class);*/

    @Autowired
    JavaMailSender mailSender;



    /**
     * 发送简单邮件
     *
     * @param deliver
     * @param receiver
     * @param carbonCopy
     * @param subject
     * @param content
     * @throws ServiceException
     */
    public void sendSimpleEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content) throws ServiceException {

        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ... ");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSentDate(new Date());
            message.setFrom(deliver);
            message.setTo(receiver);
            message.setCc(carbonCopy);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MailException e) {
            logger.error("Send mail failed, error message is : {} \n", e.getMessage());
            e.printStackTrace();
            throw new ServiceException();
        }
    }


    /**
     * 发送 HTML 内容的邮件
     *
     * @param deliver
     * @param receiver
     * @param carbonCopy
     * @param subject
     * @param content
     * @param isHtml
     * @throws ServiceException
     */
    public void sendHtmlEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml) throws ServiceException {
        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send email ...");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);

            mailSender.send(message);
            logger.info("Send email success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            logger.error("Send email failed, error message is {} \n", e.getMessage());
            e.printStackTrace();
            throw new ServiceException();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }



    /**
     * 发送带附件的邮件
     *
     * @param deliver
     * @param receiver
     * @param carbonCopy
     * @param subject
     * @param content
     * @param isHtml
     * @param fileName
     * @param file
     * @throws ServiceException
     */
    public void sendAttachmentFileEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml, String fileName, File file) throws ServiceException {
        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ...");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
            messageHelper.addAttachment(fileName,file);

            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            logger.error("Send mail failed, error message is {}\n", e.getMessage());
            e.printStackTrace();
            throw new ServiceException();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }










}
