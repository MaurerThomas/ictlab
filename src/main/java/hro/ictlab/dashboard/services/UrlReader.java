package hro.ictlab.dashboard.services;

import hro.ictlab.dashboard.exception.FailToConnectException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UrlReader {
    public String readFromUrl(URL url) throws FailToConnectException {
        try {
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new FailToConnectException("Could not read from URL: " + url, e);
        }
    }
}

