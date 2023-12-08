fun lowestCommonMultiple(a: Long, b: Long) = a*b/greatestCommonDivisor(a, b)

@Suppress("NAME_SHADOWING")
fun greatestCommonDivisor(a: Long, b: Long): Long {
    var a = a
    var b = b
    if (a < 0 || b < 0 || a + b <= 0) {
        throw IllegalArgumentException("GCD Error: a=$a, b=$b")
    }

    while (a > 0 && b > 0) {
        if (a >= b) {
            a %= b
        } else {
            b %= a
        }
    }

    return maxOf(a, b)
}
