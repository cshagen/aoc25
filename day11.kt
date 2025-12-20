val FILENAME = "data/d11-input.txt"
val re = Regex("^\\[(.+)\\] (\\(.+\\)\\s)\\{(.+)\\}$")
fun main() {
	val file = java.io.File(FILENAME)
	//println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Int {
	var steps = mutableMapOf<String,List<String>>()
	val start="you"
	var nodes = mutableMapOf<String,Int>(start to 0)
	var current = mutableListOf<List<String>>()
	for (line in lines) {
		val (source, next) = line.split(":")
		val targets = next.trim().split(" ")
		steps[source]=targets
	}
	current.add(mutableListOf(start,start))
	var done = false
	while (!done) {
		current = repeat1(current, steps, nodes)
		done = true
		for (path in current) {
			if (path[1] != "out")
			done = false
			break
		}
	}
	return current.size
}

fun part2(lines: List<String>): Int {
	var result = 0
	var steps = mutableMapOf<String,List<String>>()
	val start="svr"
	var nodes = mutableMapOf<String,Int>(start to 0)
	var current = mutableListOf<List<String>>()
	for (line in lines) {
		val (source, next) = line.split(":")
		val targets = next.trim().split(" ")
		steps[source]=targets
	}
	current.add(mutableListOf(start))
	var done = false
	while (!done) {
		current = repeat2(current, steps, nodes)
		done = true
		for (path in current) {
			if (path.last() != "out")
			done = false
			break
		}
		println(current.size)
	}
	for (path in current) {
			if (path.contains("dac") && (path.contains("fft"))) {
				result +=1
			}
		}
	return result
}

fun repeat1 (current: List<List<String>>, steps: MutableMap<String,List<String>>, nodes: MutableMap<String,Int> ) : MutableList<List<String>> {
	var result = mutableListOf<List<String>>()
	current.forEach { path ->
		var matched = false
		steps.forEach { step ->
			if (path.last() == step.key) {
				for (target in step.value) {
					result.add(mutableListOf(path[0],target))
					matched = true
					}
				}
		} 
		if (!matched) {
			result.add(path)
		}
	}
	return result
}

fun repeat2 (current: List<List<String>>, steps: MutableMap<String,List<String>>, nodes: MutableMap<String,Int> ) : MutableList<List<String>> {
	var result = mutableListOf<List<String>>()
	current.forEach { path ->
		var matched = false
		steps.forEach { step ->
			if (path.last() == step.key) {
				for (target in step.value) {
					if (path.contains(target)) {
						println("loop")
					} else {
						var p=path.toMutableList()
						p.add(target)
						result.add(p)
						matched = true
					}
				}
			} 
		}
		if (!matched) {
			result.add(path)
		}
	}	
	//println ("transitioned from $current to $result")
	return result
}