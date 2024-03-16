package com.mieker.ifpr.shelfie.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookControler {
//    rota (googleid) -> service -> na service procura se tem esse id no banco -> se tiver adiciona no banco mais usuário
//    rota (googleid) -> service -> na service procura se tem esse id no banco -> se não tiver adiciona no banco tb_books
}
