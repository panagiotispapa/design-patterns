package com.design.common;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Mappings {

    public static <T, R> Function<List<T>, List<R>> fromList(Function<T, R> mapping) {
        return in -> in.stream().map(mapping).collect(toList());

    }

    public static <T, R> Function<List<List<T>>, List<List<R>>> fromListOfLists(Function<T, R> mapping) {
        return in -> in.stream().map(t -> fromList(mapping).apply(t)).collect(toList());
    }

    public static <T> Function<List<List<T>>, List<List<T>>> combine() {
        return pair -> {
            final int size = pair.get(0).size();
            return IntStream.range(0, size).mapToObj(i ->
                            pair.stream().map(l -> l.get(i)).collect(toList())
            ).collect(toList());
        };

    }

    public static <T> Function<Pair<List<T>, List<T>>, List<List<T>>> combine(List<Integer> connections) {
        return pair -> {
            final List<T> left = pair.getLeft();
            final List<T> right = pair.getRight();
            final int size = left.size();
            return IntStream.range(0, size).mapToObj(i ->
                            connections.stream().map(c -> asList(left.get(i), right.get((c + i) % size)))
            ).flatMap(s -> s).collect(toList());
        };
    }

    public static <T> Function<Pair<List<T>, List<List<T>>>, List<List<T>>> combineFull() {
        return pair -> {
            final List<T> left = pair.getLeft();
            final List<List<T>> right = pair.getRight();
            final int size = left.size();

            return IntStream.range(0, size).mapToObj(i ->
                            right.get(i).stream().map(r -> asList(left.get(i), r))
            ).flatMap(s -> s).collect(toList());

//            return IntStream.rangóe(0, size).mapToObj(i ->
//                    connections.stream().map(c -> asList(left.get(i), right.get((c + i) % size)))
//            ).flatMap(s -> s).collect(toList());
        };
    }

    public static <T> Function<List<List<T>>, List<T>> toFlatMapList() {
        return t -> t.stream().map(Collection::stream).flatMap(s -> s).collect(toList());
    }

//    public static <I, O> Function<I, O> chaining(Function<I, O> first, Function<I, O>... rest) {
//        return Stream.of(rest).reduce(first, Function::andThen);
//    }

    public static <T> List<List<T>> concat(List<List<T>>... lists) {
        return Lists.newArrayList(Iterables.concat(lists));
    }

}
