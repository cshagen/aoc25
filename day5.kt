val FILENAME = "data/d5-input.txt"

fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Int {
	var result = 0
	var readUnits = false
	var units = mutableListOf<Long>()
	var ranges = mutableListOf<List<Long>>()
	lines.forEach { line ->
		if (line == "") {
			readUnits = true
		} else {
			if (readUnits) {
				units.add(line.toLong())
			} else {
				ranges.add(line.split('-').map { it.toLong() } )
			}
		}
	}
	for (u in units) {
		for (r in ranges) {
			if (u in r[0]..r[1]) {
				result += 1
				break
			}
		}
	}
		return result
}

fun part2(lines: List<String>): Long {
	var result = 0L
	var ranges = mutableListOf<Pair<Long,Long>>()
	var targetRanges = mutableListOf<Pair<Long,Long>>()
	
	for (line in lines) {
		if (line == "") {
			break 
		} else {
			val parts = line.split('-')
			ranges.add(Pair(parts[0].toLong(), parts[1].toLong()))
		}
	}
	ranges.sortBy { it.first }
	for (r in ranges) {
		var newR = r.copy()
		for (previousR in targetRanges) {
			if (newR.first <= previousR.second) {
				if (newR.second <= previousR.second) {
					newR = Pair(1,0)
				} else {
					newR = Pair(previousR.second + 1, newR.second)
				}
			}
		}
		if (newR.first <= newR.second) {
			targetRanges.add(newR)
			result += (newR.second - newR.first + 1)
			}
	}
	return result
}


