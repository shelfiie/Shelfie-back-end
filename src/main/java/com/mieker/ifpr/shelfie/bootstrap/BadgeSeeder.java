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
        String none = "";

//        review badges
        String novice = "https://i.imgur.com/ASDWRTR.png";
        String critic = "https://i.imgur.com/4SS7r6J.png";
        String expert = "https://i.imgur.com/URZ2ZWE.png";
        String connoisseur = "https://i.imgur.com/BbSCVwU.png";

//        paginometer badges
        String hundredPages = "https://i.imgur.com/WZkFQjW.png";
        String thousandPages = "https://i.imgur.com/RMABMHU.png";
        String lotOfPages = "https://i.imgur.com/6Q2MuSH.png";

//        na verdade esses poderiam ser em relação a quantidade de livros livros
//        book badges
        String reader = "https://i.imgur.com/HHomZVo.png";
        String bookworm = "https://i.imgur.com/O0Q15dz.png";
        String bibliophile = "https://i.imgur.com/XCybLSc.png";
        String bibliomaniac = "https://i.imgur.com/qTZOmbr.png";


//        'NOVICE','CRITIC','EXPERT','CONNOISSEUR'
        String[][] badgeData = {
                {"NONE", none, "Não conseguiu essa badge ainda? :("},
                {"NOVICE", novice, "Crítica construtiva (ou nem tanto)"},
                {"CRITIC", critic, "O crítico nasceu"},
                {"CONNOISSEUR", connoisseur, "Connoisseur de livros"},
                {"EXPERT", expert, "Mestre das análises"},
                {"HUNDRED_PAGES", hundredPages, "Primeiras páginas"},
                {"THOUSAND_PAGES", thousandPages, "Mil e uma noites"},
                {"LOT_OF_PAGES", lotOfPages, "Morando na biblioteca"},
                {"READER", reader, "Batizado nas letras"},
                {"BOOKWORM", bookworm, "Leitor Júnior"},
                {"BIBLIOPHILE", bibliophile, "Livros são a minha vibe"},
                {"BIBLIOMANIAC", bibliomaniac, "Livro? Sim! Sono? Talvez... depois!"}
        };

        for (String[] badgeEntry : badgeData) {
            if (badgeRepository.findByName(badgeEntry[0]) == null) {
                Badge badge = new Badge();
                badge.setName(badgeEntry[0]);
                badge.setImage(badgeEntry[1]);
                badge.setDescription(badgeEntry[2]);
                badgeRepository.save(badge);
            }
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
