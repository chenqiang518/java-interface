package com.vernon.source;

import java.util.stream.Stream;

public class SearchSource {

    public static Stream<String> searchParams() {
        return Stream.of("available","pending","sold");
    }
}
