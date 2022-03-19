/**
 *
 *  @author Jasińska Paulina S20442
 *
 */

package zad2;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {
    private String country;
    static private final String OPENWEATHER_API_KEY = "e41298aa5f3ebae9d1bb9b0477608e6f";
    static private final String OPENWEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s,%s&appid=%s";
    static private final String EXCHANGERATE_API_URL = "https://api.exchangerate.host/convert?from=%s&to=%s";
    static private final String NBPA_API_URL = "http://api.nbp.pl/api/exchangerates/tables/A/";
    static private final String NBPB_API_URL = "http://api.nbp.pl/api/exchangerates/tables/B/";

    public Service(String kraj) {
        this.country = kraj;
    }

    public String getWeather(String city) {

        try {
            Socket socket = new Socket("api.openweathermap.org", 80);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(String.format(OPENWEATHER_API_URL, city, getCountryCode(), OPENWEATHER_API_KEY));
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line ;
            StringBuilder out = new StringBuilder();
            while((line = buf.readLine())!= null){
                out.append(line);
            }
            buf.close();
            return out.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCountryCode() {

        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(new Locale("en", iso).getDisplayCountry(new Locale("en", iso)), iso);
        }
        return countries.get(country);
    }
    public Double getRateFor(String currency_code){
        Currency curr = Currency.getInstance(new Locale("en", getCountryCode()));
        String url_str = String.format(EXCHANGERATE_API_URL, curr, currency_code);
        URL url = null;
        try {
            url = new URL(url_str);

            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();
            double req_result = jsonobj.get("result").getAsDouble();
            return req_result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getNBPRate(){
        Currency curr = Currency.getInstance(new Locale("en", getCountryCode()));
        String url_str = String.format(NBPA_API_URL, curr);
        URL url = null;
        try {
            url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            BufferedReader buf = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line ;
            StringBuilder out = new StringBuilder();
            while((line = buf.readLine())!= null){
                out.append(line);
            }
            buf.close();
            Pattern p_1 = Pattern.compile(curr + "\",\"mid\":[0-9]+\\.[0-9]+");
            Matcher m = p_1.matcher(out);
            m.find();
            String str  = m.group();
            Pattern p_2 = Pattern.compile("[0-9]+\\.[0-9]+");
            Matcher m_2 = p_2.matcher(str);
            m_2.find();
            String str_2  = m_2.group();
            return new Double(str_2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
