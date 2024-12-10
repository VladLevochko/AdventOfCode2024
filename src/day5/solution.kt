package day5

import readFileLines

fun main() {
    val lines = readFileLines("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day5/input.txt")
    part2(lines)
}

fun part1(lines: List<String>) {
    val g = HashMap<Int, HashSet<Int>>()

    var i = 0
    while (i < lines.size && lines[i].isNotEmpty()) {
        val split = lines[i++].split('|')
        val a = split[0].toInt()
        val b = split[1].toInt()
        val set = g.getOrDefault(a, HashSet())
        set.add(b)
        g[a] = set
    }

    i++
    var result = 0
    while (i < lines.size) {
        val numbers = lines[i++].split(',').map { it.toInt() }.toList()
        if (!isOrderRight(numbers, g)) continue
        result += numbers[numbers.size / 2]
    }

    println(result)
}

fun isOrderRight(numbers: List<Int>, g: Map<Int, HashSet<Int>>): Boolean {
    for (i in 0..<numbers.size - 1) {
        if (g[numbers[i]]?.contains(numbers[i + 1]) != true) return false
    }
    return true
}

fun part2(lines: List<String>) {
    val g = HashMap<Int, HashSet<Int>>()
    val roots = HashSet<Int>()

    var i = 0
    while (i < lines.size && lines[i].isNotEmpty()) {
        val split = lines[i++].split('|')
        val a = split[0].toInt()
        val b = split[1].toInt()
        if (!g.containsKey(a)) roots.add(a)
        roots.remove(b)

        val set = g.getOrDefault(a, HashSet())
        set.add(b)
        g[a] = set
    }

    i++
    var result = 0
    while (i < lines.size) {
        val numbers = lines[i++].split(',').map { it.toInt() }.toList()
        if (isOrderRight(numbers, g)) continue
        val ordered = orderNumbers(numbers.toIntArray(), roots, g)
        result += ordered[ordered.size / 2]
    }

    println(result)
}

fun orderNumbers(numbers: IntArray, roots: Set<Int>, g: HashMap<Int, HashSet<Int>>): IntArray {
    for (i in 1..<numbers.size) {
        for (j in 0..<i) {
            if (g.getOrDefault(numbers[i], emptySet()).contains(numbers[j])) {
                val t = numbers[i]
                numbers[i] = numbers[j]
                numbers[j] = t
            }
        }
    }

    return numbers
}