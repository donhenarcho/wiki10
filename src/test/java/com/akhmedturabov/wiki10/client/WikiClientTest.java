package com.akhmedturabov.wiki10.client;

import static org.assertj.core.api.Assertions.*;

import java.io.*;
import java.util.stream.*;

import com.akhmedturabov.wiki10.entity.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

@ExtendWith(MockitoExtension.class)
public class WikiClientTest {

    @InjectMocks
    WikiClient wikiClient;

    private static Stream<Arguments> argumentsForTest() {
        return Stream.of(
                Arguments.of(55.1718d, 61.2783d, 0, null, 10),
                Arguments.of(-55.1718d, 61.2783d, 0, null, 0),
                Arguments.of(555.1718d, 61.2783d, 1, "Invalid coordinate provided", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsForTest")
    void test(double lat, double lon, int error, String errorMessage, int pages) throws IOException, InterruptedException {
        WikiResponse wikiResponse = wikiClient.getPages(lat, lon);
        assertThat(wikiResponse.getError()).isEqualTo(error);
        assertThat(wikiResponse.getErrorMessage()).isEqualTo(errorMessage);
        assertThat(wikiResponse.getPages().size()).isEqualTo(pages);
    }
}
