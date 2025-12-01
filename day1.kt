val FILENAME = "data/d1-input.txt"
val whitespace = Regex("\\s+")

fun main() {
	println ("Part 1: ${part1(FILENAME)}")	
	println ("Part 2: ${part2(FILENAME)}")	
	}

fun part1(inputFile: String): Int {
		val file = java.io.File(inputFile)
		var position = 50
		var zeros = 0
		
		file.readLines().forEach { turn ->
			val direction = turn[0]
			val steps = turn.substring(1).toInt()
			
			when (direction) {
				'L' -> {
					position = ((position - steps) % 100 + 100) % 100
				}
				'R' -> {
					position = (position + steps) % 100
				}
			}
			if (position == 0) {
				zeros += 1
			}
		}
		return zeros
}

fun part2(inputFile: String): Int {
		val file = java.io.File(inputFile)
		var position = 50
		var zeros = 0
		
		file.readLines().forEach { turn ->
			val direction = turn[0]
			val steps = turn.substring(1).toInt()
			
			when (direction) {
				'L' -> {
					if(position <= steps) {
						zeros  -= (position - steps) / 100 
						if (position != 0) {
							zeros += 1
						}
					}
					position = ((position - steps) % 100 + 100) % 100
				}
				'R' -> {
					zeros += (position + steps) / 100
					position = (position + steps) % 100
				}
			}
		}
		return zeros
}