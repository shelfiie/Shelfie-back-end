package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.Report.ListReportDTO;
import com.mieker.ifpr.shelfie.dto.Review.ReviewDTO;
import com.mieker.ifpr.shelfie.entity.enumeration.ReportStatus;
import com.mieker.ifpr.shelfie.service.ReadingProgressService;
import com.mieker.ifpr.shelfie.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private final ReportService reportService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("{reviewId}")
    public ResponseEntity<String> createReport(@PathVariable UUID reviewId) {
        String message = reportService.createReport(reviewId);
        return ResponseEntity.status(201).body(message);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/review/{reviewId}")
    public ResponseEntity<List<ListReportDTO>> getReportListByReviewId(@PathVariable UUID reviewId) {
        List<ListReportDTO> listReportDTO = reportService.getReportListByReviewId(reviewId);
        return ResponseEntity.status(200).body(listReportDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<List<ListReportDTO>> getAllReports() {
        List<ListReportDTO> listReportDTO = reportService.getAllReports();
        return ResponseEntity.status(200).body(listReportDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/{reportId}/{reportStatus}")
    public ResponseEntity<ListReportDTO> changeStatusReport(@PathVariable UUID reportId, @PathVariable ReportStatus reportStatus) {
        ListReportDTO listReportDTO = reportService.changeReportStatus(reportId, reportStatus);
        return ResponseEntity.status(200).body(listReportDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<List<ListReportDTO>> getReportListByUserId(@PathVariable UUID userId) {
        List<ListReportDTO> listReportDTO = reportService.getReportListByUserId(userId);
        return ResponseEntity.status(200).body(listReportDTO);
    }
}

// rotas necessárias:
// criar report de review xxx
// pegar todos os reports xxx
// pegar report por review
// pegar report por usuario
// pegar report por livro
// desabilitar report - mudar status para recusadp xxx
// desabilitar review - mudar status para resolvido xxx

