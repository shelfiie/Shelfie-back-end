package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.ReadingProgress.CollectionOfMyBooksDTO;
import com.mieker.ifpr.shelfie.entity.ReadingProgress;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReadingProgressMapper {
    private final ModelMapper mapper;

    public ReadingProgressMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CollectionOfMyBooksDTO readingProgressToCollectionOfMyBooks(ReadingProgress rp) {
        return mapper.map(rp, CollectionOfMyBooksDTO.class);
    }
}
