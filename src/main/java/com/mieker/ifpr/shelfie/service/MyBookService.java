package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.dto.MyBookDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.mapper.MyBookMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MyBookService {
//    todo
//    create
//    implementar o dto
//    mapper

    private final MyBookRepository myBookRepository;
    private final BookRepository bookRepository;
    private final BookApiService bookApiService;
    private final BookService bookService;
    private final UserService userService;
    private final MyBookMapper myBookMapper;

    public MyBookDTO create(UUID userId, String googleId, BookStatus bookStatus) {
//        MyBookDTO myBookDTO = new MyBookDTO();
        Optional<Book> book = bookRepository.findByGoogleId(googleId);
        System.out.println(googleId);
        if (book.isPresent()) {

        }

        MyBooks myBook = book.isPresent() ? addBookToUser(book.get(), userId, bookStatus) : createBookAndAddToUser(googleId, userId, bookStatus);
        return myBookMapper.myBookToMyBookDTO(myBook);

//
//        if (myBooks.isPresent()) {
//            System.out.println("Book already exists");
//        } else {
//            BookApiDTO bookDTO = bookApiService.getBookByGoogleId(googleId);
//            try {
//                Book book = bookService.createBook(bookDTO);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Book does not exist. Create a new book");
////            BookApiDTO book = bookApiService.getBookByGoogleId(googleId);
//        return myBooks.isPresent() ? addBookToUser(myBooks.get(), userId, bookStatus) : createBookAndAddToUser(googleId, userId, bookStatus);
//
//        }
//        return null;
    }

    public MyBooks addBookToUser(Book book, UUID userId, BookStatus bookStatus) {
        MyBooks myBooks = new MyBooks();
        User user =  userService.getUserById(userId);
        myBooks.setBook(book);
        myBooks.setUser(user);
        myBooks.setBookStatus(bookStatus);
        return myBookRepository.save(myBooks);
    }

    public MyBooks createBookAndAddToUser(String googleId, UUID userId, BookStatus bookStatus) {
        BookApiDTO bookDTO = bookApiService.getBookByGoogleId(googleId);
        try {
            Book book = bookService.createBook(bookDTO);
            return addBookToUser(book, userId, bookStatus);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

// TODO
// criar um método? que vai comparar se já tem o usuário e o livro associado com as IDS
// criar um método que vai pegar o id do tb_book com o googleId
// ver o negócio do enum como vou fazer para comparar
