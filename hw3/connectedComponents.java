import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
public class connectedComponents {
    public static void main(String[] args) throws Exception{
        File file1 = new File("./file1.txt");
        Scanner scanner = new Scanner(file1);
        int edges = scanner.nextInt();
        int vertices = scanner.nextInt();
        adjacencyLists graph1 = new adjacencyLists(edges, vertices);
        while(scanner.hasNextInt()) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            graph1.addEdge(i, j);
        }
        System.out.print("File 1");
        graph1.components();
        System.out.println("\n");

        File file2 = new File("./file2.txt");
        scanner = new Scanner(file2);
        edges = scanner.nextInt();
        vertices = scanner.nextInt();
        adjacencyLists graph2 = new adjacencyLists(edges, vertices);
        while(scanner.hasNextInt()) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            graph2.addEdge(i, j);
        }
        System.out.print("File 2");
        graph2.components();
        System.out.println("\n");

        File file3 = new File("./file3.txt");
        scanner = new Scanner(file3);
        edges = scanner.nextInt();
        vertices = scanner.nextInt();
        adjacencyLists graph3 = new adjacencyLists(edges, vertices);
        while(scanner.hasNextInt()) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            graph3.addEdge(i, j);
        }
        System.out.print("File 3");
        graph3.components();
        System.out.println("\n");

        File file4 = new File("./file4.txt");
        scanner = new Scanner(file4);
        edges = scanner.nextInt();
        vertices = scanner.nextInt();
        adjacencyLists graph4 = new adjacencyLists(edges, vertices);
        while(scanner.hasNextInt()) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            graph4.addEdge(i, j);
        }
        System.out.print("File 4");
        graph4.components();
        System.out.println("\n");
        
    }
}

class adjacencyLists {
    int edges;
    int vertices;
    LinkedList<Integer> graph[];
    boolean[] visited;
    int components;

    public adjacencyLists(int edges, int vertices) {
        this.edges = edges;
        this.vertices = vertices;
        visited = new boolean[vertices];

        graph = new LinkedList[vertices];
        for(int i = 0; i<vertices; i++) {
            graph[i] = new LinkedList();
        }
    }

    public void addEdge(int i, int j) {
        int u = i-1;
        int v = j-1;
        graph[u].add(v);
        graph[v].add(u);
    }

    public void dfs(int vertex) {
        System.out.print((vertex+1)+" ");
        visited[vertex] = true;
        for(int i = 0; i<graph[vertex].size(); i++) {
            if(!visited[graph[vertex].get(i)]){
                dfs(graph[vertex].get(i));
            }
        }
    }

    public void components() {
        System.out.println("\nConnected Component #"+(++components));
        dfs(0);
        for(int i = 0; i<vertices; i++) {
            if(!visited[i]){
                System.out.println("\nConnected Component #"+(++components));
                dfs(i);
            }
        }
    }

}
