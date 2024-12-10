package day9

import java.io.BufferedReader
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main() {
    val reader = File("/Users/VladyslavL/IdeaProjects/AdventOfCode2024/src/day9/input.txt").inputStream().bufferedReader()
    part2(reader)
    reader.close()
}

fun part1(reader: BufferedReader) {
    val a = ArrayList<Int>()
    var isBlock = true
    var id = 0
    while (true) {
        val c = reader.read()
        if (c == -1) break

        val len = c.toChar() - '0'
        if (isBlock) {
            for (i in 0..<len) {
                a.add(id)
            }
            id++
        } else {
            for (i in 0..<len) {
                a.add(-1)
            }
        }
        isBlock = !isBlock
    }

    var left = moveToEmpty(a, 0, a.size - 1)
    var right = moveToFilled(a, 0, a.size - 1)

    while (left < right && a[left] == -1 && a[right] != -1) {
        val t = a[left]
        a[left] = a[right]
        a[right] = t
        left = moveToEmpty(a, left, right)
        right = moveToFilled(a, left, right)
    }

    println(checksum(a))
}

fun part2(reader: BufferedReader) {
    val a = ArrayList<Int>()
    var isBlock = true
    var id = 0

    val emptySpaces = HashMap<Int, LinkedList<Int>>() // length to list of starting positions
    val blocks = LinkedList<Pair<Int, Int>>() // position, length
    while (true) {
        val c = reader.read()
        if (c == -1) break

        val len = c.toChar() - '0'
        if (isBlock) {
            blocks.addFirst(Pair(a.size, len))
            for (i in 0..<len) {
                a.add(id)
            }
            id++
        } else {
            val list = emptySpaces.getOrDefault(len, LinkedList())
            list.add(a.size)
            emptySpaces[len] = list
            for (i in 0..<len) {
                a.add(-1)
            }
        }
        isBlock = !isBlock
    }

    for (block in blocks) {
        val emptySpace = findSuitableEmptySpaceLength(block.second, emptySpaces) ?: continue
        val emptySpacePositions = emptySpaces[emptySpace.second]!!
        if (emptySpacePositions.first + block.second >= block.first) continue
        val emptySpacePosition = emptySpacePositions.removeFirst()
        moveBlock(a, emptySpacePosition, block.first, block.second)

        if (emptySpace.second > block.second) {
            val leftSpace = emptySpace.second - block.second
            val list = emptySpaces.getOrDefault(leftSpace, LinkedList())
            list.add(emptySpacePosition + block.second)
            list.sort()
        }
    }

    println(a)
    println(checksum(a))
}

fun findSuitableEmptySpaceLength(blockLength: Int, emptySpaces: Map<Int, List<Int>>): Pair<Int, Int>? {
    var leftmostEmptySpace: Pair<Int, Int>? = null

    for (len in blockLength..9) {
        if (!emptySpaces.containsKey(len) || emptySpaces[len]!!.isEmpty()) continue
        val spacePosition = emptySpaces[len]!!.first()
        if (spacePosition < (leftmostEmptySpace?.first ?: Integer.MAX_VALUE)) {
            leftmostEmptySpace = Pair(spacePosition, len)
        }
    }

    return leftmostEmptySpace
}

fun moveBlock(a: ArrayList<Int>, emptySpaceStart: Int, blockStart: Int, length: Int) {
    for (i in 0..<length) {
        a[emptySpaceStart + i] = a[blockStart + i]
        a[blockStart + i] = -1
    }
}

fun moveToEmpty(a: List<Int>, left: Int, right: Int): Int {
    var l = left
    while (l < right && a[l] != -1) l++
    return l
}

fun moveToFilled(a: List<Int>, left: Int, right: Int): Int {
    var r = right
    while (left < r && a[r] == -1) r--
    return r
}

fun checksum(a: List<Int>): Long {
    var result = 0L
    for (i in a.indices) {
        if (a[i] == -1) continue
        result += a[i] * i
    }
    return result
}