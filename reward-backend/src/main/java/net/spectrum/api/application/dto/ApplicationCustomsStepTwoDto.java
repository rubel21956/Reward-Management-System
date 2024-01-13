package net.spectrum.api.application.dto;

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.spectrum.api.applicationattachment.entity.ApplicationAttachmentEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCustomsStepTwoDto {
    @NonNull
    private String oid;
    private String applicationNarration;
    private Timestamp applicationDate;
    private String applicationStatus;
    private String applicationStatusBn;
    private List<ApplicationAttachmentEntity> applicationAttachments;
}
