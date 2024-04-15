package com.mieker.ifpr.shelfie.service;


import com.mieker.ifpr.shelfie.dto.CollectionOfMyBooksDTO;
import com.mieker.ifpr.shelfie.dto.MyBooksDTO;
import com.mieker.ifpr.shelfie.dto.ReadingProgressDTO;
import com.mieker.ifpr.shelfie.entity.MyBooks;
import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import com.mieker.ifpr.shelfie.mapper.BookMapper;
import com.mieker.ifpr.shelfie.mapper.MyBooksMapper;
import com.mieker.ifpr.shelfie.repository.ReadingProgressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ReadingProgressService {
    private final ReadingProgressRepository rpRepository;
    private final MyBookService myBookService;
    private final MyBooksMapper myBooksMapper;

    public void create (ReadingProgressDTO rpDTO) {
        ReadingProgress rp = new ReadingProgress();
        MyBooksDTO mbDTO = myBookService.getMyBooksById(rpDTO.getMyBooksId());
        MyBooks myBooks = myBooksMapper.myBookDTOtoMyBook(mbDTO);
        rp.setMyBooks(myBooks);
        rp.setPage(rpDTO.getPage());
        rp.setCommentary(rpDTO.getCommentary());
        rpRepository.save(rp);
    }

//    arrumar isso aq
//    public CollectionOfMyBooksDTO getReadingProgressByMyBooksId(UUID userId, UUID myBooksId) {
////        return new CollectionOfMyBooksDTO(rpRepository.findByMyBooksId(myBooksId));
////        ai mexer nisso aq mas to cm pregui
//    }
}
