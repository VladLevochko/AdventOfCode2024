package day7

import readFileLines

fun main() {
    val lines = readFileLines("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day7/input.txt")
    part1(lines)
}

fun part1(lines: List<String>) {
    var result = 0L
    for (line in lines) {
        println(line)
        val r = evaluate(line)
        result += r
//        if (r != 0L) println(result)
//        result += r
    }

    println(result)
}

fun evaluate(line: String): Long {
    val split = line.split(":")
    val value = split.first().toLong()
    val arguments = split[1].substring(1).split(' ').map { it.toLong() }.toList()

    val operations = CharArray(arguments.size - 1) { '+' }

    return if (r2(value, arguments, operations, 0)) value else 0L
}

fun r(value: Long, arguments: List<Long>, operations: CharArray, startIndex: Int): Boolean {
    val v = applyOperations(arguments, operations)
    if (v == value) return true
//    if (v > value) return false

    for (i in startIndex..<operations.size) {
        if (operations[i] == '*') continue
        operations[i] = '*'
        val rr = r(value, arguments, operations, i + 1)
        operations[i] = '+'
        if (rr) return true
    }

    return false
}

fun r2(value: Long, arguments: List<Long>, operations: CharArray, startIndex: Int): Boolean {
    val v = applyOperations(arguments, operations)
    if (v == value) return true

    for (i in startIndex..<operations.size) {
        if (operations[i] != '*') {
            val o = operations[i]
            operations[i] = '*'
            var rr = r2(value, arguments, operations, i + 1)
            operations[i] = o
            if (rr) return true
        }

        if (operations[i] != '|') {
            val o = operations[i]
            operations[i] = '|'
            val rr = r2(value, arguments, operations, i + 1)
            operations[i] = o
            if (rr) return true
        }
    }

    return false
}

fun applyOperations(arguments: List<Long>, operations: CharArray): Long {
//    println(operations)
    var result = arguments.first().toLong()
    for (i in operations.indices) {
        if (operations[i] == '*') {
            result *= arguments[i + 1]
        } else if (operations[i] == '+') {
            result += arguments[i + 1]
        } else {
            result = (result.toString() + arguments[i + 1].toString()).toLong()
        }
    }
    return result
}

fun applyOperations2(arguments: List<Int>, operations: CharArray): Long {
    val sumArguments = mutableListOf<Long>()
    var i = 0
    var m = 1L
    while (i < operations.size) {
        if (operations[i] == '+') {
            sumArguments.add(arguments[i++].toLong())
        } else {
            while (i < operations.size && operations[i] == '*') {
                m *= arguments[i++]
            }
            m *= arguments[i++]
            sumArguments.add(m)
            m = 1L
        }
    }
    if (i == arguments.size - 1) {
        sumArguments.add(arguments[i].toLong())
    }

    return sumArguments.reduce(Long::plus)
}
