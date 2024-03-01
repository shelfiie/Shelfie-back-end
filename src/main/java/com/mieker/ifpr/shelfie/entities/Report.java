package com.mieker.ifpr.shelfie.entities;

import com.mieker.ifpr.shelfie.entities.enumeration.ReportStatus;

import java.util.Date;

public class Report {
    private Long id;
    private User user;
    private Review review;
    private ReportStatus reportStatus;
    private Date createdAt;
}
