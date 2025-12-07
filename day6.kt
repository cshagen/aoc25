val FILENAME = "data/d6-input.txt"

fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Long {
	var result = 0L
	val whitespace = Regex("\\s+")
	var rows = mutableListOf<List<String>>() 

	for (line in lines) {
		val columns = line.trim().split(whitespace)
		rows.add(columns)
	}
	val rowCount = rows.size
	val colCount = rows[0].size

	for (col in 0 until colCount ) {
		var op = rows[rowCount-1][col]
		var colresult = if (op == "+") 0L else 1L
		for (row in 0 until rowCount -1) {
			if (op == "+") colresult += rows[row][col].toLong()
			else colresult *= rows[row][col].toLong()
		}
		result += colresult
	}
	return result
}

fun part2(lines: List<String>): Long {
	var result = 0L
	var matrix = mutableListOf<CharArray>()
		
	lines.forEach { line -> matrix.add(line.toCharArray())}
	val rowCount = matrix.size
	val colCount = matrix[0].size
	var op = ' '
	var operands = mutableListOf<Int>()
	for (col in 0 until colCount) {
		if (matrix[rowCount-1][col] != ' ') op = matrix[rowCount-1][col]
		var n = getNumber(matrix, col)
		if (n != 0) operands.add(n)
		else {
			result += calculate(operands, op)
			operands.clear()
		}
	}
	result += calculate(operands, op)
	return result
}

fun emptyColumn(matrix: MutableList<CharArray>, col: Int) : Boolean {
	for (row in 0 until matrix.size) {
		if (matrix[row][col] != ' ') return false
	}
	return true
}

fun getNumber(matrix: MutableList<CharArray>, col: Int) : Int {
	var num = 0
	for (i in 0 until matrix.size-1) {
		if (matrix[i][col] in '0'..'9') num = num*10 + (matrix[i][col].digitToInt())
	} 
	return num
}

fun calculate(operands: List<Int>, op: Char) : Long {
	var result = if (op == '+') 0L else 1L
	for (m in operands) {
		if (op == '+') result += m
		else result *= m
	}
	return result
}