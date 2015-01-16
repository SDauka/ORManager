package kz.sdauka.ormanager;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("/fxml/loginForm.fxml"));
        primaryStage.setTitle("ORManager");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("/img/icon.png")
        );
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(1);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
