package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.BookApiDTO;
import com.mieker.ifpr.shelfie.dto.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.UpdateMyBooksDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.mapper.MyBooksMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final MyBooksMapper myBooksMapper;

    public MyBooksDTO create(UUID userId, String googleId, BookStatus bookStatus) throws ParseException {
        Optional<Book> optionalBook = bookRepository.findByGoogleId(googleId);
        MyBooks myBook = optionalBook.isPresent() ? addBookToUser(optionalBook.get().getId(), userId, bookStatus) : createBookAndAddToUser(googleId, userId, bookStatus);
        return myBooksMapper.myBookToMyBookDTO(myBook);
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

//    public MyBooks getMyBooksById(UUID id) {
//        MyBooksDTO myBooks = myBooksRepository.findById(id);
//        myBooks = myBooksMapper.myBookToMyBookDTO(myBooks);
//        return myBooks.orElse(null);
//    }


    public MyBooks updateMyBookDisable(UUID id) {
        MyBooks myBooksToUpdate = myBooksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + id));
        myBooksToUpdate = myBooksMapper.updateMyBooksDisabled(myBooksToUpdate);
        return myBooksRepository.save(myBooksToUpdate);
    }

    public MyBooks updateMyBook(UUID id, UpdateMyBooksDTO updateMyBooksDTO) {
        MyBooks myBooksToUpdate = myBooksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + id));
        myBooksToUpdate = myBooksMapper.updateMyBooks(myBooksToUpdate, updateMyBooksDTO);
        return myBooksRepository.save(myBooksToUpdate);
    }

    public List<MyBooksDTO> getAllMyBooks() {
        List<MyBooks> myBooks = myBooksRepository.findAll();
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }
}

// TODO:
// implementar o resto do crud
//      só falta o diable e o get

// TODO
// criar um método? que vai comparar se já tem o usuário e o livro associado com as IDS
// criar um método que vai pegar o id do tb_book com o googleId
// ver o negócio do enum como vou fazer para comparar
