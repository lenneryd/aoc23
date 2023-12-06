fun List<String>.bigInts() = map { it.toBigInteger() }
fun List<String>.splitBy(predicate: (String) -> Boolean): List<List<String>> {
    return this.fold(mutableListOf<MutableList<String>>() to 0) { (splits, idx), current ->
        if (!predicate(current)) {
            splits.getOrElse(idx) {
                mutableListOf<String>().also { splits.add(it) }
            }.add(current)
            splits to idx
        } else {
            splits to idx + 1
        }
    }.first
}
