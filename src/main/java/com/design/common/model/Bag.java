package com.design.common.model;

import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Bag<E> {


    final Map<E, AtomicInteger> countMap = Maps.newLinkedHashMap();

    public static <E> List<List<E>> computeCombinations(Iterable<E> eList) {
        Bag<E> bag = new Bag<E>();

        bag.countFor(eList);

        return bag.combinations();
    }

    public void countFor(Iterable<E> eList) {
        for (E e : eList) {
            countFor(e, 1);
        }

    }
    public void countFor(E e, int n) {
        countMap.put(e, new AtomicInteger(n));
    }

    private void decrement(E e) {
        AtomicInteger ai = countMap.get(e);
        if (ai.decrementAndGet() < 1)
            countMap.remove(e);
    }

    private void increment(E e) {
        AtomicInteger ai = countMap.get(e);
        if (ai == null)
            countMap.put(e, new AtomicInteger(1));
        else
            ai.incrementAndGet();
    }

    public List<List<E>> combinations() {
        List<List<E>> ret = new ArrayList<>();
        List<E> current = new ArrayList<>();
        combinations0(ret, current);
        return ret;
    }

    private void combinations0(List<List<E>> ret, List<E> current) {
        if (countMap.isEmpty()) {
            ret.add((current));
            return;
        }
        int position = current.size();
        current.add(null);
        List<E> es = new ArrayList<E>(countMap.keySet());
        if (es.get(0) instanceof Comparable)
            Collections.sort((List) es);
        for (E e : es) {
            current.set(position, e);
            decrement(e);
            combinations0(ret, current);
            increment(e);
        }
        current.remove(position);
    }

    //http://vanillajava.blogspot.co.uk/2012/01/generating-every-combination-without.html
    public static void main(String[] args) {
        /*
        Bag<Integer> b = new Bag<Integer>();
        b.countFor(0, 1);
        b.countFor(1, 1);
        b.countFor(2, 1);
//        b.countFor(3, 1);

//        Set<String> set = Sets.newLinkedHashSet();
        List<List<Integer>> combinations = b.combinations();
        for (List<Integer> list : combinations) {
            System.out.println(list);
//            String s = list.toString();
//            if (!set.add(s))
//                System.err.println("Duplicate entry " + s);
        }

        System.out.println(combinations.size());
                                                                                         */

        List<List<Integer>> combs = Bag.computeCombinations(Arrays.asList(0, 1));
        for (List<Integer> comb : combs) {
            System.out.println(comb);
        }

        System.out.println(combs.size());

    }

}
