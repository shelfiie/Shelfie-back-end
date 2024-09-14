package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.MyBooks.BookRelationDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.PageDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.entity.enumeration.PaginometerBadge;
import com.mieker.ifpr.shelfie.entity.enumeration.ReviewBadge;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.ReadingProgressRepository;
import com.mieker.ifpr.shelfie.repository.UserRepository;
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
    private final UserRepository userRepository;

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

        this.setPaginometerBadge(userId, totalPages);

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

        int page = rp.getPage();

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

        this.setReviewBadge(userId, review);
        this.setBookBadge(userId, lido);
        this.setPaginometerBadge(userId, paginometer);

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

    private void setPaginometerBadge(UUID userId, int badge) {
        Optional<User> user = userRepository.findById(userId);
        if (badge < 100) {
            user.get().setPaginometerBadge(PaginometerBadge.NONE);
        } else if (100 <= badge && badge < 1000) {
            user.get().setPaginometerBadge(PaginometerBadge.HUNDRED_PAGES);
        } else if (1000 <= badge && badge < 5000) {
            user.get().setPaginometerBadge(PaginometerBadge.THOUSAND_PAGES);
        } else {
            user.get().setPaginometerBadge(PaginometerBadge.LOT_OF_PAGES);
        }
        userRepository.save(user.get());
    }

    private void setBookBadge(UUID userId, int lido) { // 1 5 15 30
        Optional<User> user = userRepository.findById(userId);
        if (lido >= 1 && lido < 5) {
            user.get().setBookBadge(BookBadge.READER);
        } else if (lido >= 5 && lido < 15 ) {
            user.get().setBookBadge(BookBadge.BOOKWORM);
        } else if (lido >= 15 && lido < 30 ) {
            user.get().setBookBadge(BookBadge.BIBLIOPHILE);
        } else if (lido > 30 ) {
            user.get().setBookBadge(BookBadge.BIBLIOMANIAC);
        }
        userRepository.save(user.get());
    }

    private void setReviewBadge(UUID userId, int review) { // 1 3 10 +20
        Optional<User> user = userRepository.findById(userId);
        if (review >= 1 && review < 3) {
            user.get().setReviewBadge(ReviewBadge.NOVICE);
        } else if (review >= 3 && review < 10 ) {
            user.get().setReviewBadge(ReviewBadge.CRITIC);
        } else if (review >= 10 && review < 20 ) {
            user.get().setReviewBadge(ReviewBadge.EXPERT);
        } else if (review > 20 ) {
            user.get().setReviewBadge(ReviewBadge.CONNOISSEUR);
        }
        userRepository.save(user.get());
    }
}
