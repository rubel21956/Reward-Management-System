package net.spectrum.api.util;


import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Random;


@Component
@Slf4j
public class Utility {

    public static String generateHash(String data, String salt) {
        StringBuilder transKeys = new StringBuilder()
                .append(data)
                .append(salt);

        String hashed = Hashing.sha256()
                .hashString(transKeys, StandardCharsets.UTF_8)
                .toString();

        return hashed;
    }

    public String getRandomAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijkmnopqrstuvwxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public String getRandomTrackingNumberString(int n)
    {
        String AlphaNumericString = "ABCDEFGHJKLMNPQRSTUVWXYZ" + "123456789";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public void saveImage(String path, String fileName, String encodedBase64Image) throws ExceptionHandlerUtil {
        try {
            //byte[] decodedImg = Base64.getDecoder().decode(encodedImage.getBytes(StandardCharsets.UTF_8));
            byte[] decodedImg = Base64.decodeBase64(encodedBase64Image);
            Path destinationFile = Paths.get(path, fileName);
            Files.write(destinationFile, decodedImg);
        } catch (IOException e) {
            log.error("Exception occurred during writing image from base64 string", e);
            throw new ExceptionHandlerUtil (HttpStatus.INTERNAL_SERVER_ERROR, "Exception occurred during writing image from base64 string" );

        }
    }

    public String convertHexToBase64(String hexString) throws ExceptionHandlerUtil{
        byte[] decodedHex = new byte[0];
        try {
            decodedHex = Hex.decodeHex(hexString.toCharArray());
        } catch (DecoderException e) {
            log.error("Exception occurred during converting hex string to base64 string", e);
            throw new ExceptionHandlerUtil (HttpStatus.INTERNAL_SERVER_ERROR, "Exception occurred during converting hex string to base64 string" );
        }
        String result = Base64.encodeBase64String(decodedHex);
        return result;
    }


    public String convertByteArrayToBase64(byte[] fileContent){
        String result = Base64.encodeBase64String(fileContent);
        return result;
    }

    public String convertByteArrayToHex(byte[] fileContent){
        String result = Hex.encodeHexString(fileContent) ;
        return result;
    }
    public String encodeFileToBase64(File file) throws Exception{
        try (FileInputStream fileInputStreamReader = new FileInputStream(file)) {
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            return Base64.encodeBase64String(bytes);
        }
    }

    public static String getTrackingNumber () {
        String randomNum = null;
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            //generate a random number
            randomNum = Integer.valueOf(prng.nextInt()).toString();
        }
        catch (Exception ex) {
            log.error("An exception occurred while generating random tracking number", ex);
        }
        return randomNum;
    }
    public static String getOtp(){
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        return  otp;
    }
}
