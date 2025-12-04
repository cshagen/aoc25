val FILENAME = "data/d4-input.txt"

fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Int {
		var result = 0
		var matrix = mutableListOf<CharArray>()
		
		lines.forEach { line -> matrix.add(line.toCharArray())}
		matrix.forEachIndexed { y, row ->
			row.forEachIndexed { x, c ->
				if (c == '@' && accessible(matrix, y, x) ) result += 1
			}
		}
		return result
}

fun part2(lines: List<String>): Int {
	var result = 0
	var matrix = mutableListOf<CharArray>()
		
	lines.forEach { line -> matrix.add(line.toCharArray()) }
	while (removeFirstAccessible (matrix)) {
		result += 1
	}
	return result
}

fun accessible (matrix: MutableList<CharArray>, row: Int, col: Int) : Boolean {
	var count = 0
	for (y in row-1 .. row+1) {
		for (x in col-1 .. col+1) {
			if (y in 0 .. matrix.size-1 && x in 0 .. matrix[0].size-1 && (y != row || x != col) && matrix[y][x] == '@') {
				count ++
			}
		}
	}
	return (count < 4)
}

fun removeFirstAccessible (matrix: MutableList<CharArray>) : Boolean {
	matrix.forEachIndexed { y, row ->
		row.forEachIndexed { x, c ->
			if (c == '@' && accessible(matrix, y, x)) {
				matrix[y][x] = '.'
				return true
			}
		}
	}
	return false
}	
