package net.spectrum.api.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.spectrum.api.applicationattachment.entity.ApplicationAttachmentEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCustomsStepFiveDto {
    private String oid;
    private ApplicationAttachmentEntity applicationAttachmentEntity;
}
