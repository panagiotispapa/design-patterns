package com.design.islamic.model;

public class Pair<T> {

    private final T a;
    private final T b;

    public Pair(T a, T b) {
        this.a = a;
        this.b = b;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    public static <T> Pair<T> newPair(T a, T b) {
        return new Pair<>(a,b);
    }
}
