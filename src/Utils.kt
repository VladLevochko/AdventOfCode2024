import java.io.File
import kotlin.streams.toList

fun readFileLines(filePath: String): List<String> {
    return File(filePath).bufferedReader().readLines()
}

fun read2DCharArray(filePath: String): List<CharArray> {
    return File(filePath).bufferedReader().lines().map { it.toCharArray() }.toList()
}

fun intNumbersFromLine(line: String): List<Int> {
    return line.split(" ").map { it.toInt() }
}
