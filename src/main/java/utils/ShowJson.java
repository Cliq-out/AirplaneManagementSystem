
package utils;

import models.Flughaefen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ShowJson {
        public static void main(String[] args){

            Path currentRelativePath = Paths.get("");//get the root of the path
            String s = currentRelativePath.toAbsolutePath().toString();
            String filename1 = s+"\\target\\classes\\flughaefen.json";//full path where the data is

            //Deserialize
            final Type review_Type = new TypeToken<List<Flughaefen>>(){}.getType();
            //token generic class gson um Json abzulesen
            //interfaz um Types zu erstellen
            Gson gson = new Gson();
            JsonReader reader = null;
            try {
                reader = new JsonReader(new FileReader(filename1));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            List<Flughaefen> data = gson.fromJson(reader, review_Type); // contains the whole reviews list


            for (Flughaefen fh: data ){
                Double val1 =fh.getLon();
                Integer val2= fh.getRunway_length();

            if ((fh.getCode().length() != 0) || (fh.getCity().length() != 0) ||
                    (val1 != null) || (val2!=null)){
                data.forEach(t->System.out.println(t.toString()));}
            }
              //code, lon x, city, country, runway_length x!= leer
            // data.forEach(t->System.out.println(t.toString()));}
        }
}