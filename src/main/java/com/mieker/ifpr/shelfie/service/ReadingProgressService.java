package com.mieker.ifpr.shelfie.service;


import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import com.mieker.ifpr.shelfie.repository.ReadingProgressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ReadingProgressService {
    private final ReadingProgressRepository rpRepository;
    private final MyBookService myBookService;

    public void create (UUID myBookId, Integer pages) {
        System.out.println(myBookId);
//        ver isso aqui
//        pegar direto da service
//        pegar do repositorio ?
        ReadingProgress rp = rpRepository.findById(myBookId).orElseThrow(() -> new RuntimeException("Not found with id: " + myBookId));
        myBookService.getMyBooksById(myBookId);
        rp.setPage(pages);
        rpRepository.save(rp);
    }
}
