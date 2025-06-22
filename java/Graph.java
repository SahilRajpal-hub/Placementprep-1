package Placementprep.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {

    static class Pair{
        int val;
        int parent;
        
        Pair(int val,int parent){
            this.val=val;
            this.parent=parent;
        }
    }
    
    // *** Traversals
    // * BFS
    // ? TC = O(V + 2*E) , SC = O(2*V
    public ArrayList<Integer> bfs(ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> ans = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[adj.size()+1];
        for(int i=0; i<=adj.size(); i++) visited[i]=false;
        
        q.add(0);
        visited[0]=true;
        
        while(!q.isEmpty()){
            int ele = q.poll();
            ans.add(ele);
            
            for(int i:adj.get(ele)){
                if(!visited[i]){
                    q.add(i);
                    visited[i]=true;
                }
            }
        }
        
        return ans;
    }

    // * DFS
    public void dfsHelper(ArrayList<ArrayList<Integer>> adj,ArrayList<Integer> ans,boolean[] visited,int vertex){
        visited[vertex]=true;
        ans.add(vertex);
        
        for(int i:adj.get(vertex)){
            if(!visited[i]){
                dfsHelper(adj,ans,visited,i);
            }
        }
        
    }
    public ArrayList<Integer> dfs(ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> ans = new ArrayList<>();
        boolean[] visited = new boolean[adj.size()+1];
        dfsHelper(adj,ans,visited,0);
        /*
         * for unconnected components we can add a loop here for all vertices not visited
         * for(int i:vertces){
         *      if(!visited[i]){
         *          dfsHelper(adj,ans,visited,i);
         *      }
         * }
         */
        return ans;
    }

    




    // *** Cycle detection 

    // ** IN Undirected Graph
    // * using BFS
    //? TC = O(V+2E) , SC = O(V)
    public boolean isCycleBFS(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0; i < V; i++) adj.add(new ArrayList<>());
    
        // Convert edge list to adjacency list
        for(int[] edge : edges){
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]); // undirected graph
        }
    
        
        Queue<Pair> q = new LinkedList<>();
        boolean[] visited = new boolean[V];
        for(int i=0; i<V; i++) visited[i]=false;
        
        for(int i=0; i<V; i++){
            if(!visited[i]){
                q.add(new Pair(i,-1));
                visited[i]=true;
                
                while(!q.isEmpty()){
                    Pair ele = q.poll();
                    
                    for(int n:adj.get(ele.val)){
                        if(!visited[n]){
                            q.add(new Pair(n,ele.val));
                            visited[n]=true;
                        }else{
                            if(n!=ele.parent) return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    // * using DFS
    //? TC = O(V+2E) , SC = O(V)
    public boolean dfsHelper(List<List<Integer>> edges, boolean[] visited, int v, int parent) {
        visited[v] = true;
        for(int neighbor : edges.get(v)) {
            if(visited[neighbor]) {
                if(neighbor != parent) return true; // found a back edge (cycle)
            } else {
                if(dfsHelper(edges, visited, neighbor, v)) return true;
            }
        }
        return false;
    }
    public boolean isCycle(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0; i < V; i++) adj.add(new ArrayList<>());
    
        // Convert edge list to adjacency list
        for(int[] edge : edges){
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]); // undirected graph
        }
    
        boolean[] visited = new boolean[V];
        for(int i = 0; i < V; i++){ // loop for unconnected components
            if(!visited[i]){
                if(dfsHelper(adj, visited, i, -1)) return true;
            }
        }
        return false;
    } 


    // ** IN Directed Graph
    




    // *** Topological Sort (Possible for only DAG)
    /*
     * Definition : Topological Sort is an ordering of the vertices of a Directed Acyclic Graph (DAG) such that For every
     * directed edge u → v, u comes before v in the ordering.
     * 
     * Uses Cases : Task scheduling (e.g., build systems, project dependencies), Course prerequisite order,
     * Compilation order of files , Dependency resolution
     */

    // * Using DFS
    static void dfs(int node, List<List<Integer>> adj, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adj, visited, stack);
            }
        }
        stack.push(node);
    }
    public static ArrayList<Integer> topoSort(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0; i < V; i++) adj.add(new ArrayList<>());
    
        // Convert edge list to adjacency list
        for(int[] edge : edges){
            adj.get(edge[0]).add(edge[1]);
        }
        
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> ans = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, adj, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            ans.add(stack.pop());
        }
        return ans;
    }

    // * Using BFS (Kahn’s Algo)
    void topoSortKahn(int V, List<List<Integer>> adj) {
        int[] indegree = new int[V];
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj.get(i)) {
                indegree[neighbor]++;
            }
        }
    
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) q.add(i);
        }
    
        while (!q.isEmpty()) {
            int node = q.poll();
            System.out.print(node + " ");
    
            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) q.add(neighbor);
            }
        }
    }
    


   


}


