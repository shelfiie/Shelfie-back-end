//package com.mieker.ifpr.shelfie.controller;
//
//import com.mieker.ifpr.shelfie.service.ImageService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@CrossOrigin(origins = "*")
//@RestController
//@AllArgsConstructor
//@RequestMapping("/api/users/image")
//public class ImageUploadController {
//    @Autowired
//    private ImageService imageService;
//
//    @PostMapping
//    public ResponseEntity<?> uploadImage(@RequestParam ("image") MultipartFile file) {
//        String uploadImage = imageService.uploadImage(file);
//        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
//    }
//
////    todo:
////    rotas de update
////    rotas de get
////    rotas de delete (essse deleta do banco e do storage)
//
//
//}
