package kz.sdauka.ormanager.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kz.sdauka.ormanager.dao.factory.DAOFactory;
import kz.sdauka.ormanager.entity.Operator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dauletkhan on 13.01.2015.
 */
public class OperatorsEditDialogCTRL implements Initializable {
    @FXML
    private TextField nameText;
    @FXML
    private Label errorLabel;
    private static Operator operator;
    private Stage editDialogStage;
    private boolean okClicked = false;
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static Operator getOperator() {
        return operator;
    }

    private void addNewOperator(Operator operator) {
        try {
            DAOFactory.getInstance().getOperatorsDAO().setOperator(operator);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Stage getEditDialogStage() {
        return editDialogStage;
    }

    public void setEditDialogStage(Stage editDialogStage) {
        this.editDialogStage = editDialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setOkClicked(boolean okClicked) {
        this.okClicked = okClicked;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
        nameText.setText(operator.getName());
    }

    @FXML
    private void handleOK(ActionEvent actionEvent) {
        if (isValid()) {
            operator.setName(nameText.getText());
            okClicked = true;
            editDialogStage.close();
        }
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        editDialogStage.close();
    }

    private boolean isValid() {
        if (nameText.getText().equals("")) {
            errorLabel.setText("Заполните все поля");
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
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
