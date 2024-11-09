package algorithm.baekjoon.`P2644-촌수계산`

import java.util.*
import java.io.*

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val n = br.readLine().toInt()
    val dist = IntArray(n + 1)
    val graph = Array(n + 1) { IntArray(n + 1) }

    val st = StringTokenizer(br.readLine())
    val start = st.nextToken().toInt()
    val end = st.nextToken().toInt()

    val line = br.readLine().toInt()

    repeat(line) {
        val edge = StringTokenizer(br.readLine())
        val a = edge.nextToken().toInt()
        val b = edge.nextToken().toInt()
        graph[a][b] = 1
        graph[b][a] = 1
    }

    bfs(start, graph, dist)

    println(if (dist[end] == 0) -1 else dist[end])
}

fun bfs(start: Int, graph: Array<IntArray>, dist: IntArray) {
    val queue: Queue<Int> = LinkedList()
    queue.add(start)

    while (queue.isNotEmpty()) {
        val current = queue.poll()

        for (i in graph.indices) {
            if (graph[current][i] == 1 && dist[i] == 0) {
                queue.add(i)
                dist[i] = dist[current] + 1
            }
        }
    }
}