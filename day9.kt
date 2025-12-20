val FILENAME = "data/d9-test.txt"

fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Long {
	var redtiles = mutableListOf<Pair<Long, Long>>()
	var squares = mutableListOf<Long>()
	lines.forEach { line ->
		redtiles.add(line.split(",").map { it.toLong() }.let { (x, y) -> Pair(x, y) })
	}
	for (t1 in redtiles) {
		for (t2 in redtiles) {
			squares.add((Math.abs(t2.first - t1.first)+1) * (Math.abs(t2.second - t1.second)+1)) 
			}
		}
	squares.sortByDescending { it }
	return squares[0]
}

fun part2(lines: List<String>): Long {
	var redtiles = mutableListOf<Pair<Long, Long>>()
	var squares = mutableListOf<Long>()
	lines.forEach { line ->
		redtiles.add(line.split(",").map { it.toLong() }.let { (x, y) -> Pair(x, y) })
	}
	for (t1 in redtiles) {
		for (t2 in redtiles) {
			if (isValid(t1, t2, redtiles)) {
				squares.add((Math.abs(t2.first - t1.first)+1) * (Math.abs(t2.second - t1.second)+1)) 
			}
		}
	}
	squares.sortByDescending { it }
	return squares[0]
	
}

fun isValid (t1: Pair<Long, Long>, t2: Pair<Long, Long>, redTiles: List<Pair<Long, Long>>): Boolean {
			val t3 = Pair(t1.first, t2.second)
			val t4 = Pair(t2.first, t1.second)
		
	
	return isInside(t3, redTiles) && isInside(t4, redTiles) 
}

fun isInside (t: Pair<Long, Long>, redTiles: List<Pair<Long, Long>>): Boolean {
	var left = false
	var right = false
	for (rt in redTiles) {
		if (rt.first <= t.first && rt.second <= t.second) {
			left = true
		}
		if (rt.first >= t.first && rt.second >= t.second) {
			right = true
		}
	}
	return left && right
}