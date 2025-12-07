val FILENAME = "data/d7-input.txt"

fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Int {
	var result = 0
	var matrix  = lines.map { line -> line.toCharArray()}
	var beams = mutableSetOf<Int>(matrix[0].indexOf('S'))
	for (row in matrix.slice(1 until matrix.size)) {
		var newBeams = mutableSetOf<Int>()
		for (beam in beams) {
			if (row[beam] == '^') {
				result += 1
				if (beam > 0) newBeams.add(beam-1)
				if (beam < matrix[0].size - 1) newBeams.add(beam+1)
			} else newBeams.add(beam)
		}
		beams = newBeams
		}
		return result
}

fun part2(lines: List<String>): Long {
	var result = 1L
	var matrix  = lines.map { line -> line.toCharArray()}
	val width = matrix[0].size
	var beams = mutableMapOf<Int,Long>()
	for (col in 0 until matrix[0].size) {
		beams[col] = if (matrix[0][col] == 'S') 1L else 0L
	}
	for (row in matrix.slice(1 until matrix.size)) {
		var newBeams = beams.toMutableMap()
		for ((beam, count) in beams) {
			if (row[beam] == '^') {
				result += count 
				if (beam > 0) newBeams[beam-1] = (newBeams[beam-1]?:0 ) + count  
				if (beam < width - 1) {
					newBeams[beam+1] = (newBeams[beam+1]?:0 ) + count  
				}
				newBeams[beam] = 0
			} 
		}
		beams = newBeams
	}
	return result
}


