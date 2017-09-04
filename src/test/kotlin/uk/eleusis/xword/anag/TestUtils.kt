package uk.eleusis.xword.anag

fun time(msg: String, block: () -> Unit) {
    val t = System.currentTimeMillis()
    block()
    println("$msg: timed: ${System.currentTimeMillis() - t}ms")
}