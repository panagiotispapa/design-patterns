package com.design.common;

import com.googlecode.totallylazy.Callable1;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    public static <L, R, O> Stream<O> zip(Stream<L> lefts, Stream<R> rights, BiFunction<L, R, O> combiner) {
        return StreamSupport.stream(ZipSpliterator.of(lefts.spliterator(), rights.spliterator(), combiner), false);
    }

    public static <T, S> Callable1<? super T, ? extends S> m(Function<T, S> f) {
        return t -> f.apply(t);
    }

}
