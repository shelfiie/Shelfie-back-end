package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.Badge.BadgeDTO;
import com.mieker.ifpr.shelfie.entity.Badge;
import com.mieker.ifpr.shelfie.repository.BadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BadgeService {
    private BadgeRepository badgeRepository;

    public List<Badge> getAllBadges() {
        List<Badge> badges = badgeRepository.findAll();
        return badges;
    }

    public Badge updateBadge(UUID badgeId, BadgeDTO badgeDTO) {
        Badge badge = badgeRepository.findById(badgeId).orElseThrow(() -> new RuntimeException("Badge not found"));
        if (!badgeDTO.getImage().isEmpty()) {
            badge.setImage(badgeDTO.getImage());
        }
        if (!badgeDTO.getDescription().isEmpty()) {
            badge.setDescription(badgeDTO.getDescription());
        }
        return badgeRepository.save(badge);
    }
}
