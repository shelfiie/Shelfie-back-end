package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.entity.Report;
import com.mieker.ifpr.shelfie.entity.Review;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.ReportStatus;
import com.mieker.ifpr.shelfie.repository.ReportRepository;
import com.mieker.ifpr.shelfie.repository.ReviewRepository;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
