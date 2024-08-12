package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository  extends JpaRepository<Report, UUID> {

}
