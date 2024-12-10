package day3

import readFileLines

fun main() {
    val lines = readFileLines("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day3/input.txt")

    parseLine2(lines)
}

fun part1(lines: List<String>) {
    var result: Long = 0
//    for (line in lines) {
//        result += parseLine2(line)
//    }

    println(result)
}

fun parseLine2(lines: List<String>) {
    var result: Long = 0
    var apply = true

    for (line in lines) {
        val r = Regex("(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))")
        val results = r.findAll(line)
        for (match in results) {
            when (match.value) {
                "do()" -> apply = true
                "don't()" -> apply = false
                else -> if (apply) result += mul(match.value)
            }
        }
    }

    println(result)
}

fun mul(match: String): Int {
    val r = Regex("\\d+")
    val m = r.findAll(match)

    return m.first().value.toInt() * m.last().value.toInt()
}

fun parseLine(line: String): Long {
    var result: Long = 0
    var i = 0
    val chars = line.toCharArray()

    fun parseDoDont(): Boolean? {
        if (chars.getOrNull(i++) != 'd') return null
        if (chars.getOrNull(i++) != 'o') return null
        val c = chars.getOrNull(i++)
        if (c == '(') return true
        if (c != 'n') return null
        if (chars.getOrNull(i++) != '\'') return null
        if (chars.getOrNull(i++) != 't') return null
        if (chars.getOrNull(i++) != '(') return null

        return false
    }

    fun parseCommand(): Boolean {
        if (chars.getOrNull(i++) != 'm') return false
        if (chars.getOrNull(i++) != 'u') return false
        if (chars.getOrNull(i++) != 'l') return false
        if (chars.getOrNull(i++) != '(') return false
        return true
    }

    fun parseArgument(): Int? {
        if (!chars[i].isDigit()) {
            i++
            return null
        }
        var number = 0
        while (chars.getOrNull(i)?.isDigit() == true) {
            number = number * 10 + chars[i++].digitToInt()
        }
        return number
    }

    fun parseSeparator(): Boolean {
        return chars.getOrNull(i++) == ','
    }

    fun parseBrace(): Boolean {
        return chars.getOrNull(i++) == ')'
    }

    val modifiers = ArrayList<Pair<Int, Boolean>>()
    while (i < line.length) {
        val position = i
        val modifier = parseDoDont() ?: continue
        modifiers.add(Pair(position, modifier))
    }

    i = 0

    var apply = true
    var modifierIndex = 0
    while(i < line.length) {
        if (i >= (modifiers.getOrNull(modifierIndex)?.first ?: Int.MAX_VALUE)) {
            apply = modifiers[modifierIndex++].second
        }

        if (!parseCommand()) continue
        val a = parseArgument() ?: continue
        if (!parseSeparator()) continue
        val b = parseArgument() ?: continue
        if (!parseBrace()) continue

        if (!apply) continue
        result += a * b
    }

    return result
}
