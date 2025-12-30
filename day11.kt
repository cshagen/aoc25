val FILENAME = "data/d11-input.txt"

fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

var nodes = mutableMapOf<String,Node>()

fun part1(lines: List<String>): Long {
// Read the file and create graph
	for (line in lines) {
		val (source, next) = line.split(":")
		val targets = next.trim().split(" ")
		if (!nodes.contains(source)) nodes[source] = Node (source, 0, targets.toMutableSet(), mutableSetOf<String>())
		 	else nodes[source]!!.next = targets.toMutableSet()
		for (t in targets) {
			if (!nodes.contains(t)) nodes[t] = Node (t, 0, mutableSetOf<String>(), mutableSetOf<String>())
			nodes[t]!!.prev.add(source)
		}
	}
	// Find the paths
	return countPaths("you","out")
}
fun part2(lines: List<String>): Long {
	// Read the file and create graph
	for (line in lines) {
		val (source, next) = line.split(":")
		val targets = next.trim().split(" ")
		if (!nodes.contains(source)) nodes[source] = Node (source, 0, targets.toMutableSet(), mutableSetOf<String>())
		 	else nodes[source]!!.next = targets.toMutableSet()
		for (t in targets) {
			if (!nodes.contains(t)) nodes[t] = Node (t, 0, mutableSetOf<String>(), mutableSetOf<String>())
			nodes[t]!!.prev.add(source)
		}
	}
	// Find the paths
	val count1 = countPaths("svr", "fft")
	val count2 = countPaths("fft","dac")
	val count3 = countPaths("dac","out")
	return count1 * count2 * count3
}
fun countPaths (start: String, end: String) : Long {
	var current = mutableSetOf<String>(start)
	for (n in nodes) {
		n.value.count = 0
	}
	nodes[start]!!.count = 1
	while (current.size > 0) {
		current = nextRound(current, end)
	}
	return nodes[end]!!.count.toLong()
}
fun nextRound (current: Set<String>, goal: String) : MutableSet<String> {
	var result = mutableSetOf<String>()
	current.forEach { cur ->
		nodes[cur]!!.next.forEach { nxt ->
			nodes[nxt]!!.count = nodes[nxt]!!.prev.sumOf { nodes[it]!!.count }
			if (nxt != goal) result.add(nxt)
		}
	}
	return result
}
data class Node (
	val id: String, 
	var count: Int, 
	var next: MutableSet<String>, 
	var prev: MutableSet<String>
)
