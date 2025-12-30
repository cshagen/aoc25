val FILENAME = "data/d12-input.txt"
val re_newObject= Regex("^(\\d)+:$")
val re_shape=Regex("^([#.]+)$")
val re_region=Regex("^(\\d+)x(\\d+): (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+)$")
val presents = mutableMapOf<Pair<Int,Int>,Present>()
val overlapCache = hashSetOf<Triple<Position,Position, Boolean>>()
var overlapCounter = 0
var trialCounter = 0

fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Int {
	var present = Present()
	var regions = mutableListOf<Region>()
	// Read file
	for (line in lines) {
		if (line == "") {
			presents[Pair(present.id,0)]=present
			var p = present
			for (i in 1..3) {
				p = p.rotateRight()
				presents[Pair(present.id,i)] = p
			}
			present = Present()
		} else {
			val match = re_newObject.find(line)
			if (match != null) {
				present.id = match.groups[1]?.value!!.toInt()
			} else {
				val match = re_shape.find(line)
				if (match != null) {
					present.addLine(match.groups[1]?.value!!)
				} else {
					val match = re_region.find(line)
					if (match != null) {
						var region = Region() 
						region.width = match.groups[1]?.value!!.toInt() 
						region.height = match.groups[2]?.value!!.toInt() 
						for (c in 3..8) {
							region.counts.add(match.groups[c]?.value!!.toInt() )
						}
						regions.add(region)
					}
				}
			}
		}
	}
	// Solve
	var result = 0
	regions.forEach { a ->
		//a.print()
		if (a.preCheck()) result += 1
	}
	return result
	}

fun part2(lines: List<String>): Int {
	var result = 0
	return result
}

class Present {
	var id = 0
	var width = 0
	var height = 0
	var lines = mutableListOf<String>()
	
	fun addLine (l: String) {
		lines.add(l)
		width = l.length
		height = lines.size
	}

	fun print() {
		println ("Present $id - width: $width, height: $height, lines: $lines")
	}

	fun rotateRight() : Present {
		var p = Present()
		p.id = id
		p.width = height
		p.height = width
		for (row  in 0 until p.height) {
			p.lines.add("")
		}
		for (col in 0 until height) {
			for (row in width -1 downTo 0 ) {
				p.lines[col] += lines[row][col] 
			}
		}
		return p
	}

	fun summarize() : Int {
		var result = 0
		for (row in lines) {
			for (cell in row) {
				if (cell == '#') {
					result += 1
				}
			}
		}
		return result
	}
}

class Region {
	var width =0
	var height = 0
	var counts = mutableListOf<Int>()
	fun print() {
		println ("Region - width: $width, height: $height, counts: $counts")
	}
	fun preCheck () : Boolean {
		var sum = 0
		for (id in 0 until counts.size) {
			val p = presents[Pair(id,0)]!!
			sum += counts[id]!! * p.summarize()
		}
		return (sum <= width*height)
	}
}
data class Position (val id: Int, val direction: Int, val x: Int, val y: Int)
