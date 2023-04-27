package numble.shorturl.application;

import org.springframework.stereotype.Service;

@Service
public class UrlEncodingService {
    static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String encoding(Long value) {
        final StringBuilder sb = new StringBuilder();
        do {
            int i = Math.toIntExact(value % 62);
            sb.append(BASE62[i]);
            value /= 62;
        } while (value > 0);
        return sb.toString();
    }
}
