package main;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Crypto {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final BASE64Encoder encoder = new BASE64Encoder();
    private static final BASE64Decoder decoder = new BASE64Decoder();

    private static Key getKey() {
        String key = "@V<N)Vmdh7l6AH;N";
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    }

    public static String cryptText(String text) {
        Cipher cipher = getCipher(getKey(), Cipher.ENCRYPT_MODE);

        byte[] cryptText;
        try {
            cryptText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return encoder.encode(cryptText);
    }

    public static String decryptText(String text) {
        Cipher cipher = getCipher(getKey(), Cipher.DECRYPT_MODE);

        byte[] cryptText;
        try {
            cryptText = cipher.doFinal(decoder.decodeBuffer(text));
        } catch (IllegalBlockSizeException | IOException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return new String(cryptText, StandardCharsets.UTF_8);
    }

    private static Cipher getCipher(Key key, int mode) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        try {
            cipher.init(mode, key, new IvParameterSpec(new byte[16]));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        return cipher;
    }
}
