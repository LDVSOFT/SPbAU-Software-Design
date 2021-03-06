package ru.spbau.mit

import org.junit.Before
import org.junit.Test
import ru.spbau.mit.commands.*
import kotlin.test.assertEquals

/**
 * Created by rebryk on 9/21/16.
 */

class Test {
    val shell = Shell()

    @Before
    fun init() {
        shell.registerCommand("echo", Echo())
        shell.registerCommand("wc", Wc())
        shell.registerCommand("cat", Cat())
        shell.registerCommand("grep", Grep())
    }

    @Test
    fun testEcho() {
        assertEquals("test message", shell.execute("echo 'test message'"))
    }

    @Test
    fun testCat() {
        shell.execute("file = \'src/main/kotlin/ru/spbau/mit/main.kt\'")
        assertEquals(shell.execute("cat \$file | wc"), shell.execute("cat src/main/kotlin/ru/spbau/mit/main.kt | wc"))
    }

    @Test
    fun testGrep() {
        assertEquals("1 1 34", shell.execute("grep modelVersion pom.xml | wc"))
    }
}