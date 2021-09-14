package utils;

import models.Flugzeuge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ShowCsv {

    public static void main(String[] args) {
        Path currentRelativePath = Paths.get("");//get the root of the path
        String s = currentRelativePath.toAbsolutePath().toString();
        String filename2 = s+"\\target\\classes\\flugzeuge.csv";//full path where the data is
        String filename02 = "resources/flugzeuge.csv";//intelliJ

        ArrayList<Flugzeuge> datas = new ArrayList<Flugzeuge>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        int rowNumber=0;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;//jump when es nichts findet
            } catch (IOException e) {
                e.printStackTrace();
            }
            rowNumber++;

            if(rowNumber == 1){//jump the header-si es primera linea, continua la ejecución saltando el resto codigo de siguient while
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

        datas.forEach(t->System.out.println(t.toString()));
    }

}


