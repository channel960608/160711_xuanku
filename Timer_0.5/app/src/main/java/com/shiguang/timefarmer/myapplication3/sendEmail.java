package com.shiguang.timefarmer.myapplication3;

/**
 * Created by channel on 2016/7/20.
 */

public class sendEmail extends Thread {
    private String number;
    private String address;
    public sendEmail(String number,String address){
        super();
        this.number=number;
        this.address=address;
    }
    public  void run(){
        //System.out.println("线程"+number+"开始");
        MultiMailsender.MultiMailSenderInfo mailInfo = new MultiMailsender.MultiMailSenderInfo();
        mailInfo.setMailServerHost("smtp.sina.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("whuisschannel@sina.com");
        mailInfo.setPassword("channel");//您的邮箱密码
        mailInfo.setFromAddress("whuisschannel@sina.com");
        mailInfo.setToAddress(address);
        mailInfo.setSubject("Time Farmer账号找回");

        mailInfo.setContent("您的验证码为： "+number);
        String[] receivers = new String[]{};
        String[] ccs = receivers; mailInfo.setReceivers(receivers);
        mailInfo.setCcs(ccs);
        //这个类主要来发送邮件
        MultiMailsender sms = new MultiMailsender();
        sms.sendTextMail(mailInfo);//发送文体格式
        //MultiMailsender.sendHtmlMail(mailInfo);//发送html格式
        //MultiMailsender.sendMailtoMultiCC(mailInfo);//发送抄送

    }
}
