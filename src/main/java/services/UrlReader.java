package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

class UrlReader {
    public String readFromUrl(String url) throws IOException {
        URL theUrl = new URL(url);
        URLConnection urlConnection = theUrl.openConnection();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            return stringBuilder.toString();
        }
    }
}
