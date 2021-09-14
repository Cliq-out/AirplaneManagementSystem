package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Flughaefen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Flugzeuge;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FlughaefenZeigenController {
    @FXML
    private TableView<Flughaefen> tableFlughafen;

    @FXML
    private TableColumn<Flughaefen, String> idcol1;//code

    @FXML
    private TableColumn<Flughaefen, Double> idcol2;//lat

    @FXML
    private TableColumn<Flughaefen, Double> idcol3;//lon

    @FXML
    private TableColumn<Flughaefen, String> idcol4;//name

    @FXML
    private TableColumn<Flughaefen,String> idcol5;//city

    @FXML
    private TableColumn<Flughaefen, String> idcol18;//state

    @FXML
    private TableColumn<Flughaefen, String> idcol6;//country

    @FXML
    private TableColumn<Flughaefen, Long> idcol7;//woeid

    @FXML
    private TableColumn<Flughaefen, String> idcol8;//tz

    @FXML
    private TableColumn<Flughaefen,String> idcol9;//phone

    @FXML
    private TableColumn<Flughaefen, String> idcol10;//type

    @FXML
    private TableColumn<Flughaefen, String> idcol11;//email

    @FXML
    private TableColumn<Flughaefen, String> idcol12;//url

    @FXML
    private TableColumn<Flughaefen, Integer> idcol13;//runway

    @FXML
    private TableColumn<Flughaefen, Integer> idcol14;//elev

    @FXML
    private TableColumn<Flughaefen, String> idcol15;//icao

    @FXML
    private TableColumn<Flughaefen, Integer> idcol16;//direct_flight

    @FXML
    private TableColumn<Flughaefen, Integer> idcol17;//carriers


    @FXML
    private Button btnAbbrechen;

    @FXML
    private Button load_bt;

    @FXML
    private TextField file_tf;


    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage = (Stage)btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void loadFile(ActionEvent event) {
        boolean isOK=false;
        String msg ="";
        //read scv
        ArrayList<Flughaefen> datas = readJSON();
        if(datas.size()>0) {
            //save in database
            if (saveFlughaefen(datas)) {
                if(fillGridFlughaefen()){
                    isOK=true;
                    msg ="mostrar ok OK";
                }else{
                    msg ="mostrar bad OK";
                }
            } else {
                msg ="BD BAD";

            }
        }else{
            msg ="READ JSON BAD BAD";
        }

        if(isOK){
            WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, msg);
        }{
            WindowUtil.alertWindow("Richtig", Alert.AlertType.ERROR, msg);
        }

    }

    public boolean saveFlughaefen(ArrayList<Flughaefen> datas)
    {
        org.hibernate.Session session =null;
        try{
            session = SessionFactoryUtil.getInstance().getSession();
            session.beginTransaction();
            for (Flughaefen flughaefen:datas
            ) {
                session.persist(flughaefen);
            }
            //session.saveOrUpdate(datas);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            try{
                session.getTransaction().rollback();
            }catch (Exception e1){

            }
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Flughaefen> readJSON(){
        String filename= file_tf.getText();//path where the data is
        //Deserialize
        final Type review_type = new TypeToken<List<Flughaefen>>(){}.getType();
        //token generic class de gson permite leer un gson
        //interfaz para crear types
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Flughaefen> flugList = gson.fromJson(reader, review_type); // contains the whole reviews list
        return flugList;
    }

    @FXML
    void selectFile(MouseEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wählen Sie einen File aus");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        File file = fileChooser.showOpenDialog(file_tf.getScene().getWindow());
        //abre la ventana fileChooser dentro de la ventan principal(la ventan que contiene al elemento tf_archivo-es decir la ventana papá)
        if(file!=null) {
            file_tf.setText(file.getAbsolutePath());
        }else{
            file_tf.setText("");
        }
    }


    @FXML
    public void initialize() {
        idcol1.setCellValueFactory(new PropertyValueFactory<>("code"));
        idcol2.setCellValueFactory(new PropertyValueFactory<>("lat"));
        idcol3.setCellValueFactory(new PropertyValueFactory<>("lon"));
        idcol4.setCellValueFactory(new PropertyValueFactory<>("name"));
        idcol5.setCellValueFactory(new PropertyValueFactory<>("city"));
        idcol18.setCellValueFactory(new PropertyValueFactory<>("state"));
        idcol6.setCellValueFactory(new PropertyValueFactory<>("country"));
        idcol7.setCellValueFactory(new PropertyValueFactory<>("woeid"));
        idcol8.setCellValueFactory(new PropertyValueFactory<>("tz"));
        idcol9.setCellValueFactory(new PropertyValueFactory<>("phone"));
        idcol10.setCellValueFactory(new PropertyValueFactory<>("type"));
        idcol11.setCellValueFactory(new PropertyValueFactory<>("email"));
        idcol12.setCellValueFactory(new PropertyValueFactory<>("url"));
        idcol13.setCellValueFactory(new PropertyValueFactory<>("runway_length"));
        idcol14.setCellValueFactory(new PropertyValueFactory<>("elev"));
        idcol15.setCellValueFactory(new PropertyValueFactory<>("icao"));
        idcol16.setCellValueFactory(new PropertyValueFactory<>("direct_flights"));
        idcol17.setCellValueFactory(new PropertyValueFactory<>("carriers"));
        fillGridFlughaefen();
    }


    public boolean fillGridFlughaefen() {
        try{
            org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
            Query<Flughaefen> query = session.createQuery("FROM Flughaefen");
            List<Flughaefen> flughaefenList = query.list();
            tableFlughafen.getItems().clear();//clean if exists data

            for (Flughaefen fh : flughaefenList) {
                tableFlughafen.getItems().add(fh);
            }
            return  true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}