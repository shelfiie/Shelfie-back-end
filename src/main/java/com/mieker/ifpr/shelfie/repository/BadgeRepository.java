package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BadgeRepository extends JpaRepository<Badge, UUID> {
    Badge findByName(String name);
}
