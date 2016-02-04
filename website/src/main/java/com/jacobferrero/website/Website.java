package com.jacobferrero.website;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Website extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            sendEmail(req);
        } catch (MessagingException e) {
            Logger.getGlobal().warning(e.toString());
        }
    }

    private void sendEmail(HttpServletRequest req) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "";

        Enumeration<String> parameterNames = req.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String parameter = parameterNames.nextElement();
            msgBody += parameter + ": " + req.getParameter(parameter) + "\n\n";
        }

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("jacobaferrero@gmail.com"));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress("jacobaferrero@gmail.com"));
        msg.setSubject("New Job");
        msg.setText(msgBody);
        Transport.send(msg);
    }
}