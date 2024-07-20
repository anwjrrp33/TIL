package algorithm.baekjoon.`P2852-NBA 농구`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine().toInt()
    val teamGoal = IntArray(3)
    val teamTime = IntArray(3)
    var lastTime = 0

    repeat(input) {
        val information = bufferedReader.readLine().split(" ")
        val goalTeam = information[0].toInt()
        val timeParts = information[1].split(":")
        val goalTime = timeParts[0].toInt() * 60 + timeParts[1].toInt()

        when {
            teamGoal[1] > teamGoal[2] -> teamTime[1] += goalTime - lastTime
            teamGoal[2] > teamGoal[1] -> teamTime[2] += goalTime - lastTime
        }

        teamGoal[goalTeam] += 1
        lastTime = goalTime
    }

    val endTime = 48 * 60
    when {
        teamGoal[1] > teamGoal[2] -> teamTime[1] += endTime - lastTime
        teamGoal[2] > teamGoal[1] -> teamTime[2] += endTime - lastTime
    }

    bufferedWriter.write(String.format("%02d:%02d", teamTime[1] / 60, teamTime[1] % 60) + "\n")
    bufferedWriter.write(String.format("%02d:%02d", teamTime[2] / 60, teamTime[2] % 60))
    bufferedWriter.flush()
    bufferedWriter.close()
}