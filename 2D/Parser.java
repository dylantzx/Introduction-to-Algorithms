package com.example.lib_2sat;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Parser {
    public static void main(String[] args) throws IOException {
        int numClauses = 0;
        int numVariables = 0;
        List<Integer> literalA = new ArrayList<>();
        List<Integer> literalB =  new ArrayList<>();

        try {
            //parsing file
            File file = new File("C:\\Users\\dylan\\AndroidStudioProjects\\2SAT\\lib_2sat\\src\\main\\java\\com\\example\\lib_2sat\\2sat_test.cnf");
            Scanner sc = new Scanner(file);
            boolean comment = true;

            while (comment) {
                String[] temp = sc.nextLine().split("\\s+");
                temp[0] = temp[0].toLowerCase();
                if (temp[0].equals("p")) {
                    comment = false;
                    numVariables = Integer.parseInt(temp[2]);
                    numClauses = Integer.parseInt(temp[3]);
                }
            }
            String line;
            int counter = 0;
            while (counter < numClauses) {
                line = sc.nextLine();

                if (!line.isEmpty()) {
                    line = line.trim();
                    String[] newLine = line.split("\\s+");
                    if (newLine.length>2){
                        literalA.add(Integer.parseInt(newLine[0]));
                        literalB.add((Integer.parseInt(newLine[1])));
                    }
                }
                counter++;

            }
            sc.close();


            //main logic
            System.out.println("Implication graph + kosaraju's algo");
            long started = System.nanoTime();
            //Solver3 solver = new Solver3();
            Solver2 solver = new Solver2(numVariables);
            solver.solve(numVariables,numClauses,literalA,literalB);
            long time = System.nanoTime();
            long timeTaken = time - started;
            System.out.println("Time:" + timeTaken / 1000000.0 + "ms");

            System.out.println("Randomized selection algo");
            long started2 = System.nanoTime();
            RandomSolver randomSolver = new RandomSolver();
            randomSolver.FlipValue(numVariables,literalA,literalB);
            long time2 = System.nanoTime();
            long timeTaken2 = time2 - started2;
            System.out.println("Time:" + timeTaken2 / 1000000.0 + "ms");


        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
