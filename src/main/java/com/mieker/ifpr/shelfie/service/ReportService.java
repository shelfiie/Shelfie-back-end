package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Report.ListReportDTO;
import com.mieker.ifpr.shelfie.entity.Report;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.ReportStatus;
import com.mieker.ifpr.shelfie.repository.ReportRepository;
import com.mieker.ifpr.shelfie.repository.ReviewRepository;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final Validation userValidation;

    public String createReport(UUID reviewId) {
        UUID userId = userValidation.userAuthenticator();
        User user = userRepository.findById(userId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        Report report = new Report();
        report.setUser(user);
        report.setReview(review);
        report.setReportStatus(ReportStatus.PENDENTE);
        reportRepository.save(report);
        return "Report criado com sucesso!";
    }

    public List<ListReportDTO> getReportListByBookId(UUID bookId) {
        return null;
    }

    public List<ListReportDTO> getAllReports() {
        List<Report> reportList = reportRepository.findAll();
        ListReportDTO listReportDTO = new ListReportDTO();

        return reportList.stream().map(report -> getListReportDTO(report, listReportDTO)).toList();
    }

    public ListReportDTO changeReportStatus(UUID reportId, ReportStatus reportStatus) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        report.setReportStatus(reportStatus);
        reportRepository.save(report);

        if (reportStatus.equals(ReportStatus.RESOLVIDO)) {
            Review review = reviewRepository.findById(report.getReview().getId()).orElseThrow();
            review.setEnabled(false);
            reviewRepository.save(review);
        }
        ListReportDTO listReportDTO = new ListReportDTO();
        return getListReportDTO(report, listReportDTO);
    }

    @NotNull
    private ListReportDTO getListReportDTO(Report report, ListReportDTO listReportDTO) {
        listReportDTO.setReportId(report.getId());
        listReportDTO.setUserId(report.getUser().getId());
        listReportDTO.setReviewId(report.getReview().getId());
        listReportDTO.setBookId(report.getReview().getMyBooks().getId());
        listReportDTO.setReview(report.getReview().getReview());
        listReportDTO.setReportStatus(report.getReportStatus());
        return listReportDTO;
    }
}
