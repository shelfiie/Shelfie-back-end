package com.mieker.ifpr.shelfie.bootstrap;

import com.mieker.ifpr.shelfie.entity.Badge;
import com.mieker.ifpr.shelfie.repository.BadgeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BadgeSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final BadgeRepository badgeRepository;

    public BadgeSeeder(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createBadges();
    }

    private void createBadges() {
        String none = "https://www.shutterstock.com/image-vector/prohibited-signs-isolated-on-white-260nw-1890653254.jpg";

//        book badges
        String beginner = "https://www.clipartmax.com/png/small/53-531308_keys-clip-art-download-jg-300.png";
        String intermediate = "https://www.onlygfx.com/wp-content/uploads/2021/02/6-pixel-heart-1-300x300.png";
        String advanced = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5NdYu0PxHRgx7vmWVFxI7Vhb1VR6k3JkoNA&s.png";
        String expert = "https://pngfre.com/wp-content/uploads/orange-75-300x300.png";

//        paginometer badges
        String hundredPages = "https://www.onlygfx.com/wp-content/uploads/2021/02/6-pixel-heart-1-300x300.png";
        String thousandPages = "https://www.clipartmax.com/png/small/53-531308_keys-clip-art-download-jg-300.png";
        String lotOfPages = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5NdYu0PxHRgx7vmWVFxI7Vhb1VR6k3JkoNA&s.png";

//        na verdade esses poderiam ser em relação a quantidade de livros livros
//        reading progression badge
        String reader = "https://www.clipartmax.com/png/small/53-531308_keys-clip-art-download-jg-300.png";
        String bookworm = "https://www.onlygfx.com/wp-content/uploads/2021/02/6-pixel-heart-1-300x300.png";
        String bibliophile = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5NdYu0PxHRgx7vmWVFxI7Vhb1VR6k3JkoNA&s.png";
        String bibliomaniac = "https://pngfre.com/wp-content/uploads/orange-75-300x300.png";



        String[][] badgeData = {
                {"NONE", none},
                {"BEGINNER", beginner},
                {"INTERMEDIATE", intermediate},
                {"ADVANCED", advanced},
                {"EXPERT", expert},
                {"HUNDRED_PAGES", hundredPages},
                {"THOUSAND_PAGES", thousandPages},
                {"LOT_OF_PAGES", lotOfPages},
                {"READER", reader},
                {"BOOKWORM", bookworm},
                {"BIBLIOPHILE", bibliophile},
                {"BIBLIOMANIAC", bibliomaniac}
        };

        for (String[] badgeEntry : badgeData) {
            if (badgeRepository.findByName(badgeEntry[0]) == null) {
                Badge badge = new Badge();
                badge.setName(badgeEntry[0]);
                badge.setImage(badgeEntry[1]);
                badge.setDescription("Badge " + badgeEntry[0].toLowerCase());
                badgeRepository.save(badge);
            }
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
