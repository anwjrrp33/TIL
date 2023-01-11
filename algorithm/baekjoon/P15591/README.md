## [MooTube (Silver)](https://www.acmicpc.net/problem/15591)

![52ca9fe2-c235-4960-87f7-02f2fa377387](https://user-images.githubusercontent.com/38122225/211690255-7dba44ef-6705-4479-8407-6414986af8b0.png)

처음에 문제를 보고는 설명이 복잡했다. 문제 내용의 핵심은 `N까지의 영상이 존재하는데 영상끼리 가는 경로는 무조건 존재하며 개수는 N - 1개라고 주어져서 이 문제는 신장 트리(Spanning Tree)`라는 것을 알 수 있다.

아직은 실력이 부족해서 그래프 관련 문제가 나오면 문제를 이해하는데 많은 시간을 할애하게 되는데 이럴 때는 그림으로 직접 예제를 그려보는게 좋은 점이 많은 거 같다.

![캡처](https://user-images.githubusercontent.com/38122225/211691857-dd4b65a4-99ec-4fc5-a3f2-6bbee2bcd453.PNG)\

### 예제 입력

```
4 3
1 2 3
2 3 2
2 4 4
1 2
4 1
3 1
```

입력된 예제를 보면 동영상의 개수는 4개 간선의 개수는 3개인데 1, 2 동영상은 3이라는 두 동영상이 서로 얼마나 가까운 지를 측정하는 단위인 `USADO`를 입력받았다.

2, 3은 2(USADO)을 2, 4은 4(USADO)를 입력받았다. 시작 동영상이 2이고 1(USADO)인 경우 3개의 동영상, 시작 동영상이 1이고 4(USADO)인 경우 0개의 동영상, 시작 동영상이 1이고 3(USADO)인 경우 2개의 동영상이 추천된다.

문제의 설명 자체를 그림으로 정리하고 핵심만 요약하면 구현 자체는 단순 BFS로 구현이 가능하고 코드는 아래와 같다.

### 코드

```
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class P15591 {
    static ArrayList<Video> videos[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        videos = new ArrayList[N + 1];

        for(int i = 0; i < N+1; i++) {
            videos[i] = new ArrayList<>();
        }

        for(int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            videos[p].add(new Video(q, r));
            videos[q].add(new Video(p, r));
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            sb.append(BFS(k, v)).append("\n");
        }

        System.out.println(sb);
    }

    static int BFS(int k, int v) {
        int count = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(v);

        boolean check[] = new boolean[videos.length];
        check[v] = true;

        while (!pq.isEmpty()) {
            int no = pq.poll();
            for (Video video: videos[no]) {
                if(!check[video.no] && k <= video.usado) {
                    pq.offer(video.no);
                    check[video.no] = true;
                    count++;
                }
            }
        }

        return count;
    }

    static class Video {
        private int no;
        private int usado;

        Video(int no, int usado){
            this.no = no;
            this.usado = usado;
        }
    }
}
```
