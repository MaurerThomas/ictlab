package services;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class FailOver {
    private UrlReader urlReader = new UrlReader();

    public String handleUrl(URL url) throws IOException {
        //Alle hosts
        URL[] allHosts = new URL[0];
        //Controleer of de werkende host nog werkt
        if (pingHost(url.toString(),80,50)) {
            return urlReader.readFromUrl(url);
        } else {
        //Zo niet kies een nieuwe host uit de werkende hosts
        //Reroute al het verkeer naar de nieuwe host
            for (URL newWorkingHost : allHosts) {
                if (pingHost(newWorkingHost.toString(), 80, 50)) {
                    return urlReader.readFromUrl(newWorkingHost);
                }
            }
        }
        return null;
    }

    private static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Failed to reach host.
        }
    }
}
