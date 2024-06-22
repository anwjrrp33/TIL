import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main()  {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine().toInt()
    var result = mutableListOf<Int>()

    for (i in 0..input) {
        var beforeNumber = input
        var afterNumber = i
        val sequence = mutableListOf(beforeNumber, afterNumber)

        while (true) {
            val number = beforeNumber - afterNumber

            if (number < 0) {
                break
            }

            sequence.add(number)
            beforeNumber = afterNumber
            afterNumber = number
        }

        if (result.size < sequence.size) {
            result = sequence
        }
    }

    bufferedWriter.write("${result.size}\n${result.joinToString(separator = " ")}")
    bufferedWriter.flush()
    bufferedReader.close()
}