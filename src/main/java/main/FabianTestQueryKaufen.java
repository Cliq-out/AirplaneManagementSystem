package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.AppSession;

public class FabianTestQueryKaufen extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdministratorMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Administrator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        AppSession.getIntance().setId_manager(472); //mein test manager
        AppSession.getIntance().setId_gesellschaft(472); //meine test gesellschaft
        launch(args);
    }
}
