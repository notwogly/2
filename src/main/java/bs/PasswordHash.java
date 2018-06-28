package bs;

import org.springframework.lang.Nullable;
import sun.misc.BASE64Encoder;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordHash {
    public static @NotNull
    String getSalt() {
        Random rand = new SecureRandom();
        byte[] salt = new byte[16];
        rand.nextBytes(salt);
        return new BASE64Encoder().encode(salt);
    }

    public static @Nullable
    String getHashedPassword(@NotNull String password, @NotNull String salt) {
        String plain = salt + password;
        MessageDigest messageDigest;
        String cipher = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(plain.getBytes("UTF-8"));
            cipher = new BASE64Encoder().encode(messageDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cipher;
    }
}

