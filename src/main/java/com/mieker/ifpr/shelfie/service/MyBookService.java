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

//    criar associação do livro ao usuário
    public MyBooksDTO create(UUID userId, String googleId, BookStatus bookStatus) throws ParseException {
        Optional<Book> optionalBook = bookRepository.findByGoogleId(googleId);
        MyBooks myBook = optionalBook.isPresent() ? addBookToUser(optionalBook.get().getId(), userId, bookStatus) : createBookAndAddToUser(googleId, userId, bookStatus);
        return myBooksMapper.myBookToMyBookDTO(myBook);
    }

//    adicionar o livro no banco
    public MyBooks addBookToUser(UUID bookId, UUID userId, BookStatus bookStatus) {
        MyBooks myBooks = new MyBooks();
        User user =  userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);
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
    public MyBooks updateMyBooksDisable(UUID id) {
        MyBooks myBooksToUpdate = myBooksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + id));
        myBooksToUpdate = myBooksMapper.updateMyBooksDisabled(myBooksToUpdate);
        return myBooksRepository.save(myBooksToUpdate);
    }

//    todo
//    arrumar essa função

//    esse é pra atualizar o status do livro
    public UpdateMyBooksDTO updateMyBooks(UUID id, BookStatus bookStatus) {
        MyBooks myBooksToUpdate = myBooksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MyBooks not found with id: " + id));
        this.updateStatus(myBooksToUpdate, bookStatus);
//        System.out.println(myBooksToUpdate);
//        System.out.println(bookStatus);
//        myBooksRepository.save(myBooksToUpdate);
//        UpdateMyBooksDTO myBooksToUpdateDTO = myBooksMapper.updateMyBooks(myBooksToUpdate, bookStatus);
        System.out.println("HERER " + myBooksMapper.updateMyBooks(myBooksToUpdate));
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

    public List<MyBooksDTO> getMyBooksByUserId(UUID id) {
        List<MyBooks> myBooks = myBooksRepository.findAllByUserIdAndEnabledTrue(id);
        return myBooks.stream().map(myBooksMapper::myBookToMyBookDTO).collect(Collectors.toList());
    }

    public List<MyBooksDTO> getMyBooksByBookId(UUID bookId) {
        List<MyBooks> myBooks = myBooksRepository.findAllByBookId(bookId);
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
