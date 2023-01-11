package algorithm.baekjoon.P15591;

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