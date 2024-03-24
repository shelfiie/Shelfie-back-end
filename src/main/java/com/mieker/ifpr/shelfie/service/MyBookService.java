package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.dto.MyBookDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.mapper.MyBookMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
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

    private final MyBooksRepository myBooksRepository;
    private final BookRepository bookRepository;
    private final BookApiService bookApiService;
    private final BookService bookService;
    private final UserService userService;
    private final MyBookMapper myBookMapper;

    public MyBookDTO create(UUID userId, String googleId, BookStatus bookStatus) throws ParseException {
        Optional<Book> optionalBook = bookRepository.findByGoogleId(googleId);
        MyBooks myBook = optionalBook.isPresent() ? addBookToUser(optionalBook.get().getId(), userId, bookStatus) : createBookAndAddToUser(googleId, userId, bookStatus);
        return myBookMapper.myBookToMyBookDTO(myBook);
    }

    public MyBooks addBookToUser(UUID bookId, UUID userId, BookStatus bookStatus) {
        MyBooks myBooks = new MyBooks();
        User user =  userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);
        myBooks.setBook(book);
        myBooks.setUser(user);
        myBooks.setBookStatus(bookStatus);
        return myBooksRepository.save(myBooks);
    }

    public MyBooks createBookAndAddToUser(String googleId,UUID userId,BookStatus bookStatus) throws ParseException {
        BookApiDTO bookDTO = bookApiService.getBookByGoogleId(googleId);
        Book book = bookService.createBook(bookDTO);
        return addBookToUser(book.getId(), userId, bookStatus);
    }
}

// TODO:
// implementar o resto do crud
//      só falta o diable e o get

// TODO
// criar um método? que vai comparar se já tem o usuário e o livro associado com as IDS
// criar um método que vai pegar o id do tb_book com o googleId
// ver o negócio do enum como vou fazer para comparar
