package com.mieker.ifpr.shelfie.entities;

import com.mieker.ifpr.shelfie.entities.enumeration.BookStatus;

import java.util.Date;

public class MyBooks {
    private Long id;
    private User user;
    private Book book;
    private BookStatus bookStatus;
    private Date createdAt;

}
