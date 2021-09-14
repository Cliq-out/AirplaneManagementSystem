package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.AppSession;

import static javafx.application.Application.launch;

public class FabianTestKilometerKonto extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/KilometerKonto.fxml"));
        stage.setTitle("KilometerKonto");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        AppSession.getIntance().setCustomerUsername("FabianTestKunde");
        launch(args);
    }
}
