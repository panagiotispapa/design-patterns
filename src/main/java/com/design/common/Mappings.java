package com.design.common;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Mappings {

    public static <T> Function<T, T> chain(Function<T, T>... ops) {
        return Stream.of(ops).reduce(t -> t, Function::andThen);
    }

    public static <T> List<List<T>> concat(List<List<T>>... lists) {
        return Lists.newArrayList(Iterables.concat(lists));
    }

}
