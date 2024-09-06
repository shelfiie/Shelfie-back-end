package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.Badge.BadgeDTO;
import com.mieker.ifpr.shelfie.dto.Badge.UserBadgeDTO;
import com.mieker.ifpr.shelfie.entity.Badge;
import com.mieker.ifpr.shelfie.service.BadgeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{badgeId}")
    public ResponseEntity<Badge> updateBadge(@PathVariable UUID badgeId, @RequestBody BadgeDTO badgeDTO) {
        Badge badge = badgeService.updateBadge(badgeId, badgeDTO);
        return ResponseEntity.ok(badge);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ResponseEntity<UserBadgeDTO> getUserBadge() {
        UserBadgeDTO userBadge = badgeService.getUserBadges();
        return ResponseEntity.ok(userBadge);
    }
}
