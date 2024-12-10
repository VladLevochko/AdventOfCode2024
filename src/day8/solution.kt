package day8

import readFileLines
import kotlin.math.absoluteValue

fun main() {
    val lines = readFileLines("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day8/input.txt")

    val m = lines.map { it.toCharArray() }
    val a = Array(m.size) { CharArray(m[0].size) }

    for (i in m.indices) {
        for (j in m[0].indices) {
            if (m[i][j] == '.') continue
            findAntinodes(m, i, j, a)
        }
    }

    println(calculateAntinodes(a, m))
}

fun findAntinodes(m: List<CharArray>, row: Int, col: Int, a: Array<CharArray>) {
    val c = m[row][col]
    for (i in row..<m.size) {
        val jRange = if (i == row) col+1..<m[0].size else m[0].indices
        for (j in jRange) {
            if (m[i][j] == c) {
//                markAntinodes(m, row, col, i, j, a)
                markAntinodes2(m, row, col, i, j, a)
            }
        }
    }
}

fun markAntinodes(m: List<CharArray>, i1: Int, j1: Int, i2: Int, j2: Int, a: Array<CharArray>) {
    val rd = i2 - i1
    val cd = j2 - j1

    val lowR = i1 - rd
    val lowC = j1 - cd
    if (lowR in m.indices && lowC in m[0].indices) {
        a[lowR][lowC] = '#'
    }
    val highR = i2 + rd
    val highC = j2 + cd
    if (highR in m.indices && highC in m[0].indices) {
        a[highR][highC] = '#'
    }
}

fun markAntinodes2(m: List<CharArray>, i1: Int, j1: Int, i2: Int, j2: Int, a: Array<CharArray>) {
    val rd = i2 - i1
    val cd = j2 - j1

    var lowR = i1
    var lowC = j1
    while (lowR in m.indices && lowC in m[0].indices) {
        a[lowR][lowC] = '#'
        lowR -= rd
        lowC -= cd
    }
    var highR = i2
    var highC = j2
    while (highR in m.indices && highC in m[0].indices) {
        a[highR][highC] = '#'
        highR += rd
        highC += cd
    }
}

fun calculateAntinodes(a: Array<CharArray>, m: List<CharArray>): Int {
    var sum = 0
    for (i in a.indices) {
        for (j in a[0].indices) {
//            if (m[i][j] != '.') {
//                print(m[i][j])
//            } else if (a[i][j] == '#') {
//                print(a[i][j])
//            }
            sum += if (a[i][j] == '#') 1 else 0
        }
//        println()
    }
    return sum
}
