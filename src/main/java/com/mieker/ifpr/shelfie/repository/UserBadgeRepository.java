package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserBadgeRepository extends JpaRepository<UserBadge, UUID> {
}
