package day10

import read2DCharArray

class Solution10(filePath: String) {
    private var m: List<CharArray> = read2DCharArray(filePath)
    private var visited: List<IntArray> = List(m.size) { IntArray(m[0].size) { -1 } }

    fun part1() {
        var result = 0
        for (i in m.indices) {
            for (j in m[0].indices) {
                if (m[i][j] != '0') continue
                val score = trailheadScore(i, j, i * m.size + j)
//                if (score != 0) println("$score ($i $j)")
                result += score
            }
        }

        println(result)
    }

    private fun trailheadScore(i: Int, j: Int, id: Int): Int {
//        visited[i][j] = id
        if (m[i][j] == '9') return 1

        var result = 0
        if (i - 1 >= 0 && m[i - 1][j] == m[i][j] + 1 && visited[i - 1][j] != id) {
            result += trailheadScore(i - 1, j, id)
        }
        if (i + 1 < m.size && m[i + 1][j] == m[i][j] + 1 && visited[i + 1][j] != id) {
            result += trailheadScore(i + 1, j, id)
        }
        if (j - 1 >= 0 && m[i][j - 1] == m[i][j] + 1 && visited[i][j - 1] != id)
            result += trailheadScore(i, j - 1, id)
        if (j + 1 < m[0].size && m[i][j + 1] == m[i][j] + 1 && visited[i][j + 1] != id)
            result += trailheadScore(i, j + 1, id)

        return result
    }
}

fun main() {
    val s = Solution10("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day10/input.txt")
    s.part1()
}
