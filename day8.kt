val FILENAME = "data/d8-input.txt"

fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Int {
	val testsize = 1000
	val maxcount = 3
	var result = 1
	val boxes = getBoxes(lines)
	val pairs = calculateDistancesBetweenPairs(boxes)
	var circuits : MutableList<MutableSet<Int>> = mutableListOf()
	
	for (i in 0 until testsize) {
		val newPair = Pair(pairs[i].first, pairs[i].second)
		var matched = false
		var firstCircuit: MutableSet<Int>? = null
					
		for (circuit in circuits) {
			if (circuit.contains(newPair.first) || circuit.contains(newPair.second)) {
				if (!matched) {
					circuit.add(newPair.first)
					circuit.add(newPair.second)
					matched = true
					firstCircuit = circuit
				} else {
					if (firstCircuit != null) {
						mergeCircuitInto(firstCircuit, circuit)
					}
				} 
			}
		}
		if (!matched) {
				circuits.add(mutableSetOf(newPair.first, newPair.second))
		}
	}
	circuits.sortByDescending { it.size }
	for (i in 0 until maxcount) {
		val circuit = circuits[i]
		result *= circuit.size
	}
	return result
}

fun part2(lines: List<String>): Long {
	val boxes = getBoxes(lines)
	val pairs = calculateDistancesBetweenPairs(boxes)
	var circuits : MutableList<MutableSet<Int>> = mutableListOf()
	for (b in boxes.keys) {
		circuits.add(mutableSetOf(b))
	}
	for (newLink  in pairs) {
			mergeLinkInto(circuits, newLink)
			if (circuits.size == 1) {
				return  boxes[newLink.first]!!.first * boxes[newLink.second]!!.first
		}
	}
	return 0L
}

fun distance (a: Triple<Long, Long, Long>, b: Triple<Long, Long, Long>): Double {
	return Math.sqrt(Math.pow((a.first - b.first).toDouble(), 2.0) +
		Math.pow((a.second - b.second).toDouble(), 2.0) +
		Math.pow((a.third - b.third).toDouble(), 2.0))
}

fun mergeCircuitInto (target: MutableSet<Int>, source: MutableSet<Int>) {
	for (item in source) {
		target.add(item)
	}
	source.clear()
}

fun getBoxes (lines: List<String>): MutableMap<Int,Triple<Long, Long, Long>> {
	var boxes = mutableMapOf<Int,Triple<Long, Long, Long>>()
	lines.forEachIndexed { id, line ->
		val triple = line.split(",").map { it.toLong() }
			.let { (a, b, c) -> Triple(a, b, c) }
		boxes[id] = triple
	}
	return boxes
}

fun calculateDistancesBetweenPairs (
	boxes: MutableMap<Int,Triple<Long, Long, Long>>,
): MutableList<Triple<Int, Int, Double>> {
	var distances = mutableListOf<Triple<Int, Int, Double>>()
	boxes.forEach { (key1, box1) ->
		boxes.forEach {(key2, box2) ->
			if (key2 > key1) {
				val dist = distance(box1, box2)
				distances.add(Triple(key1, key2, dist))
			}
		}
	}
	distances.sortBy { it.third }
	return distances
}

fun mergeLinkInto (circuits: MutableList<MutableSet<Int>>, newLink: Triple<Int, Int, Double>) {
	var matched = false
	var firstCircuit: MutableSet<Int>? = null
	var secondCircuit: MutableSet<Int>? = null
	for (circuit in circuits) {
		if (circuit.contains(newLink.first) || circuit.contains(newLink.second)) {
			if (!matched) {
				circuit.add(newLink.first)
				circuit.add(newLink.second)
				matched = true
				firstCircuit = circuit
			} else {
				if (firstCircuit != null) {
					mergeCircuitInto(firstCircuit, circuit)
					secondCircuit = circuit
					break
				}
			} 
		}
	}
	if (secondCircuit != null) {
			circuits.remove(secondCircuit)
	}
}