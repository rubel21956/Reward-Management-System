package net.spectrum.api.auth.session.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestDto {
    @NotNull
    List<String> oids;
}