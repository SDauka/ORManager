package kz.sdauka.ormanager.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kz.sdauka.ormanager.dao.factory.DAOFactory;
import kz.sdauka.ormanager.entity.Admin;
import kz.sdauka.ormanager.entity.Game;
import kz.sdauka.ormanager.entity.Operator;
import kz.sdauka.ormanager.utils.IniFileUtil;
import kz.sdauka.ormanager.utils.ValidationUtils;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private CheckBox disableAltF4;
    @FXML
    private CheckBox disableAltTab;
    @FXML
    private CheckBox disableWin;
    @FXML
    private CheckBox openNotification;
    @FXML
    private CheckBox closeNotification;
    @FXML
    private TextField emailAdresat;
    @FXML
    private TextField emailSender;
    @FXML
    private TextField emailPassword;
    @FXML
    private TextField smtp;
    @FXML
    private TextField port;
    @FXML
    private TextField password;
    @FXML
    private TextField rePassword;
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
    private TableColumn<Operator, String> loginOperators;
    @FXML
    private TableColumn<Operator, String> passwordOperators;
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
    private Admin admin;
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAdmin();
        idGames.setCellValueFactory(new PropertyValueFactory<Game, Integer>("id"));
        nameGames.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
        pathGames.setCellValueFactory(new PropertyValueFactory<Game, String>("path"));
        imageGames.setCellValueFactory(new PropertyValueFactory<Game, String>("image"));
        attributeGames.setCellValueFactory(new PropertyValueFactory<Game, String>("attribute"));
        timeGames.setCellValueFactory(new PropertyValueFactory<Game, Integer>("time"));
        costGames.setCellValueFactory(new PropertyValueFactory<Game, Integer>("cost"));
        idOperators.setCellValueFactory(new PropertyValueFactory<Operator, Integer>("id"));
        nameOperators.setCellValueFactory(new PropertyValueFactory<Operator, String>("name"));
        loginOperators.setCellValueFactory(new PropertyValueFactory<Operator, String>("login"));
        passwordOperators.setCellValueFactory(new PropertyValueFactory<Operator, String>("password"));
        operatorsTable.setItems(getAllOperators());
        gamesTable.setItems(getAllGames());
        password.setText(admin.getPassword());
        hideTaskBar.setSelected(IniFileUtil.getSetting().isHideTaskBar());
        disableAltF4.setSelected(IniFileUtil.getSetting().isDisableAltF4());
        disableAltTab.setSelected(IniFileUtil.getSetting().isDisableAltTab());
        disableTaskManager.setSelected(IniFileUtil.getSetting().isDisableTaskManager());
        disableWin.setSelected(IniFileUtil.getSetting().isDisableWin());
        openNotification.setSelected(IniFileUtil.getSetting().isOpenNotification());
        closeNotification.setSelected(IniFileUtil.getSetting().isCloseNotification());
        emailAdresat.setText(IniFileUtil.getSetting().getEmailAdresat());
        emailPassword.setText(IniFileUtil.getSetting().getEmailPassword());
        emailSender.setText(IniFileUtil.getSetting().getEmailSender());
        smtp.setText(IniFileUtil.getSetting().getSmtp());
        port.setText(IniFileUtil.getSetting().getPort());
        addOperatorBtn.setGraphic(new ImageView("/img/user_add.png"));
        editOperatorBtn.setGraphic(new ImageView("/img/user_edit.png"));
        deleteOperatorBtn.setGraphic(new ImageView("/img/user_remove.png"));
        addGameBtn1.setGraphic(new ImageView("/img/games_control_add.png"));
        editGameBtn1.setGraphic(new ImageView("/img/games_control_pencil.png"));
        deleteGameBtn1.setGraphic(new ImageView("/img/games_control_close.png"));
        reportExcelBtn.setGraphic(new ImageView("/img/excel.png"));
        searchBtn.setGraphic(new ImageView("/img/find.png"));
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
            dialogStage.initModality(Modality.WINDOW_MODAL);
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
            e.printStackTrace();
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
            dialogStage.initModality(Modality.WINDOW_MODAL);
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
            e.printStackTrace();
            return false;
        }
    }

    private void getAdmin() {
        try {
            admin = DAOFactory.getInstance().getAdminDAO().getAdmin(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Operator> getAllOperators() {
        ObservableList<Operator> operatorList = FXCollections.observableArrayList();
        try {
            operatorList.addAll(DAOFactory.getInstance().getOperatorsDAO().getAllOperators());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return operatorList;
    }

    private ObservableList<Game> getAllGames() {
        ObservableList<Game> gamesList = FXCollections.observableArrayList();
        try {
            gamesList.addAll(DAOFactory.getInstance().getGamesDAO().getAllGames());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gamesList;
    }

    private void addNewGame(Game game) {
        try {
            DAOFactory.getInstance().getGamesDAO().setGame(game);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateGame(Game game) {
        try {
            DAOFactory.getInstance().getGamesDAO().updateGame(game);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteGame(Game game) {
        try {
            DAOFactory.getInstance().getGamesDAO().deleteGame(game);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addNewOperator(Operator operator) {
        try {
            DAOFactory.getInstance().getOperatorsDAO().setOperator(operator);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateOperator(Operator operator) {
        try {
            DAOFactory.getInstance().getOperatorsDAO().updateOperator(operator);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteOperator(Operator operator) {
        try {
            DAOFactory.getInstance().getOperatorsDAO().deleteOperator(operator);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveAccessRights(ActionEvent actionEvent) {
        IniFileUtil.setIniFileElement("Access Rights", "hideTaskBar", hideTaskBar.isSelected());
        IniFileUtil.setIniFileElement("Access Rights", "disableTaskManager", disableTaskManager.isSelected());
        IniFileUtil.setIniFileElement("Access Rights", "disableAltF4", disableAltF4.isSelected());
        IniFileUtil.setIniFileElement("Access Rights", "disableAltTab", disableAltTab.isSelected());
        IniFileUtil.setIniFileElement("Access Rights", "disableWin", disableWin.isSelected());
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

}
