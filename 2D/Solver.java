package com.example.lib_2sat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Solver {

    public Stack<Integer> S = new Stack<>();
    public HashMap<Integer,Boolean> visited = new HashMap<>();
    public HashMap<Integer,Boolean> visitedRev = new HashMap<>();
    public HashMap<Integer, List<Integer>> adj = new HashMap<>();
    public HashMap<Integer, List<Integer>> adjRev = new HashMap<>();
    public HashMap<Integer, Integer> scc = new HashMap<>();
    public HashMap<Integer, Boolean> solution = new HashMap<>();
    int counter = 1;

    public void addEdge(int a, int b){
        if(adj.get(a)==null){
            adj.put(a,new ArrayList<Integer>());
        }
        adj.get(a).add(b);
    }

    public void addEdgeRev(int a,int b){
        if(adjRev.get(b)==null){
            adjRev.put(b,new ArrayList<Integer>());
        }
        adjRev.get(b).add(a);

    }

    public void dfs1(int u){
        if(visited.get(u)==true) return;
        visited.put(u,true);
        for(int i=0;i<adj.get(u).size();i++){
            dfs1(adj.get(u).get(i));
        }
        S.push(u);
    }

    public void dfs2(int u){
        if(visitedRev.get(u)==true) return;
        visitedRev.put(u,true);
        for(int i=0;i<adjRev.get(u).size();i++){
            dfs2(adjRev.get(u).get(i));
        }
        scc.put(u,counter);
    }


    //this is void, but you can access the graph by calling the public adj and adjRev maps
    public void solve(int n, int m, List<Integer> literalA, List<Integer> literalB){
        for(int i=0;i<m;i++){
            if(literalA.get(i)>0 && literalB.get(i)>0){
                addEdge(literalA.get(i)+n,literalB.get(i));
                addEdgeRev(literalA.get(i)+n,literalB.get(i));
                addEdge(literalB.get(i)+n,literalA.get(i));
                addEdgeRev(literalB.get(i)+n,literalA.get(i));
            }
            else if(literalA.get(i)>0 && literalB.get(i)<0){
                addEdge(literalA.get(i)+n,n-literalB.get(i));
                addEdgeRev(literalA.get(i)+n,n-literalB.get(i));
                addEdge(-literalB.get(i),literalA.get(i));
                addEdgeRev(-literalB.get(i),literalA.get(i));
            }
            else if(literalA.get(i)<0 && literalB.get(i)>0){
                addEdge(-literalA.get(i),literalB.get(i));
                addEdgeRev(-literalA.get(i),literalB.get(i));
                addEdge(literalB.get(i)+n,n-literalA.get(i));
                addEdgeRev(literalB.get(i)+n,n-literalA.get(i));
            }
            else{
                addEdge(-literalA.get(i),n-literalB.get(i));
                addEdgeRev(-literalA.get(i),n-literalB.get(i));
                addEdge(-literalB.get(i),n-literalA.get(i));
                addEdgeRev(-literalB.get(i),n-literalA.get(i));
            }
        }
        System.out.println("adj: " + adj);
        System.out.println("adjRev: " + adjRev);
        /**
         n is number of variables
         so 2n is number of nodes.
         You want to do dfs on nodes, hence 2n
         **/

        // Set all Bool var to false first.
        for(int i=1;i<=2*n;i++){
            if(visited.get(i)==null){
                visited.put(i,false);
            }
            if(visitedRev.get(i)==null){
                visitedRev.put(i,false);
            }
        }
        // Use DFS to add to stack
        for(int i=1;i<=2*n;i++){
            if(visited.get(i)==false){
                dfs1(i);
            }
        }
        System.out.println("cm2");
        System.out.println("S: " + S);

        // Group into SCCs
        while(!S.isEmpty()){
            int node = S.pop();
            if(visitedRev.get(node)==false){
                dfs2(node);
                counter++;
            }
        }
        System.out.println(scc);

        for(int i=1;i<=n;i++){
            if(scc.get(i) == scc.get(i+n)){
                System.out.println("UNSATISFIABLE");
                return;
            }
        }

        System.out.println("SATISFIABLE");
        generateSolutions();
        return;
    }
    // method assigns boolean variables to find a feasible solution
    public void generateSolutions() {
        for (int i = counter-1; i >= 1; i--) {
            System.out.println(scc.values());
            for (Integer j: scc.values()) {
                System.out.println(i + "," + j);
                if (!solution.containsKey(j)) {
                    solution.put(j, true);
                    solution.put(-j, false);
                }
            }
        }
        System.out.println(solution);
//        for (int i = 1; i <= solution.size() / 2; i++) {
//            boolean v = solution.get(i);
//            if (v) {
//                System.out.print("1 ");
//            } else {
//                System.out.print("0 ");
//            }
//        }
    }

}

