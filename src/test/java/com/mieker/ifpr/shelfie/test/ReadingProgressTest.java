package com.mieker.ifpr.shelfie.test;

import com.mieker.ifpr.shelfie.service.ReadingProgressService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReadingProgressTest {
    @Mock
    private ReadingProgressService readingProgressService;

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void testAddReadingProgress() {

    }
}
