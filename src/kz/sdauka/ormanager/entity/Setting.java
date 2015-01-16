package kz.sdauka.ormanager.entity;

/**
 * Created by Dauletkhan on 11.01.2015.
 */
public class Setting {
    private boolean hideTaskBar;
    private boolean disableTaskManager;
    private boolean disableAltF4;
    private boolean disableAltTab;
    private boolean disableWin;
    private boolean openNotification;
    private boolean closeNotification;
    private String emailAdresat;
    private String emailSender;
    private String emailPassword;
    private String smtp;
    private String port;


    public Setting() {
    }

    public boolean isHideTaskBar() {
        return hideTaskBar;
    }

    public void setHideTaskBar(boolean hideTaskBar) {
        this.hideTaskBar = hideTaskBar;
    }

    public boolean isDisableTaskManager() {
        return disableTaskManager;
    }

    public void setDisableTaskManager(boolean disableTaskManager) {
        this.disableTaskManager = disableTaskManager;
    }

    public boolean isDisableAltF4() {
        return disableAltF4;
    }

    public void setDisableAltF4(boolean disableAltF4) {
        this.disableAltF4 = disableAltF4;
    }

    public boolean isDisableAltTab() {
        return disableAltTab;
    }

    public void setDisableAltTab(boolean disableAltTab) {
        this.disableAltTab = disableAltTab;
    }

    public boolean isDisableWin() {
        return disableWin;
    }

    public void setDisableWin(boolean disableWin) {
        this.disableWin = disableWin;
    }

    public boolean isOpenNotification() {
        return openNotification;
    }

    public void setOpenNotification(boolean openNotification) {
        this.openNotification = openNotification;
    }

    public boolean isCloseNotification() {
        return closeNotification;
    }

    public void setCloseNotification(boolean closeNotification) {
        this.closeNotification = closeNotification;
    }

    public String getEmailAdresat() {
        return emailAdresat;
    }

    public void setEmailAdresat(String emailAdresat) {
        this.emailAdresat = emailAdresat;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
