package com.mieker.ifpr.shelfie.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class BookApiResponse {
    private List<Item> items;

    @Data
    public static class Item {
        @JsonAlias("id")
        private String googleId;
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
}


