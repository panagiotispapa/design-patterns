package com.design.common;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Mappings {

    public static <T, R> Function<List<T>, List<R>> fromList(Function<T, R> mapping) {
        return in -> in.stream().map(mapping).collect(toList());

    }
    public static <T, R> Function<List<List<T>>, List<List<R>>> fromListOfLists(Function<T, R> mapping) {
        return in -> in.stream().map(t -> fromList(mapping).apply(t)).collect(toList());
    }
}
