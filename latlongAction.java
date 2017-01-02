/**
 * Created by snataraj on 12/31/16.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class latlongAction {

    public static void main(String[] args) throws ParseException, IOException {


        //Reading CSV File
        String csvfile = "/Users/snataraj/documents/expedia/sabre/car_geo_locations_non_airport.csv";
        String outputcsvfile = "/Users/snataraj/documents/expedia/sabre/car_geo_locations_non_airport_output.csv";
        BufferedReader br = null;
        BufferedWriter bw = null;
        String google_lat = null;
        String google_lng = null;
        Double offset_lat;
        Double offset_lng;
        String fileContent;

        try {
            br = new BufferedReader(new FileReader(csvfile));
            bw = new BufferedWriter(new FileWriter(outputcsvfile));
            if (br != null) {
                String linecsv = "";
                //Ignoring the header
                linecsv = br.readLine();
                //writing the header
                String header = linecsv + "," + "Google maps Lang" + "," + "Google maps lat" + "," + "Offset Lang" + "," + "Offset Lat";
                bw.write(header);
                bw.write("\n");
                while ((linecsv = br.readLine()) != null) {
                    //System.out.println("The line is " + linecsv);
                    String[] word = linecsv.split(",");
                    //String Address = word[3] + "," + word[4] + "," + word[5] + "," + word[6];
                    String Address = word[3] + word[4] + word[5] + word[6] + word[7] + word [8];
                    Address = Address.replace(" ", "%20");
                    //System.out.println("Address is" + Address);
                    //Construct URL
                    String Url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
                    Url = Url.concat(Address);

                    InputStream inputStream = null;
                    String json = "";

                    try {
                        HttpClient client = HttpClientBuilder.create().build();
                        System.out.println("Url is : " + Url);
                        HttpPost post = new HttpPost(Url);
                        HttpResponse response = client.execute(post);
                        HttpEntity entity = response.getEntity();
                        inputStream = entity.getContent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                        StringBuilder sbuild = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sbuild.append(line);
                        }
                        inputStream.close();
                        json = sbuild.toString();
                    } catch (Exception e) {
                    }


                    //now parse
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(json);
                    JSONObject jb = (JSONObject) obj;

                    //now read
                    String jsonStatus = (String) jb.get("status");
                    google_lat =  google_lng = "NA";
                    offset_lat = offset_lng = 0.00;
                    if (jsonStatus.equals("OK")) {
                        JSONArray jsonObject1 = (JSONArray) jb.get("results");
                        JSONObject jsonObject2 = (JSONObject) jsonObject1.get(0);
                        JSONObject jsonObject3 = (JSONObject) jsonObject2.get("geometry");
                        JSONObject location = (JSONObject) jsonObject3.get("location");


                        System.out.println("Lat = " + location.get("lat"));
                        System.out.println("Lng = " + location.get("lng"));
                        google_lat = location.get("lat").toString();
                        google_lng = location.get("lng").toString();
                        offset_lng = Double.parseDouble(google_lng) - Double.parseDouble(word[10]);
                        offset_lat = Double.parseDouble(google_lat) - Double.parseDouble(word[11]);
                    }
                    //write contents
                    fileContent = linecsv + "," + google_lng + "," + google_lat + "," + offset_lng.toString() + "," + offset_lat.toString();
                    bw.write(fileContent);
                    bw.write("\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
                bw.close();
            }
        }

    }
}


