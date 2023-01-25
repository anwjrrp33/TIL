package algorithm.baekjoon.연결요소의개수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class P11724 {

    private static ArrayList<Integer>[] A;

    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        A = new ArrayList[N + 1];
        visited = new boolean[N + 1];

        for (int i = 1; i <= N; i++) {
            A[i] = new ArrayList<Integer>();
        }

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken()), E = Integer.parseInt(st.nextToken());

            A[S].add(E);
            A[E].add(S);
        }

        int answer = 0;

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                answer++;
                dfs(i);
            }
        }

        System.out.println(answer);
    }

    private static void dfs(int V) {
        if (visited[V]) {
            return;
        }

        visited[V] = true;

        for (int i : A[V]) {
            if (visited[i] == false) {
                dfs(i);
            }
        }
    }
}
