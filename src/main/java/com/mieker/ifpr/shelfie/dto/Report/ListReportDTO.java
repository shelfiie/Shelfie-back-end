package com.mieker.ifpr.shelfie.dto.Report;

import com.mieker.ifpr.shelfie.entity.enumeration.ReportStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
public class ListReportDTO {
    private UUID reportId;
    private UUID userId;
    private UUID reviewId;
    private UUID bookId;
    private String review;
    private ReportStatus reportStatus;
    private Date createdAt;
}
