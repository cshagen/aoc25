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
			var steps = turn.substring(1).toInt()
			
			if (direction == 'L') steps = -steps
			position = ((position + steps) % 100 + 100) % 100
			if (position == 0) zeros += 1
		}
		return zeros
}

fun part2(inputFile: String): Int {
		val file = java.io.File(inputFile)
		var position = 50
		var zeros = 0
		
		file.readLines().forEach { turn ->
			val direction = turn[0]
			var steps = turn.substring(1).toInt()
			val oldposition = position
			
			if (direction == 'L') steps = -steps
			position += steps
			if (position == 0) zeros += 1
			else if (position >= 100) zeros += position / 100
			else if (position < 0) {
				zeros -= (position / 100)  
				if (oldposition != 0) zeros += 1
			}
			position = ((position % 100) + 100) % 100
		}
		return zeros
}