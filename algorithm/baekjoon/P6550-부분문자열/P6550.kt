import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    while (true) {
        val input = bufferedReader.readLine()

        if (input.isNullOrEmpty()) {
            break
        }

        val stringTokenizer = StringTokenizer(input)
        val s1 = stringTokenizer.nextToken()
        val s2 = stringTokenizer.nextToken()

        var index = 0

        for (char in s2) {
            if (s1[index] == char) {
                index += 1
            }
            if (index == s1.length) {
                break
            }
        }

        if (index == s1.length) {
            bufferedWriter.write("Yes\n")
        } else {
            bufferedWriter.write("No\n")
        }
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}