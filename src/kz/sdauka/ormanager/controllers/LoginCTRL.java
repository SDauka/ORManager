package kz.sdauka.ormanager.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kz.sdauka.ormanager.dao.factory.DAOFactory;
import kz.sdauka.ormanager.entity.Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dauletkhan on 10.01.2015.
 */
public class LoginCTRL implements Initializable {
    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    private Admin admin;


    @FXML
    private void loginAction(ActionEvent event) throws SQLException, IOException {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settingsForm.fxml"));
        if (login.getText().equals(admin.getLogin())) {
            if (password.getText().equals(admin.getPassword())) {
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Настройки");
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image("/img/icon.png"));

                stage.show();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.exit(1);
                    }
                });
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                errorLabel.setText("Неправильный пароль/логин");
                errorLabel.setTextFill(Paint.valueOf("#d30f02"));
                service.schedule(new Runnable() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                errorLabel.setText("");
                            }
                        });
                    }
                }, 2, TimeUnit.SECONDS);
            }
        } else {
            errorLabel.setText("Неправильный пароль/логин");
            errorLabel.setTextFill(Paint.valueOf("#d30f02"));
            service.schedule(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            errorLabel.setText("");
                        }
                    });
                }
            }, 2, TimeUnit.SECONDS);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAdmin();
    }

    private void getAdmin() {
        try {
            admin = DAOFactory.getInstance().getAdminDAO().getAdmin(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
