package uk.eleusis.xword.anag

fun <T> time(msg: String, block: () -> T): T {
    val t = System.currentTimeMillis()
    val result = block()
    println("$msg: timed: ${System.currentTimeMillis() - t}ms")
    return result
}
