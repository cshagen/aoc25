val FILENAME = "data/d9-input.txt"
var minX = 0L
var maxX = 0L
var minY = 0L
var maxY = 0L

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

fun part2(rows: List<String>): Long {
	var redtiles = mutableListOf<Point>()
	var border = mutableListOf<Line>()
	var squareSizes = mutableListOf<Long>()
	rows.forEach { row ->
		redtiles.add(row.split(",").map { it.toLong() }.let { (x, y) -> Point(x, y) })
	}
	minX = redtiles.minOf ( { it.x })
	maxX = redtiles.maxOf ( { it.x })
	minY = redtiles.minOf ( { it.y })
	maxY = redtiles.maxOf ( { it.y })
	
	for ( i in 1 until redtiles.size) {
		border.add(Line(redtiles[i-1],redtiles[i]))
	}
	border.add(Line(redtiles.last(),redtiles.first()))
	var counter = 0
	for (i in 0 until redtiles.size) {
		counter++
		for (j in i+1 until  redtiles.size ) {
			if (checkSquare2(redtiles[i], redtiles[j], border)) {
				squareSizes.add((Math.abs(redtiles[j].x - redtiles[i].x)+1) * (Math.abs(redtiles[j].y - redtiles[i].y)+1)) 
			} 
		}
	}
	squareSizes.sortByDescending { it }
	
	return squareSizes[0]
}

fun checkSquare2 (t1: Point, t2: Point, border: List<Line>,): Boolean {
	val t3 = Point(t1.x, t2.y)
	val t4 = Point(t2.x, t1.y)
	if (isInside2(t3, border) && isInside2 (t4, border)) {
		return !borderCutsSquare (t1,t2,border)
	}  else {
		return false
	}
}

fun isInside2 (p: Point, border:List<Line>) : Boolean {
	var l = false
	var r = false
	var t = false
	var b = false
	if (p.x < minX || p.x > maxX || p.y < minY || p.y > maxY) return false
	for (line in border) {
		if (touchesLeft(line,p)) return true
		if (touchesRight(line,p)) return true
		if (touchesTop(line,p)) return true
		if (touchesBottom(line,p)) return true
		
		if (isLeftOf (line,p)) l = true
		if (isRightOf (line,p)) r = true
		if (isBelow (line,p)) t = true
		if (isAbove (line,p)) b = true
		
	}
	return l && r && t && b
}

fun isLeftOf (l: Line, p: Point) : Boolean{
	return (l.s.x == l.e.x 
					&& p.x > l.s.x 
					&& p.y >= Math.min(l.s.y,l.e.y) 
					&& p.y <= Math.max(l.s.y, l.e.y))
}
fun isRightOf (l: Line, p: Point) : Boolean{
	return (l.s.x == l.e.x 
					&& p.x < l.s.x 
					&& p.y >= Math.min(l.s.y, l.e.y) 
					&& p.y <= Math.max(l.s.y, l.e.y))
}
fun isBelow (l: Line, p: Point) : Boolean{
	return (l.s.y == l.e.y 
					&& p.y > l.s.y 
					&& p.x >= Math.min(l.s.x, l.e.x) 
					&& p.x <= Math.max(l.s.x, l.e.x)) 
}
fun isAbove (l: Line, p: Point) : Boolean{
	return (l.s.y == l.e.y 
					&& p.y < l.s.y 
					&& p.x >= Math.min(l.s.x, l.e.x) 
					&& p.x <= Math.max(l.s.x, l.e.x))
}

fun touchesLeft (l: Line, p: Point) : Boolean{
	return (p.x == Math.min(l.s.x, l.e.x) 
					&& p.y >= Math.min(l.s.y, l.e.y) 
					&& p.y <= Math.max(l.s.y, l.e.y))
}
fun touchesRight (l: Line, p: Point) : Boolean{
	return (p.x == Math.max(l.s.x, l.e.x) 
					&& p.y >= Math.min(l.s.y, l.e.y) 
					&& p.y <= Math.max(l.s.y, l.e.y))
}
fun touchesTop (l: Line, p: Point) : Boolean{
	return (p.y == Math.min(l.s.y, l.e.y) 
					&& p.x >= Math.min(l.s.x, l.e.x) 
					&& p.x <= Math.max(l.s.x, l.e.x)) 
}
fun touchesBottom (l: Line, p: Point) : Boolean{
	return (p.y == Math.max(l.s.y, l.e.y) 
					&& p.x >= Math.min(l.s.x, l.e.x) 
					&& p.x <= Math.max(l.s.x, l.e.x))
}

fun borderCutsSquare (p1: Point, p2: Point, borders: List<Line>) : Boolean {
	val xMin = Math.min(p1.x, p2.x)
	val xMax = Math.max(p1.x, p2.x)
	val yMin = Math.min(p1.y, p2.y)
	val yMax = Math.max(p1.y, p2.y)

	for (line in borders) {
		if (line.s.x == line.e.x) { // line is vertical
			if (line.s.x > xMin && line.s.x < xMax && Math.min(line.s.y,line.e.y) < yMax && Math.max(line.s.y,line.e.y) > yMin) {
				return true
			}
		} else { // line is horizontal
			if (line.s.y > yMin && line.s.y < yMax && Math.min(line.s.x,line.e.x) < xMax && Math.max(line.s.x,line.e.x) > xMin) {
				return true
			}}
	}

	return false
}

class Point (val x: Long, val y: Long) {
	override fun toString() : String{
		return "($x,$y)"
	}
}

class Line (val s: Point, val e: Point) {
	override fun toString() : String {
		return ("$s-$e")
	}
}