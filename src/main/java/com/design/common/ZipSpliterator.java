package com.design.common;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ZipSpliterator<L, R, O> implements Spliterator<O> {

    private final Spliterator<L> lefts;
    private final Spliterator<R> rights;
    private final BiFunction<L, R, O> combiner;

    public static <L, R, O> Spliterator of(Spliterator<L> lefts, Spliterator<R> rights, BiFunction<L, R, O> combiner) {
        return new ZipSpliterator<>(lefts, rights, combiner);
    }

    private ZipSpliterator(Spliterator<L> lefts, Spliterator<R> rights, BiFunction<L, R, O> combiner) {
        this.lefts = lefts;
        this.rights = rights;
        this.combiner = combiner;
    }

    @Override
    public boolean tryAdvance(Consumer<? super O> action) {
        AtomicBoolean hasNext = new AtomicBoolean(false);
        lefts.tryAdvance(l -> rights.tryAdvance(r -> {
            hasNext.set(true);
            action.accept(combiner.apply(l, r));
        }));
        return hasNext.get();
    }

    @Override
    public Spliterator<O> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Math.min(lefts.estimateSize(), rights.estimateSize());
    }

    @Override
    public int characteristics() {
        return lefts.characteristics() & rights.characteristics()
                & ~(DISTINCT | SORTED);
    }
}
