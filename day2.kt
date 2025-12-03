val FILENAME = "data/d2-input.txt"
val re1 = Regex("^(\\d+)\\1$")
val re2 = Regex("^(\\d+)\\1+$")

fun main() {
	println ("Part 1: ${part1(FILENAME)}")	
	println ("Part 2: ${part2(FILENAME)}")	
	}

fun part1(inputFile: String): Long {
		val file = java.io.File(inputFile)
		var sum = 0L

		file.readLines().first().split(',').forEach {rng -> 
			val x = rng.split('-').map { it.toLong() }
			for (id in x[0]..x[1]) {
				val id_s = id.toString()
				if (re1.containsMatchIn(id_s)) {
					sum += id
				}
			}
		}
		return sum
}

fun part2(inputFile: String): Long {
		val file = java.io.File(inputFile)
		var sum = 0L
		// val re = Regex("^(\\d+)\\1+$")
		file.readLines().first().split(',').forEach {rng -> 
			val x = rng.split('-').map { it.toLong() }
			for (id in x[0]..x[1]) {
				val id_s = id.toString()
				if (re2.containsMatchIn(id_s)) {
					sum += id
				}
			}
		}
		return sum
}