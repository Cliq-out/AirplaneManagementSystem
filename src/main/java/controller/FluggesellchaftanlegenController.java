package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.*;
import org.hibernate.query.Query;
import utils.WindowUtil;
import java.util.List;


public class FluggesellchaftanlegenController {

    @FXML
    private TextField fgName;

    @FXML
    private TextField fgLand;

    @FXML
    private TextField fgBudget;

    @FXML
    private Button btnAnlegen;

    @FXML
    private Button btnAbbrechen;

    String user_ma = AppSession.getIntance().getUsername();

    @FXML
    void Abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();

    }

    @FXML
    void weiter(ActionEvent event) {

        org.hibernate.Session session = SessionFactoryUtil.getInstance().getSessionFactory().openSession();
        Query<FlugGesManager> query00 = session.createQuery("FROM FlugGesManager m WHERE m.username = :user_man");

        query00.setParameter("user_man",user_ma);
        List<FlugGesManager> managerList = query00.list();
        FlugGesManager rec = managerList.get(0);
        int id_ma = rec.getId_manager();
        Fluggesellschaft fg = new Fluggesellschaft();

        fg.setName(fgName.getText());
        fg.setLand(fgLand.getText());
        fg.setId_manager(id_ma);
        double budg = Double.parseDouble(fgBudget.getText());
        fg.setBudget(budg);

        Query<Fluggesellschaft> query01 = session.createQuery("FROM Fluggesellschaft fa WHERE fa.id_manager = :idman");
        query01.setParameter("idman",id_ma);
        List<Fluggesellschaft> fluggesellschaftList = query01.list();

        if (fluggesellschaftList.size()==0 ) {
            session.beginTransaction();
            session.save(fg);
            WindowUtil.alertWindow("Richtig", Alert.AlertType.INFORMATION, "Die Fluggesellschaft wurde erfolgreich hinzugefügt");
            fgName.setText("");
            fgLand.setText("");
            fgBudget.setText("");
            //bugfix fabian; wenn man ein neuer manager ist und seine fluggesellschaft frisch angelegt hat dann muss das in die appsession
            Query<Fluggesellschaft> query02 = session.createQuery("FROM Fluggesellschaft fa WHERE fa.id_manager = :idman");
            query02.setParameter("idman",id_ma);
            fluggesellschaftList = query02.list();
            AppSession.getIntance().setId_gesellschaft(fluggesellschaftList.get(0).getId_fluggesellschaft());
            AppSession.getIntance().setGesellshaftname(fluggesellschaftList.get(0).getName());
            session.getTransaction().commit();
            session.close();
        }

        else  {
            WindowUtil.alertWindow("Falsch", Alert.AlertType.ERROR, "Sie haben schon eine Fluggesellschaft zugewiesen");
            fgName.setText("");
            fgLand.setText("");
            fgBudget.setText("");
        }
    }

    @FXML
    public void initialize() {
    }

}


