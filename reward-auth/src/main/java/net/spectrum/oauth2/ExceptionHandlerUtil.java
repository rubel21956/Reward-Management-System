package net.spectrum.oauth2;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExceptionHandlerUtil extends Exception{
    HttpStatus code;
    String message;
}
