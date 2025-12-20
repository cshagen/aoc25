val FILENAME = "data/d10-input.txt"
val re1 = Regex("^\\[(.+)\\] (\\(.+\\)\\s)\\{(.+)\\}$")
fun main() {
	val file = java.io.File(FILENAME)
	println ("Part 1: ${ part1 (file.readLines()) }")	
	println ("Part 2: ${ part2 (file.readLines()) }")	
	}

fun part1(lines: List<String>): Long {
	var result = 0L
	var targetLights = mutableListOf<List<Boolean>>()
	var buttons = mutableListOf<List<List<Int>>>()
	for (line in lines) {
		val match = re1.find(line)
		if (match != null) {
			targetLights.add (match.groups[1]!!.value?.toCharArray()!!.map { (it == '#') })
			buttons.add(match.groups[2]!!.value?.trim()?.split(" ")!!.map { it.slice(1..it.length-2).split(",").map { it.toInt() } })
		}
	}

	for (case in 0 until targetLights.size) {
		var lights = mutableSetOf(List(targetLights[case].size) { false })
		var found = false
		for (round in 0 until 100) {
			if (!found) {
			lights = applyAllButtons(lights, buttons[case], targetLights[case]).toMutableSet()
			for (light in lights) {
				if (light == targetLights[case]) {
					result += round + 1
					found = true
					break
				}
			}
		}
		}
	}
	return result
}

fun part2(lines: List<String>): Long {
		var result = 0L
	var targetLevels = mutableListOf<List<Int>>()
	var buttons = mutableListOf<List<List<Int>>>()
	for (line in lines) {
		val match = re1.find(line)
		if (match != null) {
			targetLevels.add (match.groups[3]!!.value?.trim()?.split(",")!!.map { it.toInt() })
			buttons.add(match.groups[2]!!.value?.trim()?.split(" ")!!.map { it.slice(1..it.length-2).split(",").map { it.toInt() } })
		}
	}

	for (case in 0 until targetLevels.size) {
		println("Case $case: ")
		var levels = mutableSetOf(List(targetLevels[case].size) { 0 })
		var found = false
		for (round in 0 until 100) {
			if (!found) {
			levels = applyAllButtons2(levels, buttons[case], targetLevels[case]).toMutableSet()
			for (level in levels) {
				if (level == targetLevels[case]) {
					result += round + 1
					found = true
					break
				}
			}
		}
		}
	}
	
	return result
}

fun applyAllButtons (lights: Set<List<Boolean>>, buttons: List<List<Int>>, target: List<Boolean>): Set<List<Boolean>> {
	var results = mutableSetOf<List<Boolean>>()
	var found = false
		
	for (l in lights) {
		if (!found) {
			for (b in buttons) {
				val newLights = applyButton(l, b)
				if (newLights == target) {
					found = true
					results.add(newLights)
					println("Found target lights!")
					break
				}
				results.add(newLights)
			}
		}
		
	}
	return results
	
}

fun applyAllButtons2 (levels: Set<List<Int>>, buttons: List<List<Int>>, target: List<Int>): Set<List<Int>> {
	var results = mutableSetOf<List<Int>>()
	var found = false
		
	for (l in levels) {
		if (!found) {
			for (b in buttons) {
				val newLevels = applyButton2(l, b)
				if (newLevels == target) {
					found = true
					results.add(newLevels)
					println("Found target levels!")
					break
				}
				results.add(newLevels)
			}
		}
		
	}
	return results
	
}
fun applyButton (lights: List<Boolean>, button: List<Int>): List<Boolean> {
	var newLights = lights.toMutableList()
	for (i in button) {
		newLights[i] = !newLights[i]
	}
	return newLights
}

fun applyButton2 (levels: List<Int>, button: List<Int>): List<Int> {
	var newLevels = levels.toMutableList()
	for (i in button) {
		newLevels[i] = newLevels[i]+1
	}
	return newLevels
}