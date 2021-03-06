package kz.sdauka.ormanager.utils;

import kz.sdauka.ormanager.entity.Setting;
import org.apache.log4j.Logger;
import org.ini4j.Wini;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.File;
import java.io.IOException;


/**
 * Created by Dauletkhan on 11.01.2015.
 */
public class IniFileUtil {
    private static final Logger LOG = Logger.getLogger(IniFileUtil.class);
    private static Setting setting = readIniFile();

    private static void createIniFile() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("ormanager");
        try {
            File file = new File(System.getProperty("user.home") + "\\AppData\\Local\\ORManager\\settings.ini");
            if (!file.exists()) {
                file.createNewFile();
            }
            Wini wini = new Wini(new File(System.getProperty("user.home") + "\\AppData\\Local\\ORManager\\settings.ini"));
            wini.put("Access Rights", "hideTaskBar", encryptor.encrypt("true"));
            wini.put("Access Rights", "disableTaskManager", encryptor.encrypt("true"));
            wini.put("Access Rights", "disableWin", encryptor.encrypt("true"));
            wini.put("Access Rights", "disableAltF4", encryptor.encrypt("true"));
            wini.put("Access Rights", "disableAltTab", encryptor.encrypt("true"));
            wini.put("Access Rights", "startUp", encryptor.encrypt("false"));
            wini.put("Email settings", "openNotification", encryptor.encrypt("true"));
            wini.put("Email settings", "closeNotification", encryptor.encrypt("true"));
            wini.put("Email settings", "emailAdresat", encryptor.encrypt("sample@mail.ru"));
            wini.put("Email settings", "emailSender", encryptor.encrypt("sample@mail.ru"));
            wini.put("Email settings", "emailPassword", encryptor.encrypt("qwerty"));
            wini.put("Email settings", "smtp", encryptor.encrypt("smtp.mail.ru"));
            wini.put("Email settings", "port", encryptor.encrypt("465"));
            wini.put("Ads settings", "ads", encryptor.encrypt(""));
            wini.put("Obs settings", "obs", encryptor.encrypt(""));
            wini.store();
        } catch (IOException ex) {
            LOG.error(" create ini file is failed", ex);
        }
    }

    private static Setting readIniFile() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("ormanager");
        Setting setting1 = new Setting();
        try {
            File file = new File(System.getProperty("user.home") + "\\AppData\\Local\\ORManager\\settings.ini");
            if (!file.exists()) {
                createIniFile();
            }
            Wini wini = new Wini(new File(System.getProperty("user.home") + "\\AppData\\Local\\ORManager\\settings.ini"));
            setting1.setHideTaskBar(Boolean.parseBoolean(encryptor.decrypt(wini.get("Access Rights", "hideTaskBar"))));
            setting1.setDisableTaskManager(Boolean.parseBoolean(encryptor.decrypt(wini.get("Access Rights", "disableTaskManager"))));
            setting1.setDisableWin(Boolean.parseBoolean(encryptor.decrypt(wini.get("Access Rights", "disableWin"))));
            setting1.setDisableAltF4(Boolean.parseBoolean(encryptor.decrypt(wini.get("Access Rights", "disableAltF4"))));
            setting1.setDisableAltTab(Boolean.parseBoolean(encryptor.decrypt(wini.get("Access Rights", "disableAltTab"))));
            setting1.setStartUp(Boolean.parseBoolean(encryptor.decrypt(wini.get("Access Rights", "startUp"))));
            setting1.setOpenNotification(Boolean.parseBoolean(encryptor.decrypt(wini.get("Email settings", "openNotification"))));
            setting1.setCloseNotification(Boolean.parseBoolean(encryptor.decrypt(wini.get("Email settings", "closeNotification"))));
            setting1.setEmailAdresat(encryptor.decrypt(wini.get("Email settings", "emailAdresat")));
            setting1.setEmailSender(encryptor.decrypt(wini.get("Email settings", "emailSender")));
            setting1.setEmailPassword(encryptor.decrypt(wini.get("Email settings", "emailPassword")));
            setting1.setSmtp(encryptor.decrypt(wini.get("Email settings", "smtp")));
            setting1.setPort(encryptor.decrypt(wini.get("Email settings", "port")));
            setting1.setAds(encryptor.decrypt(wini.get("Ads settings", "ads")));
            setting1.setObs(encryptor.decrypt(wini.get("Obs settings", "obs")));
        } catch (IOException e) {
            LOG.error(" read ini file is failed", e);
        }
        return setting1;
    }


    public static Setting getSetting() {
        return setting;
    }

    public static void setIniFileElement(String sectionName, String optionName, Object value) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("ormanager");
        try {
            File file = new File(System.getProperty("user.home") + "\\AppData\\Local\\ORManager\\settings.ini");
            if (!file.exists()) {
                file.createNewFile();
            }
            Wini wini = new Wini(file);
            wini.put(sectionName, optionName, encryptor.encrypt(String.valueOf(value)));
            wini.store();
        } catch (IOException e) {
            LOG.error("get ini file element is failed", e);
        }
    }
}
