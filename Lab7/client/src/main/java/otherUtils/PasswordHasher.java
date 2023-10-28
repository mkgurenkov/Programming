package otherUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordHasher {
    public String digest(byte[] password) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            return Arrays.toString(sha1.digest(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
