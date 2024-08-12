package com.mieker.ifpr.shelfie.entity;

import com.mieker.ifpr.shelfie.entity.enumeration.ReportStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private Review review;
    @Enumerated(EnumType.STRING)
    @Column(name = "report_status", nullable = false)
    private ReportStatus reportStatus;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "report_created_at", nullable = false, updatable = false)
    private Date createdAt;
}
