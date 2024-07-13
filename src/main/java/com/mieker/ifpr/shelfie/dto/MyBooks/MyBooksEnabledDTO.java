package com.mieker.ifpr.shelfie.dto.MyBooks;

import com.mieker.ifpr.shelfie.entity.enumeration.BookStatus;
import lombok.Data;

@Data
public class MyBooksEnabledDTO {
    private boolean enabled;
    private BookStatus bookStatus;
}
