package net.spectrum.oauth2;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestDto {
    List<String> oids;
}