package com.example.lib_2sat;

import java.util.ArrayList;
import java.util.List;

public class RandomSolver {

    public boolean CheckSat(List<Integer> literalA, List<Integer> literalB) {
        System.out.println(literalA);
        System.out.println(literalB);
        for (int i=0; i<literalA.size(); i++) {
            System.out.println(i);
            System.out.println("A: " + literalA.get(i) + " ,B: "+ literalB.get(i));
            if (literalA.get(i)<0 && literalB.get(i)<0) {return false;}

        }
        return true;
    }

    public boolean FlipValue(int n, List<Integer> literalA, List<Integer> literalB ) {
        int count = 0;
        boolean check = CheckSat(literalA,literalB);
        System.out.println(check);
        while (!check && count< n*n) {
            int randomNum = (int)(Math.random() * n);
//            System.out.println(randomNum);
            for (int i=0; i<literalA.size(); i++) {
                if (Math.abs(literalA.get(i))==randomNum) {literalA.set(i, -literalA.get(i));}
                if (Math.abs(literalB.get(i))==randomNum) {literalB.set(i, -literalB.get(i));}
            }
            count++;
        }
        if (check) {return true;}
        else {return false;}
    }
}
