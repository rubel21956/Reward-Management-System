package net.spectrum.api.users.operator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperatorResponseDto {
    private String oid;
    private String message;
}
