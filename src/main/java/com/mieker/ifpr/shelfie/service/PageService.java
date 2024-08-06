package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.MyBooks.BookRelationDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.PageDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReadingProgressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PageService {
    private final ReadingProgressRepository rpRepository;
    private final Validation validation;
    private final MyBooksRepository mbRepository;
    private final BookRepository bookRepository;

    public PageDTO getMyLastPage(UUID bookId) {
        UUID userId = validation.userAuthenticator();
        MyBooks myBooks = mbRepository.findMyBooksByBookIdAndUserId(bookId, userId);
        if (myBooks == null) {
            throw new NotFoundException("Esse livro não está na biblioteca do usuário.");
        }
        int page = rpRepository.findMaxProgressByMyBooksId(myBooks.getId());
        System.out.println(page);

        Optional<Book> book = bookRepository.findById(bookId);
        int totalPages = book.get().getPages();

//        quantidade de páginas lidas x 100 / total de páginas

        int porcentage = (page * 100) / totalPages;

        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setPorcentage(porcentage);

        return pageDTO;
    }

    public PageDTO getMyProgressionPage(UUID rpId) {
        ReadingProgress rp = rpRepository.findById(rpId).orElseThrow(() -> new NotFoundException("Progresso de leitura não encontrado."));
        MyBooks myBooks = mbRepository.findMyBooksById(rp.getMyBooks().getId());

        int page = rpRepository.findMaxProgressByMyBooksId(myBooks.getId());

        Optional<Book> book = bookRepository.findById(myBooks.getBook().getId());
        int totalPages = book.get().getPages();

//        quantidade de páginas lidas x 100 / total de páginas

        int porcentage = (page * 100) / totalPages;

        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setPorcentage(porcentage);

        return pageDTO;
    }

    public BookRelationDTO getBookStatus() {
        UUID userId = validation.userAuthenticator();
        int lido = mbRepository.countMyBooksByUserIdAndBookStatusAndEnabled(userId, BookStatus.LIDO, true);
        int lendo = mbRepository.countMyBooksByUserIdAndBookStatusAndEnabled(userId, BookStatus.LENDO, true);
        int queroLer = mbRepository.countMyBooksByUserIdAndBookStatusAndEnabled(userId, BookStatus.QUERO_LER, true);
        int abandonado = mbRepository.countMyBooksByUserIdAndBookStatusAndEnabled(userId, BookStatus.ABANDONADO, true);
        int favorite = mbRepository.countMyBooksByUserIdAndFavoriteAndEnabled(userId, true, true);
        int review = mbRepository.countReviewByUserId(userId);
        Integer paginometer = this.paginometerCounter(userId);
        BookRelationDTO bookStatusDTO = new BookRelationDTO();
        bookStatusDTO.setLIDO(lido);
        bookStatusDTO.setLENDO(lendo);
        bookStatusDTO.setQUERO_LER(queroLer);
        bookStatusDTO.setABANDONADO(abandonado);
        bookStatusDTO.setReview(review);
        bookStatusDTO.setFavorite(favorite);
        bookStatusDTO.setPaginometer(paginometer);
        return bookStatusDTO;
    }

    public PageDTO getPaginometer() {
        UUID userId = validation.userAuthenticator();
        int paginometer = this.paginometerCounter(userId);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(paginometer);
        return pageDTO;
    }

    private int paginometerCounter (UUID userId) {
        List<MyBooks> myBooksList = mbRepository.findAllByUserIdAndEnabled(userId, true);
        Integer paginometer = 0;
        for (MyBooks myBooks : myBooksList) {
            System.out.println(rpRepository.findMaxProgressByMyBooksId(myBooks.getId()));
            Integer page = rpRepository.findMaxProgressByMyBooksId(myBooks.getId());
            if (page == null) {
                page = 0;
            }
            paginometer += page;
        }
        return paginometer;
    }
}
