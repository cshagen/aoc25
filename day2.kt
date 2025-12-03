val FILENAME = "data/d2-input.txt"

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
				if (id_s.length %2 == 0) {
					val s1 = id_s.substring(0, id_s.length/2)
					val s2 = id_s.substring(id_s.length/2, id_s.length)
					if (s1 == s2) {
						sum += id
					}
				}
			}
		}
		return sum
}

fun part2(inputFile: String): Long {
		val file = java.io.File(inputFile)
		var sum = 0L
		val re = Regex("^(\\d+)\\1+$")
		file.readLines().first().split(',').forEach {rng -> 
			val x = rng.split('-').map { it.toLong() }
			for (id in x[0]..x[1]) {
				val id_s = id.toString()
				if (re.containsMatchIn(id_s)) {
					//println("Found match: $id_s")
					sum += id
				}

				
			}
		}
		return sum
		
}