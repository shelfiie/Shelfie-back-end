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

//    criar associação do livro ao usuário
    public MyBooksDTO create(UUID userId, String googleId, BookStatus bookStatus) throws ParseException {
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
    public MyBooks createBookAndAddToUser(String googleId,UUID userId,BookStatus bookStatus) throws ParseException {
        BookApiDTO bookDTO = bookApiService.getBookByGoogleId(googleId);
        Book book = bookService.createBook(bookDTO);
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
    public UpdateMyBooksDTO updateMyBooks(UUID id, BookStatus bookStatus) {
        MyBooks myBooksToUpdate = myBooksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + id));
        this.updateStatus(myBooksToUpdate, bookStatus);
        return myBooksMapper.updateMyBooks(myBooksToUpdate);
    }

    public void updateStatus(MyBooks myBooks, BookStatus bookStatus) {
        myBooks.setBookStatus(bookStatus);
        myBooksRepository.save(myBooks);
    }

//    retorna a lista de todos os mybooks do banco
    public List<MyBooksDTO> getAllMyBooks() {
        List<MyBooks> myBooks = myBooksRepository.findAll();
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }

//    pega todos mybooks de um usuario
    public List<MyBooksDTO> getMyBooksByUserId(UUID id) {
        List<MyBooks> myBooks = myBooksRepository.findAllByUserIdAndEnabledTrue(id);
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }

//    pega todos os mybooks de um livro
    public List<MyBooksDTO> getMyBooksByBookId(UUID bookId) {
        List<MyBooks> myBooks = myBooksRepository.findAllByBookId(bookId);
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }
}