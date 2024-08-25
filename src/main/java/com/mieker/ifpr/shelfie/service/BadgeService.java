package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.entity.Badge;
import com.mieker.ifpr.shelfie.repository.BadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BadgeService {
    private BadgeRepository badgeRepository;

    public List<Badge> getAllBadges() {
        List<Badge> badges = badgeRepository.findAll();
        return badges;
    }
}
