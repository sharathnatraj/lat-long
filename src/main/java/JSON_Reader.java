/**
 * Created by A-6997 on 29-03-2017.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSON_Reader {
    public static void main(String args[]) {
        JSONParser parser = new JSONParser();
        try {

            Object object = parser
                    .parse(new FileReader("/D:/Sprint24/Data/json input.json"));

            //convert Object to JSONObject
            JSONObject jsonObject = (JSONObject) object;

            //Reading the String
            String name = (String) jsonObject.get("Name");
            Long age = (Long) jsonObject.get("Age");

            //Reading the array
            JSONArray countries = (JSONArray) jsonObject.get("Countries");

            //Printing all the values
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Countries:");
            for (Object country : countries) {
                System.out.println("\t" + country.toString());
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}