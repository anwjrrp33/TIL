## [연결 요소의 개수](https://www.acmicpc.net/problem/11724)

### 문제
방향 없는 그래프가 주어졌을 때, 연결 요소 (Connected Component)의 개수를 구하는 프로그램을 작성하시오.

### 입력
첫째 줄에 정점의 개수 N과 간선의 개수 M이 주어진다. (1 ≤ N ≤ 1,000, 0 ≤ M ≤ N×(N-1)/2) 둘째 줄부터 M개의 줄에 간선의 양 끝점 u와 v가 주어진다. (1 ≤ u, v ≤ N, u ≠ v) 같은 간선은 한 번만 주어진다.

### 출력
첫째 줄에 연결 요소의 개수를 출력한다.

### 풀이
노드와 간선이 주어지는 그래프 문제인데, 해당 문제는 노드와 노드가 이어진 연결 요소의 개수를 구해야 한다. `DFS와 BFS` 어느 것을 선택해도 문제를 해결할 수 있는 문제인데 DFS가 좀 더 편하다고 느꼈고 DFS로 문제를 풀이했다. 언젠가 한번 DFS, 백트래킹과 BFS를 한번쯤 정리해야 머리 속에 정확히 분류가 가능할거 같다.

문제의 풀이는 노드와 노드를 2차원 배열로 구현했고 노드와 노드를 연결시켜서 DFS 재귀적인 방식으로 노드를 방문하면 플래그를 체크해서 다시 방문하지 않도록 구현했다.

코드는 아래와 같이 작성했다.

### 코드
```
// DFS를 활용한 풀이

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
```
