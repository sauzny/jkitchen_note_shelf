package org.example.sauzny;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader sceneFxmlLoader = new FXMLLoader(App.class.getResource("/fxml/scene.fxml"));
        Scene scene = new Scene(sceneFxmlLoader.load(), 640, 480);

        stage.setScene(scene);
        stage.setTitle("我的JavaFX");  // Set the stage title
        stage.getIcons().add(new Image("/favicon.jpg"));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}