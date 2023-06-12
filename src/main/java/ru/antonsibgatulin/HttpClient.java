package ru.antonsibgatulin;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

public class HttpClient {

    public String GetRequest(String url){
        try {
            return httpRequest(url);
        } catch (Exception e) {

            return null;
        }
    }

    private String httpRequest(String urlString) throws Exception{

        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }

        } };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());


        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) { return true; }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
       // con.get
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String all = "";
        String pie = null;
        while((pie = bufferedReader.readLine())!=null){
            all+=pie;
        }

        return all;
    }

    public Integer getResponseCode(String url_string){
        String url = url_string;//"https://www.google.com";
        try {
            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            System.out.println("HTTP Response Code: " + responseCode);
            return responseCode;
        }catch (Exception e){
            return -1;
        }
    }
}
