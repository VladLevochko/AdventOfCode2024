package day4

import readFileLines
import kotlin.math.ceil

fun main() {
    val lines = readFileLines("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day4/input.txt")
    part2(lines)
}

fun part1(lines: List<String>) {
    val array = lines.map { it.toCharArray() }
    var result = 0
    val xmas = "XMAS".toCharArray()
    val samx = "SAMX".toCharArray()
    for (i in array.indices) {
        for (j in array[0].indices) {
            result += checkHorizontally(array, xmas, i, j)
            result += checkHorizontally(array, samx, i, j)

            result += countVertically(array, xmas, i, j)
            result += countVertically(array, samx, i, j)

            result += countDiagonally(array, xmas, i, j)
            result += countDiagonally(array, samx, i, j)

            result += countBackDiagonally(array, xmas, i, j)
            result += countBackDiagonally(array, samx, i, j)
        }
    }

    println(result)
}

fun part2(lines: List<String>) {
    val array = lines.map { it.toCharArray() }
    var result = 0

    val a = arrayOf(
        "M.M".toCharArray(),
        ".A.".toCharArray(),
        "S.S".toCharArray(),
    )
    val b = arrayOf(
        "S.M".toCharArray(),
        ".A.".toCharArray(),
        "S.M".toCharArray(),
    )
    val c = arrayOf(
        "M.S".toCharArray(),
        ".A.".toCharArray(),
        "M.S".toCharArray(),
    )
    val d = arrayOf(
        "S.S".toCharArray(),
        ".A.".toCharArray(),
        "M.M".toCharArray(),
    )

    for (i in 0..(array.size - 3)) {
        for (j in 0..(array[0].size - 3)) {
            result += checkX(array, a, i, j)
            result += checkX(array, b, i, j)
            result += checkX(array, c, i, j)
            result += checkX(array, d, i, j)
        }
    }

    println(result)
}

fun checkHorizontally(array: List<CharArray>, word: CharArray, i: Int, j: Int): Int {
    if (j + word.size > array[0].size) return 0
    for (k in word.indices) {
        if (array[i][j + k] != word[k]) return 0
    }
    return 1
}

fun countVertically(array: List<CharArray>, word: CharArray, i: Int, j: Int): Int {
    if (i + word.size > array.size) return 0
    for (k in word.indices) {
        if (array[i + k][j] != word[k]) return 0
    }
    return 1
}

fun countDiagonally(array: List<CharArray>, word: CharArray, i: Int, j: Int): Int {
    if (i + word.size > array.size || j + word.size > array[0].size) return 0
    for (k in word.indices) {
        if (array[i + k][j + k] != word[k]) return 0
    }
    return 1
}

fun countBackDiagonally(array: List<CharArray>, word: CharArray, i: Int, j: Int): Int {
    if (i + word.size > array.size || j < word.size - 1) return 0
    for (k in word.indices) {
        if (array[i + k][j - k] != word[k]) return 0
    }
    return 1
}

fun checkX(array: List<CharArray>, x: Array<CharArray>, i: Int, j: Int): Int {
    for (a in 0..x.size / 2) {
        val b = x.size - a - 1

        if (array[i + a][j + a] != x[a][a]) return 0
        if (array[i + a][j + b] != x[a][b]) return 0
        if (array[i + b][j + a] != x[b][a]) return 0
        if (array[i + b][j + b] != x[b][b]) return 0
    }
    return 1
}