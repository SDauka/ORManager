package kz.sdauka.ormanager.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kz.sdauka.ormanager.dao.factory.DAOFactory;
import kz.sdauka.ormanager.entity.*;
import kz.sdauka.ormanager.utils.ExportToExcel;
import kz.sdauka.ormanager.utils.IniFileUtil;
import kz.sdauka.ormanager.utils.ValidationUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dauletkhan on 11.01.2015.
 */
public class SettingCTRL implements Initializable {
    @FXML
    private CheckBox hideTaskBar;
    @FXML
    private CheckBox disableTaskManager;
    @FXML
    private CheckBox disableKeys;
    @FXML
    private CheckBox startUp;
    @FXML
    private CheckBox openNotification;
    @FXML
    private CheckBox closeNotification;
    @FXML
    private TextField emailAdresat;
    @FXML
    private TextField emailSender;
    @FXML
    private PasswordField emailPassword;
    @FXML
    private TextField smtp;
    @FXML
    private TextField port;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField rePassword;
    @FXML
    private Label passwordLbl;
    @FXML
    private Label accessRightsLbl;
    @FXML
    private Label emailLbl;
    @FXML
    private TableView<Operator> operatorsTable;
    @FXML
    private TableColumn<Operator, Integer> idOperators;
    @FXML
    private TableColumn<Operator, String> nameOperators;
    @FXML
    private Button addOperatorBtn;
    @FXML
    private Button deleteOperatorBtn;
    @FXML
    private Button editOperatorBtn;
    @FXML
    private Button addGameBtn1;
    @FXML
    private Button deleteGameBtn1;
    @FXML
    private Button editGameBtn1;
    @FXML
    private TableView<Game> gamesTable;
    @FXML
    private TableColumn<Game, Integer> idGames;
    @FXML
    private TableColumn<Game, String> nameGames;
    @FXML
    private TableColumn<Game, String> pathGames;
    @FXML
    private TableColumn<Game, String> imageGames;
    @FXML
    private TableColumn<Game, String> attributeGames;
    @FXML
    private TableColumn<Game, Integer> timeGames;
    @FXML
    private TableColumn<Game, Integer> costGames;
    @FXML
    private Button reportExcelBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView<Session> sessionTable;
    @FXML
    private TableColumn<Session, Integer> sessionID;
    @FXML
    private TableColumn<Session, String> sessionOperator;
    @FXML
    private TableColumn<Session, Date> sessionDate;
    @FXML
    private TableColumn<Session, Timestamp> sessionStartTime;
    @FXML
    private TableColumn<Session, Timestamp> sessionStopTime;
    @FXML
    private TableColumn<Session, Integer> sessionOperationCount;
    @FXML
    private TableColumn<Session, Integer> sessionSum;
    @FXML
    private TableView<SessionDetails> sessionDetailsTable;
    @FXML
    private TableColumn<SessionDetails, Integer> sessionDetailsID;
    @FXML
    private TableColumn<SessionDetails, Timestamp> sessionDetailStartTime;
    @FXML
    private TableColumn<SessionDetails, String> sessionDetailsGameName;
    @FXML
    private TableColumn<SessionDetails, String> sessionDetailsWorkTime;
    @FXML
    private DatePicker firstPeriod;
    @FXML
    private DatePicker secondPeriod;
    @FXML
    private Label searchErrorLabel;
    @FXML
    private TextField adsTextField;
    @FXML
    private Label adsErrorLabel;
    @FXML
    private TextField obsPathField;
    @FXML
    private Label obsErrorLbl;
    private FileChooser saveDialog = new FileChooser();
    private Admin admin;
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static final Logger LOG = Logger.getLogger(SettingCTRL.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAdmin();
        //таблица с играми
        idGames.setCellValueFactory(new PropertyValueFactory<Game, Integer>("id"));
        nameGames.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
        pathGames.setCellValueFactory(new PropertyValueFactory<Game, String>("path"));
        imageGames.setCellValueFactory(new PropertyValueFactory<Game, String>("image"));
        attributeGames.setCellValueFactory(new PropertyValueFactory<Game, String>("attribute"));
        timeGames.setCellValueFactory(new PropertyValueFactory<Game, Integer>("time"));
        costGames.setCellValueFactory(new PropertyValueFactory<Game, Integer>("cost"));
        gamesTable.setItems(getAllGames());
        //таблица с операторами
        idOperators.setCellValueFactory(new PropertyValueFactory<Operator, Integer>("id"));
        nameOperators.setCellValueFactory(new PropertyValueFactory<Operator, String>("name"));
        operatorsTable.setItems(getAllOperators());
        //таблица с сессией
        sessionID.setCellValueFactory(new PropertyValueFactory<Session, Integer>("id"));
        sessionOperator.setCellValueFactory(new PropertyValueFactory<Session, String>("operator"));
        sessionDate.setCellValueFactory(new PropertyValueFactory<Session, Date>("day"));
        sessionStartTime.setCellValueFactory(new PropertyValueFactory<Session, Timestamp>("startTime"));
        sessionStopTime.setCellValueFactory(new PropertyValueFactory<Session, Timestamp>("stopTime"));
        sessionOperationCount.setCellValueFactory(new PropertyValueFactory<Session, Integer>("countStart"));
        sessionSum.setCellValueFactory(new PropertyValueFactory<Session, Integer>("sum"));
        sessionTable.setItems(getAllSession());
        //таблица с подробностью сессии
        sessionDetailsID.setCellValueFactory(new PropertyValueFactory<SessionDetails, Integer>("id"));
        sessionDetailStartTime.setCellValueFactory(new PropertyValueFactory<SessionDetails, Timestamp>("startTime"));
        sessionDetailsWorkTime.setCellValueFactory(new PropertyValueFactory<SessionDetails, String>("workTime"));
        sessionDetailsGameName.setCellValueFactory(new PropertyValueFactory<SessionDetails, String>("gameName"));
        //other
        password.setText(admin.getPassword());
        hideTaskBar.setSelected(IniFileUtil.getSetting().isHideTaskBar());
        disableTaskManager.setSelected(IniFileUtil.getSetting().isDisableTaskManager());
        disableKeys.setSelected(IniFileUtil.getSetting().isDisableKeys());
        startUp.setSelected(IniFileUtil.getSetting().isStartUp());
        openNotification.setSelected(IniFileUtil.getSetting().isOpenNotification());
        closeNotification.setSelected(IniFileUtil.getSetting().isCloseNotification());
        emailAdresat.setText(IniFileUtil.getSetting().getEmailAdresat());
        emailPassword.setText(IniFileUtil.getSetting().getEmailPassword());
        emailSender.setText(IniFileUtil.getSetting().getEmailSender());
        smtp.setText(IniFileUtil.getSetting().getSmtp());
        port.setText(IniFileUtil.getSetting().getPort());
        adsTextField.setText(IniFileUtil.getSetting().getAds());
        obsPathField.setText(IniFileUtil.getSetting().getObs());
        addOperatorBtn.setGraphic(new ImageView("/img/user_add.png"));
        editOperatorBtn.setGraphic(new ImageView("/img/user_edit.png"));
        deleteOperatorBtn.setGraphic(new ImageView("/img/user_remove.png"));
        addGameBtn1.setGraphic(new ImageView("/img/games_control_add.png"));
        editGameBtn1.setGraphic(new ImageView("/img/games_control_pencil.png"));
        deleteGameBtn1.setGraphic(new ImageView("/img/games_control_close.png"));
        reportExcelBtn.setGraphic(new ImageView("/img/excel.png"));
        searchBtn.setGraphic(new ImageView("/img/find.png"));
    }


    public void handleExportToExcel(ActionEvent actionEvent) throws SQLException {
        if (sessionTable.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Сессия для отчета не выбрана!", "Выберите сессию", JOptionPane.OK_OPTION);
        } else {
            saveDialog.setTitle("Сохранение отчета");
            saveDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("EXCEL", "*.xls"));
            File file = saveDialog.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());
            if (!sessionTable.getSelectionModel().isEmpty()) {
                Session session = sessionTable.getSelectionModel().getSelectedItem();
                List<SessionDetails> sessionDetails = DAOFactory.getInstance().getSessionDAO().getSessionDetails(session.getId());
                ExportToExcel.exportToExcel(file, session, sessionDetails);
            }
        }
    }

    public void searchByDate(ActionEvent actionEvent) {
        if (firstPeriod.getValue() == null && secondPeriod.getValue() == null) {
            searchErrorLabel.setText("Выберите даты с-по...");
            searchErrorLabel.setTextFill(Paint.valueOf("#d30f02"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            searchErrorLabel.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        } else if (firstPeriod.getValue() != null && secondPeriod.getValue() == null) {
            sessionTable.setItems(getSessionsByDate(Date.valueOf(firstPeriod.getValue())));
        } else {
            sessionTable.setItems(getSessionsByDates(Date.valueOf(firstPeriod.getValue()), Date.valueOf(secondPeriod.getValue())));
        }
    }

    private ObservableList<Session> getSessionsByDates(Date first, Date second) {
        ObservableList<Session> sessions = FXCollections.observableArrayList();
        try {
            sessions.addAll(DAOFactory.getInstance().getSessionDAO().getSessionByDates(first, second));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return sessions;
    }

    private ObservableList<Session> getSessionsByDate(Date date) {
        ObservableList<Session> sessions = FXCollections.observableArrayList();
        try {
            sessions.addAll(DAOFactory.getInstance().getSessionDAO().getSessionByDate(date));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return sessions;
    }

    private ObservableList<Session> getAllSession() {
        ObservableList<Session> sessions = FXCollections.observableArrayList();
        try {
            sessions.addAll(DAOFactory.getInstance().getSessionDAO().getAllSession());
        } catch (SQLException e) {
            LOG.error(e);
        }
        return sessions;
    }

    private ObservableList<SessionDetails> getSessionDetails(Session session) {
        ObservableList<SessionDetails> sessionDetailses = FXCollections.observableArrayList();
        try {
            sessionDetailses.addAll(DAOFactory.getInstance().getSessionDAO().getSessionDetails(session.getId()));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return sessionDetailses;
    }

    public void handleGetSessionDetails(Event event) {
        if (!sessionTable.getSelectionModel().isEmpty()) {
            int selectedIndex = sessionDetailsTable.getSelectionModel().getSelectedIndex();
            sessionDetailsTable.setItems(null);
            sessionDetailsTable.layout();
            sessionDetailsTable.setItems(getSessionDetails(sessionTable.getSelectionModel().getSelectedItem()));
            sessionDetailsTable.getSelectionModel().select(selectedIndex);
        }
    }


    private void refreshOperatorsTable() {
        int selectedIndex = operatorsTable.getSelectionModel().getSelectedIndex();
        operatorsTable.setItems(null);
        operatorsTable.layout();
        operatorsTable.setItems(getAllOperators());
        // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
        operatorsTable.getSelectionModel().select(selectedIndex);
    }

    private void refreshGamesTable() {
        int selectedIndex = gamesTable.getSelectionModel().getSelectedIndex();
        gamesTable.setItems(null);
        gamesTable.layout();
        gamesTable.setItems(getAllGames());
        // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
        gamesTable.getSelectionModel().select(selectedIndex);
    }


    @FXML
    private void handleNewGame(ActionEvent actionEvent) {
        Game newGame = new Game();
        boolean okClicked = showGameEditDialog(newGame);
        if (okClicked) {
            addNewGame(GamesEditDialogCTRL.getGame());
            refreshGamesTable();
        }
    }

    @FXML
    private void handleEditGame(ActionEvent actionEvent) {
        Game selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            boolean okClicked = showGameEditDialog(selectedGame);
            if (okClicked) {
                updateGame(GamesEditDialogCTRL.getGame());
                refreshGamesTable();
            }
        } else {
            // Nothing selected
            JOptionPane.showMessageDialog(null, "Выберите игру из таблицы", "Игра не выбрана", JOptionPane.OK_OPTION);
        }
    }

    @FXML
    private void handleDeleteGame(ActionEvent actionEvent) {
        Game selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            deleteGame(selectedGame);
            refreshGamesTable();
        } else {
            // Nothing selected
            JOptionPane.showMessageDialog(null, "Выберите игру из таблицы", "Игра не выбрана", JOptionPane.OK_OPTION);
        }
    }

    @FXML
    private void handleNewOperator(ActionEvent actionEvent) {
        Operator newOperator = new Operator();
        boolean okClicked = showOperatorEditDialog(newOperator);
        if (okClicked) {
            addNewOperator(OperatorsEditDialogCTRL.getOperator());
            refreshOperatorsTable();
        }
    }

    @FXML
    private void handleEditOperator(ActionEvent actionEvent) {
        Operator selectedPerson = operatorsTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = showOperatorEditDialog(selectedPerson);
            if (okClicked) {
                updateOperator(OperatorsEditDialogCTRL.getOperator());
                refreshOperatorsTable();
            }
        } else {
            // Nothing selected
            JOptionPane.showMessageDialog(null, "Выберите оператора из таблицы", "Оператор не выбран", JOptionPane.OK_OPTION);
        }
    }

    @FXML
    private void handleDeleteOperator(ActionEvent actionEvent) {
        Operator selectedPerson = operatorsTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            deleteOperator(selectedPerson);
            refreshOperatorsTable();
        } else {
            // Nothing selected
            JOptionPane.showMessageDialog(null, "Выберите оператора из таблицы", "Оператор не выбран", JOptionPane.OK_OPTION);
        }
    }


    public boolean showOperatorEditDialog(Operator operator) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/operatorsEditDialog.fxml"));
            Pane page = (Pane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Изменить данные");
            dialogStage.getIcons().add(new Image("/img/icon.png"));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            // Set the person into the controller
            OperatorsEditDialogCTRL controller = loader.getController();
            controller.setEditDialogStage(dialogStage);
            controller.setOperator(operator);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            LOG.error(e);
            return false;
        }
    }

    public boolean showGameEditDialog(Game game) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gamesEditDialog.fxml"));
            Pane page = (Pane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Изменить данные");
            dialogStage.getIcons().add(new Image("/img/icon.png"));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            // Set the person into the controller
            GamesEditDialogCTRL controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGame(game);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            LOG.error(e);
            return false;
        }
    }

    private void getAdmin() {
        try {
            admin = DAOFactory.getInstance().getAdminDAO().getAdmin();
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    private ObservableList<Operator> getAllOperators() {
        ObservableList<Operator> operatorList = FXCollections.observableArrayList();
        try {
            operatorList.addAll(DAOFactory.getInstance().getOperatorsDAO().getAllOperators());
        } catch (SQLException e) {
            LOG.error(e);
        }

        return operatorList;
    }

    private ObservableList<Game> getAllGames() {
        ObservableList<Game> gamesList = FXCollections.observableArrayList();
        try {
            gamesList.addAll(DAOFactory.getInstance().getGamesDAO().getAllGames());
        } catch (SQLException e) {
            LOG.error(e);
        }

        return gamesList;
    }

    private void addNewGame(Game game) {
        try {
            DAOFactory.getInstance().getGamesDAO().setGame(game);
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    private void updateGame(Game game) {
        try {
            DAOFactory.getInstance().getGamesDAO().updateGame(game);
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    private void deleteGame(Game game) {
        try {
            DAOFactory.getInstance().getGamesDAO().deleteGame(game);
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    private void addNewOperator(Operator operator) {
        try {
            DAOFactory.getInstance().getOperatorsDAO().setOperator(operator);
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    private void updateOperator(Operator operator) {
        try {
            DAOFactory.getInstance().getOperatorsDAO().updateOperator(operator);
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    private void deleteOperator(Operator operator) {
        try {
            DAOFactory.getInstance().getOperatorsDAO().deleteOperator(operator);
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    @FXML
    private void saveAccessRights(ActionEvent actionEvent) {
        IniFileUtil.setIniFileElement("Access Rights", "hideTaskBar", hideTaskBar.isSelected());
        IniFileUtil.setIniFileElement("Access Rights", "disableTaskManager", disableTaskManager.isSelected());
        IniFileUtil.setIniFileElement("Access Rights", "disableKeys", disableKeys.isSelected());
        if (startUp.isSelected()) {
            if (new File(System.getProperty("user.dir") + "\\ORGameManager.exe").exists()) {
                try {
                    File file = new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\StartUp\\ORGameManager.bat");
                    PrintWriter writer = new PrintWriter(file.getAbsoluteFile(), "UTF-8");
                    writer.println("start " + System.getProperty("user.dir") + "\\ORGameManager.exe");
                    writer.close();
                    IniFileUtil.setIniFileElement("Access Rights", "startUp", startUp.isSelected());
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    LOG.error(e);
                }
                accessRightsLbl.setText("Сохранено");
                accessRightsLbl.setTextFill(Paint.valueOf("#02d63c"));
                service.schedule(new Runnable() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                accessRightsLbl.setText("");
                            }
                        });
                    }
                }, 2, TimeUnit.SECONDS);
            } else {
                accessRightsLbl.setText("Сохранено все кроме автозагрузки. Не найден Gamemanager");
                accessRightsLbl.setTextFill(Paint.valueOf("#02d63c"));
                service.schedule(new Runnable() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                accessRightsLbl.setText("");
                            }
                        });
                    }
                }, 2, TimeUnit.SECONDS);
            }
        } else {
            File lnk = new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\StartUp\\ORGameManager.bat");
            if (lnk.exists()) {
                lnk.delete();
            }
            IniFileUtil.setIniFileElement("Access Rights", "startUp", startUp.isSelected());
            accessRightsLbl.setText("Сохранено");
            accessRightsLbl.setTextFill(Paint.valueOf("#02d63c"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            accessRightsLbl.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        }

    }

    @FXML
    private void saveEmailSettings(ActionEvent actionEvent) {
        if (ValidationUtils.isEmailValid(emailAdresat.getText()) && ValidationUtils.isEmailValid(emailSender.getText())) {
            IniFileUtil.setIniFileElement("Email settings", "openNotification", openNotification.isSelected());
            IniFileUtil.setIniFileElement("Email settings", "closeNotification", closeNotification.isSelected());
            IniFileUtil.setIniFileElement("Email settings", "emailAdresat", emailAdresat.getText());
            IniFileUtil.setIniFileElement("Email settings", "emailSender", emailSender.getText());
            IniFileUtil.setIniFileElement("Email settings", "emailPassword", emailPassword.getText());
            IniFileUtil.setIniFileElement("Email settings", "smtp", smtp.getText());
            IniFileUtil.setIniFileElement("Email settings", "port", port.getText());
            emailLbl.setText("Сохранено");
            emailLbl.setTextFill(Paint.valueOf("#02d63c"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            emailLbl.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        } else {
            emailLbl.setText("Не верный email");
            emailLbl.setTextFill(Paint.valueOf("#d30f02"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            emailLbl.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        }
    }

    @FXML
    private void savePasswordAction(ActionEvent actionEvent) throws SQLException {
        if (password.getText().equals(rePassword.getText())) {
            admin.setPassword(rePassword.getText());
            DAOFactory.getInstance().getAdminDAO().updatePassword(admin);
            passwordLbl.setText("Пароль обновлен");
            passwordLbl.setTextFill(Paint.valueOf("#02d63c"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            passwordLbl.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);

        } else {
            passwordLbl.setText("Пароль не обновлен, проверьте правильность повторного ввода");
            passwordLbl.setTextFill(Paint.valueOf("#d30f02"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            passwordLbl.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        }
    }


    public void handleAdsFileChooser(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserAds(fileChooser);
        File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if (file != null) {
            adsTextField.setText(file.getAbsolutePath());
        }
    }

    public void saveAdsSettings(ActionEvent actionEvent) {
        if (ValidationUtils.isAdsExtension(adsTextField.getText())) {
            IniFileUtil.setIniFileElement("Ads settings", "ads", adsTextField.getText());
            adsErrorLabel.setText("Сохранено");
            adsErrorLabel.setTextFill(Paint.valueOf("#02d63c"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            adsErrorLabel.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        } else {
            adsErrorLabel.setText("Проверьти правильность пути и расширения файлов (mp4/avi)");
            adsErrorLabel.setTextFill(Paint.valueOf("#d30f02"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            adsErrorLabel.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        }

    }


    private static void configureFileChooserAds(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Укажите ролик");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("\"MPEG-4 Moive File (*.mp4)\"", "*.mp4"),
                new FileChooser.ExtensionFilter("\"AVI Moive File (*.avi)\"", "*.avi")
        );
    }

    private static void configureFileChooserObs(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Укажите файл запуска OBS");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Exe (*.exe)", "*.exe")
        );
    }

    public void saveOBSAction(ActionEvent actionEvent) {
        if (ValidationUtils.isExe(obsPathField.getText())) {
            IniFileUtil.setIniFileElement("Obs settings", "obs", obsPathField.getText());
            obsErrorLbl.setText("Сохранено");
            obsErrorLbl.setTextFill(Paint.valueOf("#02d63c"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            obsErrorLbl.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        } else {
            obsErrorLbl.setText("Проверьти правильность пути и расширения файла (*.exe)");
            obsErrorLbl.setTextFill(Paint.valueOf("#d30f02"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            obsErrorLbl.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        }
    }

    public void handleOBSFileChooser(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserObs(fileChooser);
        File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if (file != null) {
            obsPathField.setText(file.getAbsolutePath());
        }
    }
}
