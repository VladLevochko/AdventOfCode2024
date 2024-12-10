package day2

import intNumbersFromLine
import readFileLines
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    val lines = readFileLines("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day2/input.txt")
    part1(lines)
    part2(lines)
}

fun part1(lines: List<String>) {
    var result = 0
    for (line in lines) {
        val numbers = intNumbersFromLine(line)
        if (isValid(numbers)) result++
    }

    println(result)
}

fun part2(lines: List<String>) {
    var result = 0
    for (line in lines) {
        val numbers = intNumbersFromLine(line)

        for (i in numbers.indices) {
            val numbersCopy = numbers.subList(0, i) + numbers.subList(i + 1, numbers.size)

            if (isValid(numbersCopy)) {
                result++
                break
            }
        }
    }

    println(result)
}

fun isValid(numbers: List<Int>): Boolean {
    val sign = (numbers[1] - numbers[0]).sign
    for (i in 1..<numbers.size) {
        val diff = numbers[i] - numbers[i - 1]
        if (diff.sign != sign || diff.absoluteValue > 3) {
            return false
        }
    }
    return true
}