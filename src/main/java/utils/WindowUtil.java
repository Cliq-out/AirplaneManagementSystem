package utils;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import models.FlugGesManager;

import java.io.IOException;

public class WindowUtil {

    public static FlugGesManager eingeloggt =null;


    public static FlugGesManager getEingeloggt() {
        return eingeloggt;
    }

    public static void setEingeloggt(FlugGesManager eingeloggt) {
        WindowUtil.eingeloggt = eingeloggt;
    }


    public static void alertWindow ( String title, Alert.AlertType type, String notification){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(notification);
        alert.showAndWait();
    }

    //noch nicht benutzt
    public static void loadWindow(String sourceFXML, Class klasse) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(klasse.getClassLoader().getResource(sourceFXML));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
