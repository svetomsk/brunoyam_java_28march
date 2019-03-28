package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkHelper {
    public static String fetchHTML(String url) throws IOException {
        URL remoteURL = new URL(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(remoteURL.openStream(), "UTF-8"));

        String s;

        StringBuilder builder = new StringBuilder();
        while ((s = in.readLine()) != null) {
            builder.append(s);

        }

        return builder.toString();
    }
}