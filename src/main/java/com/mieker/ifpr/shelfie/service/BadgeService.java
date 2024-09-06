package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Badge.BadgeDTO;
import com.mieker.ifpr.shelfie.dto.Badge.UserBadgeDTO;
import com.mieker.ifpr.shelfie.entity.Badge;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.exception.IdNotFoundException;
import com.mieker.ifpr.shelfie.repository.BadgeRepository;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BadgeService {
    private BadgeRepository badgeRepository;
    private Validation userValidation;
    private UserRepository userRepository;

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public Badge updateBadge(UUID badgeId, BadgeDTO badgeDTO) {
        Badge badge = badgeRepository.findById(badgeId).orElseThrow(() -> new IdNotFoundException("Badge com esse id não encontrado: " + badgeId));
        if (!badgeDTO.getImage().isEmpty()) {
            badge.setImage(badgeDTO.getImage());
        }
        if (!badgeDTO.getDescription().isEmpty()) {
            badge.setDescription(badgeDTO.getDescription());
        }
        return badgeRepository.save(badge);
    }

    public UserBadgeDTO getUserBadges() {
        UUID userId = userValidation.userAuthenticator();
        User user = userRepository.findById(userId).orElseThrow(() -> new IdNotFoundException("Usuário não encontrado com esse id: " + userId));
        String book = String.valueOf(user.getBookBadge());
        String review = String.valueOf(user.getReviewBadge());
        String paginometer = String.valueOf(user.getPaginometerBadge());

        Badge bookBadge = badgeRepository.findByName(book);
        Badge reviewBadge = badgeRepository.findByName(review);
        Badge paginometerBadge = badgeRepository.findByName(paginometer);

        UserBadgeDTO userBadgeDTO = new UserBadgeDTO();
        userBadgeDTO.setNameBookBadge(bookBadge.getName());
        userBadgeDTO.setDescriptionBookBadge(bookBadge.getDescription());
        userBadgeDTO.setImageBookBadge(bookBadge.getImage());
        userBadgeDTO.setNameReviewBadge(reviewBadge.getName());
        userBadgeDTO.setDescriptionReviewBadge(reviewBadge.getDescription());
        userBadgeDTO.setImageReviewBadge(reviewBadge.getImage());
        userBadgeDTO.setNamePaginometerBadge(paginometerBadge.getName());
        userBadgeDTO.setDescriptionPaginometerBadge(paginometerBadge.getDescription());
        userBadgeDTO.setImagePaginometerBadge(paginometerBadge.getImage());
        return userBadgeDTO;
    }
}
