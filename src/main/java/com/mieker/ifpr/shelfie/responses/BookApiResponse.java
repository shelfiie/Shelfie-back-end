package com.mieker.ifpr.shelfie.responses;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class BookApiResponse {
    private String id;
    private VolumeInfo volumeInfo;

    @Data
    public static class VolumeInfo {
        private String title;
        private List<IndustryIdentifier> industryIdentifiers;

        @Data
        public static class IndustryIdentifier {
            private String type;
            private String identifier;
        }
    }
}


