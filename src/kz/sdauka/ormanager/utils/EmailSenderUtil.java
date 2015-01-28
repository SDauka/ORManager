package kz.sdauka.ormanager.utils;

import kz.sdauka.ormanager.dao.impl.GamesDAOImpl;
import kz.sdauka.ormanager.entity.Setting;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Properties;

/**
 * Created by Dauletkhan on 21.01.2015.
 */
public class EmailSenderUtil {
    private String sender;
    private String password;
    private Properties props;
    private static final Logger LOG = Logger.getLogger(GamesDAOImpl.class);

    public EmailSenderUtil(Setting setting) {
        this.sender = setting.getEmailSender();
        this.password = setting.getEmailPassword();
        props = new Properties();
        props.put("mail.smtp.host", setting.getSmtp());
        props.put("mail.smtp.socketFactory.port", setting.getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", setting.getPort());
    }


    public void sendPassword(String adminPassword) {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress(sender));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sender));
            //тема сообщения
            message.setSubject("Пароль от админки");
            message.setText("Пароль: " + adminPassword + "");
            //отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Не удалось отправить письмо", "Ошибка отправки данных'", JOptionPane.OK_OPTION);
            LOG.error("Не удалось отправить письмо " + e);
        }
    }


}
