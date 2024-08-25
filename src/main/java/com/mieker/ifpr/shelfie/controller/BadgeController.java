package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.entity.Badge;
import com.mieker.ifpr.shelfie.service.BadgeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/badges")
@AllArgsConstructor
@RestController
public class BadgeController {

    private final BadgeService badgeService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = badgeService.getAllBadges();
        return ResponseEntity.ok(badges);
    }
}
