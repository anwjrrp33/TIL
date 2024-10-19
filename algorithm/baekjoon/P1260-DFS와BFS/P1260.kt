package algorithm.baekjoon.`P1260-DFS와BFS`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val st = StringTokenizer(br.readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()
    val v = st.nextToken().toInt()

    val graph = Array<Node?>(n + 1) { null }
    var visited = BooleanArray(n + 1)
    val sb = StringBuilder()

    for (i in 0 until m) {
        val st2 = StringTokenizer(br.readLine())
        val from = st2.nextToken().toInt()
        val to = st2.nextToken().toInt()

        if (graph[from] == null) {
            graph[from] = Node(to)
        } else {
            graph[from]!!.insert(Node(to))
        }
        if (graph[to] == null) {
            graph[to] = Node(from)
        } else {
            graph[to]!!.insert(Node(from))
        }
    }

    visited = BooleanArray(n + 1)
    dfs(v, graph, visited, sb)
    sb.append('\n')

    visited = BooleanArray(n + 1)
    bfs(v, graph, visited, sb)

    bw.write(sb.toString().trim())
    bw.flush()
    bw.close()
}

fun bfs(v: Int, graph: Array<Node?>, visited: BooleanArray, sb: StringBuilder) {
    val queue: Queue<Int> = LinkedList()
    queue.add(v)
    visited[v] = true
    sb.append("$v ")

    while (queue.isNotEmpty()) {
        val cur = queue.poll()

        var tmp = graph[cur]
        while (tmp != null) {
            if (!visited[tmp.idx]) {
                sb.append("${tmp.idx} ")
                visited[tmp.idx] = true
                queue.add(tmp.idx)
            }
            tmp = tmp.next
        }
    }
}

fun dfs(cur: Int, graph: Array<Node?>, visited: BooleanArray, sb: StringBuilder) {
    visited[cur] = true
    sb.append("$cur ")

    var tmp = graph[cur]
    while (tmp != null) {
        if (!visited[tmp.idx]) {
            dfs(tmp.idx, graph, visited, sb)
        }
        tmp = tmp.next
    }
}

class Node(var idx: Int) {
    var next: Node? = null

    fun insert(node: Node) {
        if (idx > node.idx) {
            val tmp = idx
            idx = node.idx
            node.idx = tmp
            node.next = next
            next = node
        } else if (next == null) {
            next = node
        } else {
            next!!.insert(node)
        }
    }
}
