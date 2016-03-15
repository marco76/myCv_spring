package ch.javaee.mycv.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by marco on 15/03/16.
 */
public class IpAddressInfoHelper {

    private final static String FREE_GEO_IP_JSON = "http://freegeoip.net/json/";


    /**
     * From an ip address call the service of FreeGeoIp and return the info in JSON format
     * @param address
     * @return
     * @throws Exception
     */

    public static String readJsonFromFreeGeoIp(String address) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(FREE_GEO_IP_JSON + address);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
