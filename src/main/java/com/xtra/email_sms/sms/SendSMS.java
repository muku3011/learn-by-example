package com.xtra.email_sms.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.xtra.email_sms.EmailSmsData;
import com.xtra.email_sms.EmailSmsUtilClass;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class SendSMS {
    public static void main(String[] args) {
        String password = EmailSmsUtilClass.getSmsCredential("password");

        EmailSmsData emailSmsData = new EmailSmsData();
        emailSmsData.setSmsUserName(EmailSmsUtilClass.getSmsCredential("username"));
        emailSmsData.setSmsToNumber("9717888309");
        emailSmsData.setSmsFromNumber("9953966221");
        emailSmsData.setSmsMessage("Welcome test sms");

        //Reference https://www.mashape.com/blaazetech/site2sms
        StringBuilder url = new StringBuilder();

        //API fix URL
        url.append(EmailSmsUtilClass.getSmsCredential("url"));
        //API message for SMS (encoded)
        url.append(createEncodedMessage(emailSmsData));

        //API phone number to which SMS should be sent
        url.append("&phone=");
        url.append(emailSmsData.getSmsToNumber());
        //API password
        url.append("&pwd=");
        url.append(password);
        //API userID
        url.append("&uid=");
        url.append(emailSmsData.getSmsUserName());

        System.out.println("URL : " + url.toString());
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url.toString())
                    .header("X-Mashape-Key", EmailSmsUtilClass.getSmsCredential("mashape.key"))
                    .header("Accept", "application/json").asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        System.out.println("STATUS CODE : " + response.getStatus());
        if (response.getStatus() == 200) {
            System.out.println("Message sent !!!");
        } else {
            System.out.println("Message not sent !!!");
        }
    }

    private static String createEncodedMessage(EmailSmsData emailSmsData) {
        StringBuilder message = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        message.append("Query from ");
        message.append(emailSmsData.getSmsUserName().toUpperCase());
        message.append("(").append(emailSmsData.getSmsFromNumber()).append(") :");
        message.append(newLine);

        if (emailSmsData.getSmsMessage().length() > 80) {
            System.out.println("Trimming user mesage");
            String newMessage = (String) emailSmsData.getSmsMessage().subSequence(0, 77);
            message.append(newMessage);
            message.append("...");
        } else {
            message.append(emailSmsData.getSmsMessage());
        }
        String encodedMessage = message.toString();
        try {
            encodedMessage = URLEncoder.encode(encodedMessage, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("URL encoded message : " + encodedMessage);
        return encodedMessage;
    }

}