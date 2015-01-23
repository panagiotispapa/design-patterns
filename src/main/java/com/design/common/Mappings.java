package com.design.common;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Mappings {

    public static <T, R> List<R> fromList(List<T> in, Function<T, R> mapping) {
        return in.stream().map(mapping).collect(toList());

    }

    public static <T, R> List<List<R>> fromListOfLists(List<List<T>> in, Function<T, R> mapping) {
        return in.stream().map(t -> fromList(t, mapping)).collect(toList());

    }



}
