## [네트워크 연결](https://www.acmicpc.net/problem/15591)
![문제](https://user-images.githubusercontent.com/38122225/212319752-b96087aa-1d7e-4048-ab4d-a48d31775221.png)

모든 컴퓨터를 연결하는데 필요한 최소비용을 구하는 문제인데 `최소 신장 트리(Minimum Spanning Tree)`를 의미한다. 아직 그래프 문제를 푸는데 익숙하지 않아서 먼저 그림으로 그래프를 그려본 뒤 문제를 풀었다.

![그래프](https://user-images.githubusercontent.com/38122225/212319917-4ccc4cf4-d035-4791-b8d6-a1b27e792651.PNG)

최소 신장 트리의 경우 `Kruskal MST` 알고리즘을 통해서 구현하는데 탐욕적인 방법(greedy method) 을 이용하여 네트워크(가중치를 간선에 할당한 그래프)의 모든 정점을 최소 비용으로 연결하는 최적 해답을 구하는 것이라고 정의할 수 있다. 코드는 아래와 같이 구현했다.

이 문제의 핵심은 저장한 컴퓨터와 컴퓨터, Cost를 하나로 묶어서 낮은 Cost 순서로 정렬 후 Union-Find로 구현해야한다. 자바 객체를 통해서 Edge를 구현했지만 이차원 배열을 통해서도 구현이 가능하다.

그래프, 신장 트리, 최소 신장트리는 블로그에 한번 정리하는게 좋을거 같다.

### 코드
`
public class P1922 {

    static int[] parent;

	static ArrayList<Edge> edgeList;

    public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());

		edgeList = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			edgeList.add(new Edge(start, end, weight));
		}

		parent = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			parent[i] = i;
		}

		Collections.sort(edgeList);

		int ans = 0;
		for (int i = 0; i < edgeList.size(); i++) {
			Edge edge = edgeList.get(i);

			if (find(edge.start) != find(edge.end)) {
				ans += edge.weight;
				union(edge.start, edge.end);
			}
		}

		System.out.println(ans);
	}

	public static int find(int x) {
		if (x == parent[x]) {
			return x;
		}

		return parent[x] = find(parent[x]);
	}

	public static void union(int x, int y) {
		x = find(x);
		y = find(y);

		if (x != y) {
			parent[y] = x;
		}
	}
}

class Edge implements Comparable<Edge> {
	int start;

	int end;

	int weight;

	Edge(int start, int end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge o) {
		return weight - o.weight;
	}
}
`