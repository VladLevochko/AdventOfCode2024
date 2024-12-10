package day1

import readFileLines
import java.util.ArrayList

fun main() {
    val lines = readFileLines("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day1/input.txt")
    val l1 = ArrayList<Int>()
    val l2 = ArrayList<Int>()

    for (l in lines) {
        val split = l.split("   ")
        l1.add(split[0].toInt())
        l2.add(split[1].toInt())
    }

    l1.sort()
    l2.sort()

    val m = HashMap<Int, Int>()

    for (v in l2) {
        m[v] = m.getOrDefault(v, 0) + 1
    }

    println(m)

    var result = 0
    for (i in l1.indices) {
        result += l1[i] * m.getOrDefault(l1[i], 0)
    }

    println(result)
}