package hro.ictlab.dashboard.services;

import hro.ictlab.dashboard.exception.FailToConnectException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class is responsible for reading a URL and then send it back.
 * If a class is declared final then its methods are automatically (effectively) final.
 * This is because a final class cannot be subclassed, and thus its methods cannot be overridden (i.e., are effectively final).
 */
public final class UrlReader {

    private UrlReader(){}

    /**
     * Reads the URL.
     * @param url The URL to read.
     * @return A string containing the body of the response.
     * @throws FailToConnectException
     */
    public static String readFromUrl(URL url) throws FailToConnectException {
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

