package kz.sdauka.ormanager.utils;

import kz.sdauka.ormanager.entity.Setting;
import org.apache.log4j.Logger;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;


/**
 * Created by Dauletkhan on 11.01.2015.
 */
public class IniFileUtil {
    private static final Logger LOG = Logger.getLogger(IniFileUtil.class);
    private static Setting setting = readIniFile();
    private static final String filePath = "C:\\Users\\Dauletkhan\\settings.ini";
    //private static BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();


    private static void createIniFile() {
        // basicTextEncryptor.setPassword("sdauka");
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            Wini wini = new Wini(new File(filePath));
            wini.put("Access Rights", "hideTaskBar", "true");
            wini.put("Access Rights", "disableTaskManager", "true");
            wini.put("Access Rights", "disableKeys", "true");
            wini.put("Email settings", "openNotification", "true");
            wini.put("Email settings", "closeNotification", "true");
            wini.put("Email settings", "emailAdresat", "sample@mail.ru");
            wini.put("Email settings", "emailSender", "sample@mail.ru");
            wini.put("Email settings", "emailPassword", "qwerty");
            wini.put("Email settings", "smtp", "smtp.mail.ru");
            wini.put("Email settings", "port", "465");
            wini.store();
        } catch (IOException ex) {
            LOG.error(" create ini file is failed", ex);
        }
    }

    private static Setting readIniFile() {
//        basicTextEncryptor.setPassword("sdauka");
        Setting setting1 = new Setting();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                createIniFile();
            }
            Wini wini = new Wini(new File(filePath));
            setting1.setHideTaskBar(Boolean.parseBoolean(wini.get("Access Rights", "hideTaskBar")));
            setting1.setDisableTaskManager(Boolean.parseBoolean(wini.get("Access Rights", "disableTaskManager")));
            setting1.setDisableKeys(Boolean.parseBoolean(wini.get("Access Rights", "disableKeys")));
            setting1.setOpenNotification(Boolean.parseBoolean(wini.get("Email settings", "openNotification")));
            setting1.setCloseNotification(Boolean.parseBoolean(wini.get("Email settings", "closeNotification")));
            setting1.setEmailAdresat(wini.get("Email settings", "emailAdresat"));
            setting1.setEmailSender(wini.get("Email settings", "emailSender"));
            setting1.setEmailPassword(wini.get("Email settings", "emailPassword"));
            setting1.setSmtp(wini.get("Email settings", "smtp"));
            setting1.setPort(wini.get("Email settings", "port"));

        } catch (IOException e) {
            LOG.error(" read ini file is failed", e);
        }
        return setting1;
    }


    public static Setting getSetting() {
        return setting;
    }

    public static void setIniFileElement(String sectionName, String optionName, Object value) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            Wini wini = new Wini(file);
            wini.put(sectionName, optionName, String.valueOf(value));
            wini.store();
        } catch (IOException e) {
            LOG.error("get ini file element is failed", e);
        }
    }
}
