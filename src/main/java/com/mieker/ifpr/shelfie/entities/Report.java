package com.mieker.ifpr.shelfie.entities;

import com.mieker.ifpr.shelfie.entities.enumeration.ReportStatus;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
    @Enumerated(EnumType.STRING)
    @Column(name = "report_status", nullable = false)
    private ReportStatus reportStatus;
    @Column(name = "report_created_at", nullable = false)
    private Date createdAt;
}
