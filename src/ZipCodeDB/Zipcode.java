package ZipCodeDB;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Zipcode implements Comparable<Zipcode>, Distance, WeatherInfo {

    private String code;
    private String state; // two character code abbreviation
    private String city;
    private double lng;
    private double lat;
    private int pop;

    public Zipcode(String code, String state, String city, double lng, double lat, int pop) {
        this.code = code;
        this.state = state;
        this.city = city;
        this.lng = lng;
        this.lat = lat;
        this.pop = pop;
    }

    public String toString() {
        return "Zipcode{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", pop=" + pop +
                '}';
    }


    public double getLat() {
        return lat;
    }

    public String getCode() {
        return code;
    }

    // The natural ordering for zipcode
    @Override
    public int compareTo(Zipcode zc) {
        return this.code.compareTo(zc.code);
    }

    @Override
    public double distance(Zipcode zip) {
        return 0;  // fancy great circle distance
    }

    public WeatherObservation getWeatherData() {

        // http://api.geonames.org/findNearByWeatherJSON?formatted=true&lat=44&lng=-74&username=edharcourt
        URL url = null;    // null is the "nothing value"
        Scanner s = null;

        String path = "http://api.geonames.org/findNearByWeatherJSON?formatted=true&lat=" +
                this.lat + "&lng=-" + this.lng + "&username=edharcourt\n";

        try {
            url = new URL(path); // create a URL object for the path
            s = new Scanner(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double humidity = 0, temperature = 0, windspeed = 0;
        String clouds = "";


       while (s.hasNextLine()) {
           String line = s.nextLine();
           //if (line.contains("humidity")){
           if (line.indexOf("humidity") > 0) {
               humidity = Double.parseDouble(line.substring(line.indexOf(':') + 1, line.indexOf(',')));
           }
           if (line.indexOf("temperature") > 0){
                   temperature = Double.parseDouble(line.substring(line.indexOf(':') + 3, line.indexOf(',')-1));
               }
           if  (line.indexOf("windSpeed") > 0) {
               windspeed = Double.parseDouble(line.substring(line.indexOf(':') + 3, line.indexOf(',') - 1));

           }
           if (line.indexOf("clouds") > 0){
               clouds = (line.substring(line.indexOf(':') + 3, line.indexOf(',') - 1));

           }
               //System.out.println(humidity);
           }

               //System.out.println(line);
// finish this for studying

               //}
               WeatherObservation ob = new WeatherObservation(humidity, temperature, windspeed, clouds);

               return ob;
           }
       }

