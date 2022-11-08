package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void makeGetRequest(){
        URL myURL = null;
        try {
            myURL = new URL("https://yaksilapeer.herokuapp.com/patients");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) myURL.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        conn.setRequestProperty("Accept", "text/html");
        conn.setRequestProperty("charset", "utf-8");

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(myURL.openStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String inputLine;
// Read the body of the response
        while (true) {
            try {
                if (!((inputLine = in.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(inputLine);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void makePostRequest() throws IOException {
        String message = "Hello servlet";
        byte[] body = message.getBytes(StandardCharsets.UTF_8);

        URL myURL = new URL("http://localhost:8080/yaksilapeer/patients");
        HttpURLConnection conn = null;

        conn = (HttpURLConnection) myURL.openConnection();
// Set up the header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "text/html");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(body.length));
        conn.setDoOutput(true);

// Write the body of the request
        try (OutputStream outputStream = conn.getOutputStream()) {
            try {
                outputStream.write(body, 0, body.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        String inputLine;
// Read the body of the response
        while (true) {
            try {
                if (!((inputLine = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(inputLine);
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) {
        makeGetRequest();
        try {
            makePostRequest();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}