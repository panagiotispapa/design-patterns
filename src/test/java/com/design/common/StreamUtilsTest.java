package com.design.common;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class StreamUtilsTest {

    @Test
    public void testZip() throws Exception {

        List<Integer> actual = StreamUtils.zip(Stream.of(2, 4, 8), Stream.of(3, 7), (a, b) -> a * b).collect(toList());

        Assertions.assertThat(actual).isEqualTo(Arrays.asList(6, 28));

    }
}