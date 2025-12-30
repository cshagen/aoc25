val FILENAME = "data/d10-input.txt"
val re1 = Regex("^\\[(.+)\\] (\\(.+\\)\\s)\\{(.+)\\}$")
val cache = hashSetOf(List(0) { 0 })
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
					//println("Found target lights!")
					break
				}
				results.add(newLights)
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
		print("[$case]")
		var levels = mutableSetOf(List(targetLevels[case].size) { 0 })
		result += solveCase (buttons[case],targetLevels[case])
		
	}
	return result
}
fun solveCase (buttons: List<List<Int>>, target: List<Int>) : Long {
	var levels = hashSetOf(List(target.size) { 0 })
	var round = 0
	cache.clear()
	while (true) {
		print(".")
		val (solved,newLevels)  = applyAllButtons2(levels, buttons, target)
		if (solved) {
			println("(${round.toLong()+1})")
			levels.clear()
			return round.toLong() + 1
		}
		round++
		levels = newLevels
	}
}

fun applyAllButtons2 (levels: HashSet<List<Int>>, buttons: List<List<Int>>, target: List<Int>): Pair<Boolean,HashSet<List<Int>>> {
	var results = hashSetOf<List<Int>>()
	for (l in levels) {
		if (!cache.contains(l)) {
			for (b in buttons) {
				val newLevels = applyButton2(l, b)
				if (newLevels == target) {
					results.add(newLevels)
					return Pair(true,results)
				} else if (!tooHigh(newLevels,target)) {
					results.add(newLevels)
				}
			}
			cache.add(l)
		}
	}
	return Pair(false,results)
}
fun tooHigh (levels: List<Int>, target: List<Int>) : Boolean {
	for (i in 0 until levels.size) {
		if (levels[i] > target[i]) {
			return true
		}
	}
	return false
}
fun applyButton2 (levels: List<Int>, button: List<Int>): List<Int> {
	var newLevels = levels.toMutableList()
	for (i in button) {
		newLevels[i] = newLevels[i]+1
	}
	return newLevels
}


/*

 (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
	x0(n4+n5) = 3
	x1(n1+n5) = 5 
 	x2(n2+n3+n4) = 4
	x3(n0+n1+n3) = 7


 */