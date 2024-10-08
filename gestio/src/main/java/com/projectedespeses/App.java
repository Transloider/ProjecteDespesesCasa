package com.projectedespeses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


import com.projectedespeses.controller.LoginController;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login.fxml"));
            loader.setControllerFactory(t -> new LoginController());
            Parent root = loader.load();
            Scene scene = new Scene(root, 413, 310);
            stage.setTitle("Login"); 
            stage.setResizable(false);          
            stage.setScene(scene); 
            stage.show(); 
            LoginController lc = loader.getController();
            lc.setStage(stage);
            
        } catch (IOException ex) {
            ex.addSuppressed(ex);
        }
    }
}