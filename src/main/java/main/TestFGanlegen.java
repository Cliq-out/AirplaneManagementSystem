package main;
        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import models.SessionFactoryUtil;

        import javax.mail.Session;
        import java.util.Objects;

public class TestFGanlegen extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/FluggesellschaftAnlegen.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Fluggesellschaften");
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) {

        launch(args);
    }

}
