val FILENAME = "data/d4-input.txt"

fun main() {
	println ("Part 1: ${part1(FILENAME)}")	
	println ("Part 2: ${part2(FILENAME)}")	
	}

fun part1(inputFile: String): Int {
		val file = java.io.File(inputFile)
		var result = 0
		var matrix = mutableListOf<CharArray>()
		file.readLines().forEach { line ->
				matrix.add(line.toCharArray())
		}
		for (i in 0 ..  matrix.size-1) {
			for (j in 0 .. matrix[i].size-1) {
				if (matrix[i][j] == '@' && checkRoll(matrix, i, j)) {
					result += 1
				}
			}
		}
		return result
}

fun part2(inputFile: String): Int {
		val file = java.io.File(inputFile)
		var result = 0
		var matrix = mutableListOf<CharArray>()
		
		file.readLines().forEach { line ->
				matrix.add(line.toCharArray())
		}
		
		while (true) {
			val (row,col) = findFirstRemovableRoll(matrix)
			if (row == -1 && col == -1) {
				return result
			} else {
				matrix[row][col] = '.'
				result += 1
			}
		}
		return result
}

fun checkRoll (matrix: MutableList<CharArray>, row: Int, col: Int) : Boolean {
	var count = 0
	for (i in row-1 .. row+1) {
		for (j in col-1 .. col+1) {
			if (i >= 0  && j >= 0 && i < matrix.size && j < matrix[0].size && (i != row || j != col) && matrix[i][j] == '@') count += 1
		}
	}
	return (count < 4)
}

fun findFirstRemovableRoll (matrix: MutableList<CharArray>) : Pair<Int,Int> {
	for (i in 0 ..  matrix.size-1) {
		for (j in 0 .. matrix[0].size-1) {
			if (matrix[i][j] == '@' && checkRoll(matrix, i, j)) {
				return Pair(i,j)
			}
		}
	}
	return Pair(-1,-1)
}	
