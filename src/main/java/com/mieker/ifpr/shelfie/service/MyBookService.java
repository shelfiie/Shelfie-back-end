package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.Book.BookDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.MyBooksEnabledDTO;
import com.mieker.ifpr.shelfie.dto.MyBooks.UpdateMyBooksDTO;
import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import com.mieker.ifpr.shelfie.exception.IdNotFoundException;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.mapper.MyBooksMapper;
import com.mieker.ifpr.shelfie.repository.BookRepository;
import com.mieker.ifpr.shelfie.repository.MyBooksRepository;
import com.mieker.ifpr.shelfie.repository.UserRepository;
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

    private final MyBooksRepository myBooksRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookApiService bookApiService;
    private final BookService bookService;
    private final MyBooksMapper myBooksMapper;
    private Validation validation;


    //    criar associação do livro ao usuário
    public MyBooksDTO create(String googleId, BookStatus bookStatus) throws ParseException {
        UUID userId = validation.userAuthenticator();
        Optional<Book> optionalBook = bookRepository.findByGoogleId(googleId);
        MyBooks myBook = optionalBook.isPresent() ? addBookToUser(optionalBook.get().getId(), userId, bookStatus) : createBookAndAddToUser(googleId, userId, bookStatus);
        return myBooksMapper.myBookToMyBookDTO(myBook);
    }

//    adicionar o livro no banco
    public MyBooks addBookToUser(UUID bookId, UUID userId, BookStatus bookStatus) {
        MyBooks myBooks = new MyBooks();
        User user =  userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        myBooks.setBook(book);
        myBooks.setUser(user);
        myBooks.setBookStatus(bookStatus);
        return myBooksRepository.save(myBooks);
    }

//    criar o livro e relacionar ao usuário
    public MyBooks createBookAndAddToUser(String googleId, UUID userId, BookStatus bookStatus) throws ParseException {
        BookDTO bookDTO = bookApiService.getBookByGoogleId(googleId);
        Book book = bookService.createBook(googleId);
        return addBookToUser(book.getId(), userId, bookStatus);
    }

//    pegar o livro pelo id
    public MyBooksDTO getMyBooksById(UUID id) {
        MyBooks myBooks = myBooksRepository.findById(id).orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + id));
        return myBooksMapper.myBookToMyBookDTO(myBooks);
    }

//  atualizar o livro como disabled
    public MyBooksDTO updateMyBooksDisable(UUID id) {
        MyBooks myBooksToUpdate = myBooksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + id));
        myBooksToUpdate.setEnabled(false);
        myBooksRepository.save(myBooksToUpdate);
        return myBooksMapper.updateMyBooksDisabled(myBooksToUpdate);
    }

//    esse é pra atualizar o status do livro
    public UpdateMyBooksDTO updateMyBooks(String googleId, BookStatus bookStatus) {
        UUID userId = validation.userAuthenticator();
        UUID bookId = bookRepository.findByGoogleId(googleId).orElseThrow(() -> new IdNotFoundException("Não existe livro com esse googleId: " + googleId)).getId();
        MyBooks myBooks = myBooksRepository.findMyBooksByBookIdAndUserId(bookId, userId);
        if (myBooks == null) {
            throw new NotFoundException("Esse livro não está na biblioteca do usuário.");
        }
        if (!myBooks.isEnabled()) {
            myBooks.setEnabled(true);
        }
        myBooks.setBookStatus(bookStatus);
        myBooksRepository.save(myBooks);
        return myBooksMapper.updateMyBooks(myBooks);
    }

//    retorna a lista de todos os mybooks do banco
    public List<MyBooksDTO> getAllMyBooks() {
        List<MyBooks> myBooks = myBooksRepository.findAll();
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }

//    pega todos mybooks de um usuario
    public List<MyBooksDTO> getMyBooksByUserId() {
        UUID userId = validation.userAuthenticator();
        List<MyBooks> myBooks = myBooksRepository.findAllByUserIdAndEnabledTrue(userId);
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }

//    pega todos os mybooks de um livro
    public List<MyBooksDTO> getMyBooksByBookId(UUID bookId) {
        List<MyBooks> myBooks = myBooksRepository.findAllByBookId(bookId);
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }

    public List<MyBooksDTO> getMyBooksByStatus(BookStatus bookStatus) {
        UUID userId = validation.userAuthenticator();
        List<MyBooks> myBooks = myBooksRepository.findAllByUserIdAndBookStatus(userId, bookStatus);
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }

    public MyBooksEnabledDTO isEnabled(String googleId) {
        UUID userId = validation.userAuthenticator();
        Book book = bookRepository.findByGoogleId(googleId).orElseThrow(() -> new IdNotFoundException("Não existe livro com esse googleId: " + googleId));
        MyBooks myBooks = myBooksRepository.findByUserIdAndBookId(userId, book.getId());
        if (myBooks != null) {
            return myBooksMapper.myBookToMyBookEnabledDTO(myBooks);
        } else {
            throw new NotFoundException("Esse livro não está na biblioteca do usuário.");
        }

    }
}