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
	// var presents = mutableMapOf<Int,Present>()
	var present = Present()
	var regions = mutableListOf<Region>()
	
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
	//presents.forEach {entry ->
		//println("[${entry.key}] ${entry.value.lines}")
	//}
	var result = 0
	for (i in 0 until 3) {
		println ("Checking region $i")
		if ( regions[i].search()) {
			println ("fit")
			result += 1
			println("tries: $trialCounter")
			println("comparisons: $overlapCounter")
		} else {
			println("no fit")
			println("tries: $trialCounter")
			println("comparisons: $overlapCounter")
		}
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

	fun copy() : Present {
		var p = Present()
		p.id = id
		p.width = width
		p.height = height
		for (row in lines) {
			p.lines.add(row)
		}
		return p
	}
}

class Region {
	var width =0
	var height = 0
	var counts = mutableListOf<Int>()
	fun print() {
		println ("Region - width: $width, height: $height, counts: $counts")
	}

	fun search() : Boolean {
		var done = hashSetOf<Position>()
		var tried = hashSetOf<Position>()
		var todo = mutableListOf<Int>()
		
		for ( id in 0 until counts.size) {
			for (amount in 0 until counts[id]) {
				todo.add(id)
			}
		}
		println ("Presents: ${todo.size}")
		return searchRecursive(done, todo, tried)
	}

	fun searchRecursive (done: Set<Position>, todo: List<Int>, tried: Set<Position>) : Boolean {
		//println(tried.size)
		//println(done)
		if (todo.size == 0) {
			println("Found solution:")
			for (pos in done) {
				println("ID: ${pos.id}, Dir: ${pos.direction}, X: ${pos.x}, y: ${pos.y}")
			}
			return true
		}
		if (done.size == 0) println ("")
		val id = todo.first()
		var noGo = tried.toHashSet()
		var startX = 0
		var startY = 0
		var startDir = 0

		
		if (done.size > 0) {
			val last = done.last()
			val lastP = presents[Pair(last.id, last.direction)]!!
			if (last.id == id) {
				if (last.y < height - lastP.height -1) {
					startY = last.y +1
					startX = last.x
					startDir = last.direction
				} else if (last.x < width -lastP.width -1) {
					startX = last.x +1
					startDir = last.direction
				} else if (last.direction < 3) {
					startDir = last.direction +1
				} else {
					startDir = last.direction
				}
			}
		} 
		
		for (direction in startDir..3) { 													// all directions
		if (done.size == 0) println ("[$id][$direction]")
			val p = presents[Pair(id,direction)]!!
			for (newX in startX..width - p.width) { 				// all x positions
				for (newY in startX..height - p.height) { 		// all y positions
				if (done.size == 0) print('.')
					val pos = Position(id, direction, newX,newY)
					if (!noGo.contains(pos)) {
						trialCounter++
						var overlap = false
						for (pos1 in done) {
							if (overlaps (pos, pos1)) {
								overlap = true
								noGo.add(pos)
								break
							} 
						}
						if (!overlap) {
							noGo.add(pos)
							if (searchRecursive (done + pos, todo.slice(1..todo.size -1), noGo)) {
								return true
							}	else {
								noGo.add(pos)
								//println(noGo.size)
							}
						} 
						
					}
				}
			}
			//println(noGo.size)
		}
		return false
	}
}

data class Position (val id: Int, val direction: Int, val x: Int, val y: Int)

fun overlaps (pos1: Position, pos2: Position) : Boolean {

	if (overlapCache.contains(Triple(pos1,pos2,true))) {
		//println("cache hit")
		return true
	} else if (overlapCache.contains(Triple(pos1,pos2,false))) {
		//println("cache hit")
		return false
	}
	overlapCounter++
					
	var p1 = presents[Pair(pos1.id,pos1.direction)]!!
	var p2 = presents[Pair(pos2.id,pos2.direction)]!!
	
	val yrange1 = pos1.y until pos1.y +p1.height
	val yrange2 = pos2.y until pos2.y +p2.height
	val yrange = Math.max(yrange1.first,yrange2.first) .. Math.min(yrange1.last,yrange2.last)
	val xrange1 = pos1.x until pos1.x +p1.width
	val xrange2 = pos2.x until pos2.x +p2.width
	val xrange = Math.max(xrange1.first,xrange2.first) .. Math.min(xrange1.last,xrange2.last)
	
		for (row in yrange) {
			for (col in xrange) {
				if (p1.lines[row-pos1.y][col-pos1.x] == '#'
					&&  p2.lines[row-pos2.y][col-pos2.x] == '#') {
						overlapCache.add(Triple(pos1,pos2,true))
						return true
					}
			}
		}

		overlapCache.add(Triple(pos1,pos2,false))
		return false
	}