/**
 * Created by snataraj on 12/31/16.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LatLongAction {

    public static void main(String[] args) throws ParseException, IOException {


        String csvfile = "/D:/CVLData/CVLOutput/Provider Location Details.csv";
        String combCVLAndPLAirport = "/D:/CVLData/CVLOutput/Combined CVL Location Provider Location Details Airport.csv";
        String combCVLAndPLNonAirport = "/D:/CVLData/CVLOutput/Combined CVL Location Provider Location Details NonAirport.csv";
        String SabreData="/D:/CVLData/CVLOutput/DataToSabre.csv";
        String csvfileSabre = "/D:/CVLData/CVLInput/Vendor Locations.csv";
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("/D:/CVLData/CVLInput/Vendor unmatched data.json"));
//        JSONObject jsonObject = (JSONObject) object;

     //Trying to parse the entire file obtained
      /*  Gson gson1 = new GsonBuilder().create();
        JsonObject job = gson1.fromJson(jsonObject.toString(), JsonObject.class);
        JsonElement entry=job.getAsJsonObject("supplierLocationMatchesList").getAsJsonArray("unMatchedLocationList");

        String str = entry.toString();
    */

        try{
            //header to ProviderDataExcelFile
            String header = "Key"+","+"ProviderID"+","+"SupplierID"+","+"iataCode"+","+"locationCode"+","+"locationName"+","+"StreetAddress"+","+"CityName"+","+"StateProvinceCode"+","+"PostalCode"+","+"CountryCode"+","+"PhoneNumber"+","+"FaxNumber"+","+"Latitude"+","+"Longitude"+","+"LocationType"+","+"ShuttleCatagory"+","+"deliveryBool"+","+"collectionBool"+","+"outOfOfficeBool"+","+"NA"+","+"Google maps Latitude" + "," + "Google maps longitude" + "," +"Dist_ProLocations";
            String CVLheader = "Key"+","+"ProviderID"+","+"SupplierID"+","+"iataCode"+","+"locationCode"+","+"locationName"+","+"StreetAddress"+","+"CityName"+","+"StateProvinceCode"+","+"PostalCode"+","+"CountryCode"+","+"PhoneNumber"+","+"FaxNumber"+","+"Latitude"+","+"Longitude"+","+"LocationType"+","+"ShuttleCatagory"+","+"deliveryBool"+","+"collectionBool"+","+"outOfOfficeBool"+","+"CarVendorLocationID"+","+"Google maps Latitude" + "," + "Google maps longitude" + "," +"Dist_CorrectedProLocAndRawCVLLoc"+","+"Dist_CorrectedCVLLocAndCorrectedPLLoc"+","+"Dist_RawCVLAndRawPL";
            String sabreDataHeader="Key"+","+"U_ID"+","+"ID"+","+"locationName"+","+"streetAddress"+","+"cityName"+","+"COUNTYREGION"+","+"stateProvinceCode"+","+"isoCountryCode"+","+"postalCode"+","+"GEOLEVEL"+","+"longitude"+","+"latitude"+","+"PHONETIC_CODE"+","+"supplier"+","+"iataCode"+","+"locationCode"+","+"TIMESTAMP"+","+"Corrected_Latitude"+","+"Corrected_Longitude"+","+"Corrected_StreetAddress"+","+"Corrected_CityName"+","+"Corrected_isoCountryCode";
          //  Object object = parser.parse(new FileReader("/D:/Sprint24/Data/json file unmatched alamo.json"));
          //  JSONObject jsonObject = (JSONObject) object;
            JSONArray jsonArray = (JSONArray) object;
            List<LocationData> locDataList=new ArrayList<LocationData>();

           for (int i = 0, size = jsonArray.size(); i < size; i++) {

               Object jobject= jsonArray.get(i);
               Gson gson = new Gson();
               LocationData locData = gson.fromJson(jobject.toString(), LocationData.class);
               locDataList.add(locData);
               System.out.println(locDataList.size());
            }


            BufferedWriter bwProviderDataContent = new BufferedWriter(new FileWriter(csvfile));
            boolean successPL=writeHeaderForCSVFile(bwProviderDataContent,header);
            BufferedWriter bwCVLDataContentAirport = new BufferedWriter(new FileWriter(combCVLAndPLAirport));
            boolean successCL=writeHeaderForCSVFile(bwCVLDataContentAirport,CVLheader);
            BufferedWriter bwCVLDataContentNonAirport = new BufferedWriter(new FileWriter(combCVLAndPLNonAirport));
            boolean successCVLNonAirport=writeHeaderForCSVFile(bwCVLDataContentNonAirport,CVLheader);
            BufferedWriter bwSabreDataContent = new BufferedWriter(new FileWriter(SabreData));
            boolean sabreSuccessCVL=writeHeaderForCSVFile(bwSabreDataContent,sabreDataHeader);
            int count=1;

            for(LocationData locObj : locDataList){

                double diffInProDataAndCorrectedProData;
                double proLat,proLong;
                boolean flag=false;
                StringBuilder providerDataAddress=new StringBuilder();
                List<CvlLocDataList> CvlLocDataList=locObj.getCvlLocDataList();
                //String providerDataAddress=locObj.getProviderData().getStreetAddress().concat(locObj.getProviderData().getCityName()).concat(locObj.getProviderData().getCountryCode());
                String providerLocationName = locObj.getProviderData().getLocationName();
                String providerStreetAddress=locObj.getProviderData().getStreetAddress();
                String providerCity=locObj.getProviderData().getCityName();
                String providerCountry=locObj.getProviderData().getCountryCode();
                providerDataAddress.append(providerLocationName).append(",").append(providerStreetAddress).append(",").append(providerCity).append(",").append(providerCountry);
                Cordinates proCordinates=getCorrectLatLong(providerDataAddress.toString());
                proLat=proCordinates.getLatitude();
                proLong=proCordinates.getLongitude();
                if(proLat==0 && proLong==0){
                    diffInProDataAndCorrectedProData=-1;
                    proLat=0;
                    proLong=0;
                    //proLat= locObj.getProviderData().getLatitude();
                    //proLong=locObj.getProviderData().getLongitude();
                }
                else{

                    diffInProDataAndCorrectedProData=distance(locObj.getProviderData().getLatitude(),locObj.getProviderData().getLongitude(),proLat,proLong,0,0);

                }
                boolean isSuccessful=writeProviderLocationDataToCSVFile(locObj.getProviderData(),proLat,proLong,diffInProDataAndCorrectedProData,bwProviderDataContent,count,flag);

                SabreData sabreData=getDataFromSabre(locObj.getProviderData().getLatitude(),locObj.getProviderData().getLongitude(),csvfileSabre);

                boolean isSuccessfulSabre=writeSabreData(locObj.getProviderData(),sabreData,bwSabreDataContent,count);


                // Write Provider data into the Combined PL and CVL file

                if(locObj.getProviderData().getLocationCode().length()>3){

                    flag=true;
                    boolean isSuccessfulPL=writeProviderLocationDataToCSVFile(locObj.getProviderData(),proLat,proLong,diffInProDataAndCorrectedProData,bwCVLDataContentNonAirport,count,flag);

                }
                else{

                    flag=true;
                    boolean isSuccessfulPL=writeProviderLocationDataToCSVFile(locObj.getProviderData(),proLat,proLong,diffInProDataAndCorrectedProData,bwCVLDataContentAirport,count,flag);

                }
                for(CvlLocDataList cvlObj: CvlLocDataList){

                    double diffInCorrectedCVLDataAndCorrectedPLData;
                    double diffInRawCVLDataAndCorrectedProviderData;
                    double diffInRawCVLDataAndRawProviderData;
                    double cvlLat,cvlLong;
                    StringBuilder Address=new StringBuilder();
                    String locationName=cvlObj.getCarVendorLocationName();
                    String streetAddress=cvlObj.getStreetAddress();
                    String city=cvlObj.getCityName();
                    String country=cvlObj.getIsoCountryCode();
                    Address.append(locationName).append(",").append(streetAddress).append(",").append(city).append(",").append(country);
                    Cordinates CVLCordinates=getCorrectLatLong(Address.toString());
                    cvlLat=CVLCordinates.getLatitude();
                    cvlLong=CVLCordinates.getLongitude();
                    // Get Correct Latitude and Longitude from Google API
                    diffInRawCVLDataAndRawProviderData=distance(locObj.getProviderData().getLatitude(),locObj.getProviderData().getLongitude(),cvlObj.getLatitude(),cvlObj.getLongitude(),0,0);

                    if(cvlObj.getLatitude()==0 && cvlObj.getLongitude()==0){
                        cvlObj.setLatitude(Double.parseDouble("Invalid"));
                        cvlObj.setLongitude(Double.parseDouble("Invalid"));
                        diffInRawCVLDataAndCorrectedProviderData=-1;
                    }
                    else{

                        diffInRawCVLDataAndCorrectedProviderData=distance(proLat,proLong,cvlObj.getLatitude(),cvlObj.getLongitude(),0,0);
                    }
                    // Get Correct Latitude and Longitude from Google API
                    if(cvlLat==0 && cvlLong==0){
                        cvlLat=0;
                        cvlLong=0;
                        diffInCorrectedCVLDataAndCorrectedPLData=-1;


                    }
                    else{

                        diffInCorrectedCVLDataAndCorrectedPLData=distance(proLat,proLong,cvlLat,cvlLong,0,0);

                    }
                    if(locObj.getProviderData().getLocationCode().length()>3){
                        boolean isSuccessfulCVL=writeCVLDataToCSVFile(cvlObj,cvlLat,cvlLong,diffInRawCVLDataAndCorrectedProviderData,bwCVLDataContentNonAirport,diffInCorrectedCVLDataAndCorrectedPLData,diffInRawCVLDataAndRawProviderData,count,diffInProDataAndCorrectedProData);
                    }
                    else{
                        boolean isSuccessfulCVL=writeCVLDataToCSVFile(cvlObj,cvlLat,cvlLong,diffInRawCVLDataAndCorrectedProviderData,bwCVLDataContentAirport,diffInCorrectedCVLDataAndCorrectedPLData,diffInRawCVLDataAndRawProviderData,count,diffInProDataAndCorrectedProData);
                    }

                }
                count++;


            }
            bwCVLDataContentNonAirport.close();
            bwCVLDataContentAirport.close();
            bwProviderDataContent.close();
            bwSabreDataContent.close();

       } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, double el1, double el2)
    {

        final int radiusOfEarth = 6371; // Radius of the earth

        final Double latDistance = Math.toRadians(lat2 - lat1);
        final Double lonDistance = Math.toRadians(lon2 - lon1);
        final Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        final Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radiusOfEarth * c * 1000; // convert to meters

        final double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        distance = Math.sqrt(distance);

        distance = distance / 1609.344;



        return distance;
    }

    public static  Cordinates getCorrectLatLong(String Address) throws ParseException {

        BufferedReader br = null;
        BufferedWriter bw = null;
        Double google_lat = null;
        Double google_lng = null;
        Double offset_lat;
        Double offset_lng;
        String fileContent;
        Double latitudeOriginal;
        Double longitudeOriginal;
        JSONParser parser = new JSONParser();


        Address = Address.replace(" ", "%20");
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

        Object obj = parser.parse(json);
        JSONObject jb = (JSONObject) obj;
        double distanceObtained;

        //now read
        String jsonStatus = (String) jb.get("status");
        google_lat = google_lng = 0.00;
        offset_lat = offset_lng = 0.00;
        if (jsonStatus.equals("OK")) {
            JSONArray jsonObject1 = (JSONArray) jb.get("results");
            JSONObject jsonObject2 = (JSONObject) jsonObject1.get(0);
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("geometry");
            JSONObject location = (JSONObject) jsonObject3.get("location");


            System.out.println("Lat = " + location.get("lat"));
            System.out.println("Lng = " + location.get("lng"));
            google_lat = Double.parseDouble(location.get("lat").toString());
            google_lng = Double.parseDouble(location.get("lng").toString());
        }
        Cordinates cordinate=new Cordinates();
        cordinate.setLatitude(google_lat);
        cordinate.setLongitude(google_lng);
        return cordinate;

    }

    private static boolean writeProviderLocationDataToCSVFile(ProviderData providerData,double latitude,double longitude, double distance,BufferedWriter bw,int i,boolean flag){

        String fileContent;
       // String linecsv=providerData.toString();
        String linecsv= null;
        try {

            if (providerData.getStreetAddress().indexOf(',') > -1) {

                providerData.setStreetAddress( providerData.getStreetAddress().replace(",", " "));
            }
            linecsv = providerToString(providerData,i);
            linecsv = linecsv.replace("null", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(flag==true){

            fileContent =linecsv + "," + latitude + "," + longitude + "," +" ";
        }
        else{

            fileContent =linecsv + "," + latitude + "," + longitude + "," + distance;
        }

        try {
            bw.write(fileContent);
            bw.write("\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private static boolean writeCVLDataToCSVFile(CvlLocDataList CVLData,double latitude,double longitude, double distance,BufferedWriter bw,double CVLDist,double rawDist,int id,double invalidPLAddress){

        String fileContent;
        // String linecsv=providerData.toString();
        String linecsv= null;
        try {
            if (CVLData.getStreetAddress().indexOf(',') > -1) {

                CVLData.setStreetAddress( CVLData.getStreetAddress().replace(",", " "));
            }

            linecsv = CVLToString(CVLData,id);
            linecsv = linecsv.replace("null", " ");
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(invalidPLAddress==-1){

            fileContent =linecsv + "," + latitude + "," + longitude + "," + " " +","+" "+","+rawDist;

        }
        else{

            fileContent =linecsv + "," + latitude + "," + longitude + "," + distance+","+CVLDist+","+rawDist;
        }

        try {
            bw.write(fileContent);
            bw.write("\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private static boolean writeSabreData(ProviderData proData,SabreData sabreData,BufferedWriter bw,int id){

        String fileContent;
        // String linecsv=providerData.toString();
        String linecsv= null;
        try {
            linecsv = SabreFormatToString(proData,sabreData,id);
            linecsv = linecsv.replace("null", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
       // fileContent =linecsv + "," + latitude + "," + longitude + "," + distance;
        try {
            bw.write(linecsv);
            bw.write("\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    private static boolean writeHeaderForCSVFile(BufferedWriter bw,String header){

            String fileContent;
            StringBuilder linecsv=new StringBuilder();

            try {
            bw.write(header);
            bw.write("\n");
                return true;
            } catch (Exception e) {
            e.printStackTrace();
                return false;
            }

        }

    public static String providerToString(ProviderData provider,int id){

        return id+","+provider.getProviderID()+","+provider.getSupplier()+","+provider.getIataCode()+","+provider.getLocationCode()+","+provider.getLocationName()+","+provider.getStreetAddress()+","+provider.getCityName()+","+provider.getStateProvinceCode()+","+provider.getPostalCode()+","+provider.getCountryCode()+","+provider.getPhoneNumber()+","+provider.getFaxNumber()+","+provider.getLatitude()+","+provider.getLongitude()+","+provider.getLocationType()+","+provider.getShuttleCategory()+","+provider.getDeliveryBool()+","+provider.getCollectionBool()+","+provider.getOutOfOfficeHoursBool()+","+" ";
    }

    public static String CVLToString(CvlLocDataList provider,int id){

        return id+","+"8"+","+provider.getSupplierID()+","+provider.getAirportCode()+","+provider.getCarVendorLocationCode()+","+provider.getCarVendorLocationName()+","+provider.getStreetAddress()+","+provider.getCityName()+","+provider.getStateProvinceCode()+","+provider.getPostalCode()+","+provider.getIsoCountryCode()+","+provider.getPhone()+","+provider.getFaxNumber()+","+provider.getLatitude()+","+provider.getLongitude()+","+provider.getLocationTypeID()+","+provider.getCarShuttleCategoryCode()+","+provider.getDeliveryBoolean()+","+provider.getCollectionBoolean()+","+provider.getOutOfOfficeHourBoolean()+","+provider.getCarVendorLocationID();
    }

    public static String SabreFormatToString(ProviderData provider,SabreData sabreData,int id){

        return id+","+sabreData.getUID()+","+sabreData.getID()+","+provider.getLocationName()+","+provider.getStreetAddress()+","+provider.getCityName()+","+sabreData.getCountryRegion()+","+provider.getStateProvinceCode()+","+provider.getCountryCode()+","+provider.getPostalCode()+","+sabreData.getGeoLevel()+","+provider.getLongitude()+","+provider.getLatitude()+","+sabreData.getPhoneticCode()+","+provider.getSupplier()+","+provider.getIataCode()+","+provider.getLocationCode()+","+sabreData.getTimeStamp();
    }

    public static SabreData getDataFromSabre(double providerLatitude,double providerlongitude,String csvfile) {

        BufferedReader reader = null;
        SabreData sabreData=new SabreData();
        try {
            reader = new BufferedReader(new FileReader(csvfile));


            String line = "";
            int skip=0;
            while((line=reader.readLine())!=null) {

                if(skip==0){
                    skip++;
                    continue;
                }
                else{

                    String[] word = line.split(",");
                    String longEXcel=word[10];
                    String latEXcel=word[11];
                    double latitude=Double.parseDouble(latEXcel);
                    double longitude=Double.parseDouble(longEXcel);
                    if(latitude==providerLatitude && longitude==providerlongitude){
                        sabreData.setUID(word[0]);
                        sabreData.setID(word[1]);
                        sabreData.setCountryRegion(word[5]);
                        sabreData.setGeoLevel(word[9]);
                        sabreData.setPhoneticCode(word[12]);
                        sabreData.setTimeStamp(word[16]);
                        break;
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


       return sabreData;

    }



}




