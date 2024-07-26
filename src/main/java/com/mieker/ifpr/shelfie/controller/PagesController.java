package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.MyBooks.BookRelationDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgress.PageDTO;
import com.mieker.ifpr.shelfie.service.PageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/pages")
public class PagesController {
    private final PageService pageService;

    //    retorna a ultima página
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{bookId}")
    public ResponseEntity<PageDTO> getMyLastPage(@PathVariable UUID bookId) {
        PageDTO pageDTO = pageService.getMyLastPage(bookId);
        return ResponseEntity.status(200).body(pageDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mine")
    public ResponseEntity<BookRelationDTO> getMyBookStatus() {
        BookRelationDTO bookStatusDTO = pageService.getBookStatus();
        return ResponseEntity.status(200).body(bookStatusDTO);
    }

}
