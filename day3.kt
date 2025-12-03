val FILENAME = "data/d3-input.txt"

fun main() {
	println ("Part 1: ${part1(FILENAME)}")	
	println ("Part 2: ${part2(FILENAME)}")	
	}

fun part1(inputFile: String): Int {
		val file = java.io.File(inputFile)
		var result = 0

		file.readLines().forEach { line ->
				val a = line.toCharArray().map { c -> c.digitToInt()}
				val (z1,p)=findMax(a.slice(0..a.size-2))
				val (z0,p0)=findMax(a.slice(p+1..a.size-1))
				result += z1*10+z0
		}
		return result
}

fun part2(inputFile: String): Long {
		val file = java.io.File(inputFile)
		var result = 0L

		file.readLines().forEach { line ->
				val a = line.toCharArray().map { c -> c.digitToInt()}
				var sum = 0L
				var start = 0
				for (tail in 12 downTo 1) {
					val (z,p)=findMax(a.slice(start..a.size-tail))
					sum = sum * 10 + z
					start = start+p+1
				}
				result += sum
		}
		return result
}

fun findMax(input: List<Int>) : Pair<Int,Int> {
	var max = 0
	var pos = 0

	input.forEachIndexed { i, c -> 
		if (c > max) {
			max = c
			pos = i
		}
		if (c == 9) return Pair(c,i)
	}
		return Pair(max,pos)
} 
