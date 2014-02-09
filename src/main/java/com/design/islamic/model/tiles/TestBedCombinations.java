package com.design.islamic.model.tiles;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class TestBedCombinations {
    public static void main(String[] args) {
        ICombinatoricsVector<String> initialVector = Factory.createVector(
                new String[]{"red", "black", "white", "green", "blue"});


        // Create a simple combination generator to generate 3-combinations of the initial vector
        Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, 3);

        // Print all possible combinations
        for (ICombinatoricsVector<String> combination : gen) {
            System.out.println(combination.getVector());
        }

    }

}
