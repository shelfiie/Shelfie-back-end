package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.PageDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReadingProgressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PageService {
    private final ReadingProgressRepository rpRepository;
    private final Validation validation;
    private final MyBooksRepository mbRepository;


//    todo
//    I need to get the last page of the book passed as a parameter
//    get the greatest number page

    public PageDTO getMyLastPage(UUID bookId) {
        UUID userId = validation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(bookId, userId);
        if (myBooks == null) {
            throw new NotFoundException("Esse livro não está na biblioteca do usuário.");
        }
        int page = rpRepository.findMaxProgressByMyBooksId(myBooks.getId());
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        return pageDTO.setPage(page);
//        return null;
    }
}
