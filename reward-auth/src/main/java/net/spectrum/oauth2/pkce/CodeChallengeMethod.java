package net.spectrum.oauth2.pkce;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public enum CodeChallengeMethod {
    S256 {
        @Override
        public String transform(String codeVerifier) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
                //return Base64.getUrlEncoder().withoutPadding().encodeToString(Hex.encode(hash));
                /*MessageDigest digestInstance = MessageDigest.getInstance("SHA-256");
                final byte[] rawBytes = digestInstance.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
               return Base64.getUrlEncoder().withoutPadding().encodeToString(Hex.encode(hash));*/
                String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
                log.info("code challenge value {}: ", codeChallenge);
                return codeChallenge;
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e);
            }
        }
    },
    PLAIN {
        @Override
        public String transform(String codeVerifier) {
            return codeVerifier;
        }
    },
    NONE {
        @Override
        public String transform(String codeVerifier) {
            throw new UnsupportedOperationException();
        }
    };

    public abstract String transform(String codeVerifier);
}
