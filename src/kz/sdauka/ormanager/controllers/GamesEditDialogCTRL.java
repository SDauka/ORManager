package kz.sdauka.ormanager.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kz.sdauka.ormanager.entity.Game;
import kz.sdauka.ormanager.utils.ValidationUtils;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dauletkhan on 14.01.2015.
 */
public class GamesEditDialogCTRL implements Initializable {
    @FXML
    private TextField gameName;
    @FXML
    private TextField gamePath;
    @FXML
    private TextField gameImagePath;
    @FXML
    private TextField gameTime;
    @FXML
    private TextField gameAttribute;
    @FXML
    private TextField gameCost;
    @FXML
    private Label errorLabel;
    private static Game game;
    private Stage dialogStage;
    private boolean okClicked = false;
    private final FileChooser imageChooser = new FileChooser();
    private final FileChooser gameChooser = new FileChooser();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static final Logger LOG = Logger.getLogger(GamesEditDialogCTRL.class);

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setOkClicked(boolean okClicked) {
        this.okClicked = okClicked;
    }

    public static Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        gameName.setText(game.getName());
        gamePath.setText(game.getPath());
        gameImagePath.setText(game.getImage());
        gameAttribute.setText(game.getAttribute());
        gameTime.setText(String.valueOf(game.getTime()));
        gameCost.setText(String.valueOf(game.getCost()));
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleOK(ActionEvent actionEvent) {

        if (gameName.getText() == null || gamePath.getText() == null || gameTime.getText() == null || gameImagePath.getText() == null || gameCost.getText() == null) {
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
        } else {
            if (gameCost.getText().matches("^[0-9]+$") && gameTime.getText().matches("^[0-9]+$")) {
                game.setName(gameName.getText());
                game.setPath(gamePath.getText());
                game.setImage(gameImagePath.getText());
                game.setAttribute(gameAttribute.getText());
                game.setTime(Integer.parseInt(gameTime.getText()));
                game.setCost(Integer.parseInt(gameCost.getText()));
                okClicked = true;
                dialogStage.close();
            } else {
                errorLabel.setText("время/цена должны быть цифры");
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
//        if (isValid()) {
//            game.setName(gameName.getText());
//            game.setPath(gamePath.getText());
//            game.setImage(gameImagePath.getText());
//            game.setAttribute(gameAttribute.getText());
//            game.setTime(Integer.parseInt(gameTime.getText()));
//            game.setCost(Integer.parseInt(gameCost.getText()));
//            okClicked = true;
//            dialogStage.close();
//        }
    }

    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleImagePath(ActionEvent actionEvent) {
        configureFileChooserIMG(imageChooser);
        File file = imageChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if (file != null) {
            if (ValidationUtils.isImg(file.getAbsolutePath())) {
                gameImagePath.setText(file.getAbsolutePath());
            } else
                Dialogs.create().title("Ошибка выбора файла").message("Выбранный файл не является допустимым файлом изображения").showError();

        } else
            Dialogs.create().title("Ошибка выбора файла").message("Выбранный файл не является допустимым файлом изображения").showError();
    }

    @FXML
    public void handleGamePath(ActionEvent actionEvent) {
        configureFileChooserEXE(gameChooser);
        File file = gameChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if (file != null) {
            gamePath.setText(file.getAbsolutePath());
        } else
            Dialogs.create().title("Ошибка выбора файла").message("Выбранный файл не является допустимым файлом исполнения").showError();
    }

    private static void configureFileChooserIMG(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Укажите изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private static void configureFileChooserEXE(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Укажите файл запуска игры");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("EXE", "*.exe")
        );
    }

    private boolean isValid() {
        if (gameName.getText().isEmpty() || gamePath.getText().isEmpty() || gameTime.getText().isEmpty() || gameImagePath.getText().isEmpty() || gameCost.getText().isEmpty()) {
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
            if (gameCost.getText().matches("^[0-9]+$") && gameTime.getText().matches("^[0-9]+$")) {
                return true;
            } else {
                errorLabel.setText("время/цена должны быть цифры");
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
            }

        }
    }
}
