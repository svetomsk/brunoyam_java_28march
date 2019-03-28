package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkHelper {
    public static String fetchHTML(String url) throws IOException {
        URL remoteURL = new URL(url);
        InputStream input = remoteURL.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        String s;
        StringBuilder builder = new StringBuilder();
        while ((s = br.readLine()) != null) {
            builder.append(s);
        }
        return builder.toString();
    }
}
