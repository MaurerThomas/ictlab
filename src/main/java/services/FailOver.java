package services;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class FailOver {
    private UrlReader urlReader = new UrlReader();

    public String handleUrl(URL url) throws IOException {
        //All hosts
        String[] allHosts = new String[]{System.getenv("NODEMANAGER_START")};

        if (pingHost(url.toString(), 54623, 5000)) {
            return urlReader.readFromUrl(url.toString());
        } else {
            for (String newWorkingHost : allHosts) {
                if (pingHost(newWorkingHost, 80, 50)) {
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
