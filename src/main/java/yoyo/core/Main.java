package yoyo.core;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import yoyo.ui.MainWindow;

/**
 * A GUI for Yoyo using FXML.
 */
public class Main extends Application {

    private final YoyoAdapter yoyo = new YoyoAdapter();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            // Load CSS stylesheets
            scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/css/dialog-box.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Yoyo");
            stage.setResizable(true);
            stage.setMinWidth(550);
            stage.setMinHeight(500);
            stage.setWidth(650);
            stage.setHeight(600);
            fxmlLoader.<MainWindow>getController().setYoyoAdapter(yoyo);  // inject the YoyoAdapter instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
