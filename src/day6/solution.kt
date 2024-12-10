package day6

import readFileLines

fun main() {
    val lines = readFileLines("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day6/input.txt")
    part2(lines)
}

val directions = listOf(
    P(-1, 0),
    P(0, 1),
    P(1, 0),
    P(0, -1)
)

fun direction(index: Int): P {
    return directions[index]
}

fun nextDirection(index: Int): Int {
    return (index + 1) % 4
}

fun prevDirection(index: Int): Int {
    return (4 + index - 1) % 4
}

fun isNext(cur: Int, next: Int): Boolean {
    return cur == 3 && next == 0 || next - cur == 1
}

class P(val x: Int, val y: Int) {
    operator fun plus(other: P): P {
        return P(x + other.x, y + other.y)
    }
}

fun part1(lines: List<String>) {
    val map = lines.map { it.toCharArray() }
    val path = List(map.size) { CharArray(map[0].size) { '.' } }

    val startPosition = findStartPosition(map)
    var d = 0
    var position = startPosition
    var counter = 1

    while (position.x in map.indices && position.y in map[0].indices) {
        path[position.x][position.y] = 'x'
        val nextPosition = position + direction(d)
        if (nextPosition.x !in map.indices || nextPosition.y !in map[0].indices) break
        if (map[nextPosition.x][nextPosition.y] == '#') {
            d = nextDirection(d)
            continue
        }
        position = nextPosition
        counter++
    }

    var c = 0
    for (i in map.indices) {
        for (j in map[0].indices) {
            if (path[i][j] == 'x') c++
        }
    }
    println(c)
}

fun part2(lines: List<String>) {
    val map = lines.map { it.toCharArray() }
    val path = List(map.size) { IntArray(map[0].size) { -1 } }

    val startPosition = findStartPosition(map)
    var d = 0
    var position = startPosition

    var cycles = 0

    while (position.x in map.indices && position.y in map[0].indices) {
        if (canMeetVisitedPath(map, path, position, nextDirection(d))) cycles++

//        if (path[position.x][position.y] != -1) {
//            if (isNext(d, path[position.x][position.y])) cycles++
//        }

        path[position.x][position.y] = d
        val nextPosition = position + direction(d)
        if (nextPosition.x !in map.indices || nextPosition.y !in map[0].indices) break
        if (map[nextPosition.x][nextPosition.y] == '#') {
            d = nextDirection(d)
            position += direction(d)
            continue
        }

        position = nextPosition
    }

    var c = 0
    for (i in map.indices) {
        for (j in map[0].indices) {
            if (path[i][j] != -1) c++
        }
    }
    println(c)
    println(cycles)
}

fun canMeetVisitedPath(m: List<CharArray>, path: List<IntArray>, p: P, d: Int): Boolean {
    var position = p + direction(d)
    while (position.x in path.indices && position.y in path[0].indices) {
        if (m[position.x][position.y] == '#') return false
        if (path[position.x][position.y] == d) return true
//        if (path[position.x][position.y] != -1) return false
        position += direction(d)
    }
    return false
}


fun findStartPosition(map: List<CharArray>): P {
    for (i in map.indices) {
        for (j in map[0].indices) {
            if (map[i][j] == '^') return P(i, j)
        }
    }

    return P(0, 0)
}