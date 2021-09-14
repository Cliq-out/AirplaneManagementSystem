package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Flugzeuge;
import models.SessionFactoryUtil;
import org.hibernate.query.Query;
import utils.WindowUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FlugzeugeAnsehenController {

    @FXML
    private TableView<Flugzeuge> tableFlugzeuge;

    @FXML
    private TableColumn<Flugzeuge, String> idcol1;//  hersteller;

    @FXML
    private TableColumn<Flugzeuge, String> idcol2;//flugzeugtyp

    @FXML
    private TableColumn<Flugzeuge,Integer> idcol3;//anzahl_Sitzplaetze

    @FXML
    private TableColumn<Flugzeuge, Integer> idcol4;//geschwindigkeit

    @FXML
    private TableColumn<Flugzeuge, Double> idcol5;//preis_in_Mio

    @FXML
    private TableColumn<Flugzeuge, Integer> idcol18;//reichweite

    @FXML
    private Button btnAbbrechen;

    @FXML
    private TextField file_tf;

    @FXML
    private Button load_bt;

    @FXML
    private Button readCSV;


    @FXML
    void abbrechen(ActionEvent event) {
        Stage stage= (Stage) btnAbbrechen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void readcsv(ActionEvent event) {

    }


    @FXML
    void loadFile(ActionEvent event) {

        boolean isOK=false;
        String msg ="";
        //read scv
        ArrayList<Flugzeuge> datas = readCSV();
        if(datas.size()>0) {
            //save in database
            if (saveFlugzeuge(datas)) {
                if(fillGridFlugzeuge()){
                    isOK=true;
                    msg ="Load ok";
                }else{
                    msg ="Load not ok";
                }
            } else {
                msg ="Error by saving in DB";

            }
        }else{
            msg ="Error by reading CSV File";
        }

        if(isOK){
            WindowUtil.alertWindow("Richtig", Alert.AlertType.CONFIRMATION, msg);
        }{
            WindowUtil.alertWindow("Richtig", Alert.AlertType.ERROR, msg);
        }

    }

    public boolean saveFlugzeuge(ArrayList<Flugzeuge> datas)
    {
        org.hibernate.Session session =null;
        try{
            session = SessionFactoryUtil.getInstance().getSession();
            session.beginTransaction();
            for (Flugzeuge flugzeuge:datas
            ) {
                session.persist(flugzeuge);
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

    public ArrayList<Flugzeuge> readCSV(){
        String filename= file_tf.getText();//fill with path where the data is

        ArrayList<Flugzeuge> datas = new ArrayList<Flugzeuge>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        int rowNumber=0;
        while (true) {
            try {
                line = reader.readLine();
                if (line==null) break;//jump falls nicht gefunden wird
            } catch (IOException e) {
                e.printStackTrace();
            }
            rowNumber++;

            if(rowNumber == 1){//jump the header-and continue jumping during the rest of the code;
                continue;
            }

            String[] columns = line.split( ";" );//separated by ;
            Flugzeuge data = new Flugzeuge();
            data.setHersteller(columns[0]);
            data.setFlugzeugtyp(columns[1]);
            data.setAnzahl_Sitzplaetze(Integer.parseInt(columns[2]));
            data.setGeschwindigkeit(Integer.parseInt(columns[3]));
            data.setPreis_in_Mio(Double.parseDouble(columns[4]));
            data.setReichweite(Integer.parseInt(columns[5]));
            datas.add( data );
        }
        return datas;
    }

    @FXML
    void selectFile(MouseEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione un archivo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showOpenDialog(file_tf.getScene().getWindow());
        //open window fileChooser inside the main window
        // (the window that contains the file_tf)
        if(file!=null) {
            file_tf.setText(file.getAbsolutePath());
        }else{
            file_tf.setText("");
        }
    }

    @FXML
    public void initialize() {
        //Hersteller;Flugzeugtyp;Anzahl_Sitzplaetze;Geschwindigkeit;Preis_in_Mio;Reichweite
        idcol1.setCellValueFactory(new PropertyValueFactory<>("hersteller"));//Hersteller
        idcol2.setCellValueFactory(new PropertyValueFactory<>("flugzeugtyp"));//Model
        idcol3.setCellValueFactory(new PropertyValueFactory<>("anzahl_Sitzplaetze"));//Size
        idcol4.setCellValueFactory(new PropertyValueFactory<>("geschwindigkeit"));//Kerosineverbrauch?
        idcol5.setCellValueFactory(new PropertyValueFactory<>("preis_in_Mio"));//Preis
        idcol18.setCellValueFactory(new PropertyValueFactory<>("reichweite"));//State?
        fillGridFlugzeuge();
    }

    public boolean fillGridFlugzeuge() {
        try{
            org.hibernate.Session session = SessionFactoryUtil.getInstance().getSession();
            Query<Flugzeuge> query = session.createQuery("FROM Flugzeuge");
            List<Flugzeuge> flugzeugeList = query.list();
            tableFlugzeuge.getItems().clear();//clean if exists data

            for (Flugzeuge fm : flugzeugeList) {
                tableFlugzeuge.getItems().add(fm);
            }
            return  true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}

