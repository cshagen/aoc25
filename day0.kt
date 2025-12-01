val FILENAME = "data/d0-input.txt"
val whitespace = Regex("\\s+")

fun main() {
	println ("Part 1: ${part1(FILENAME)}")	
		}

fun part1(inputFile: String): Int {
		val file = java.io.File(inputFile)
		var list1 = mutableListOf<Int>()
		var list2 = mutableListOf<Int>()

		file.readLines().forEach { line ->
			val parts = line.split(whitespace)
			list1.add(parts[0].toInt())
			list2.add(parts[1].toInt())
		}
		
		return list1.sorted().zip(list2.sorted()).sumOf { Math.abs(it.first - it.second) }
}