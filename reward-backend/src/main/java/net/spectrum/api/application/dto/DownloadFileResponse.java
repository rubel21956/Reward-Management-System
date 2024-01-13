package net.spectrum.api.application.dto;


import lombok.*;

import java.io.File;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DownloadFileResponse {
    private File file;

}
