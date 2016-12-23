package ru.spbau.mit

import org.junit.Before
import org.junit.Test
import ru.spbau.mit.commands.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
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
        shell.registerCommand("ls", Ls())
        shell.registerCommand("cd", Cd())
        shell.registerCommand("pwd", Pwd())
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

    @Test
    fun testCdPwd() {
        val cwd = Paths.get(".").normalize().toAbsolutePath();
        assertEquals(cwd.toString(), shell.execute("pwd"))
        assertEquals("", shell.execute("cd src"))
        assertEquals(cwd.resolve("src").toString(), shell.execute("pwd"))
    }

    @Test
    fun testLs() {
        val path = Paths.get("src", "main", "kotlin", "ru", "spbau", "mit")
        val ls = ArrayList<String>()
        Files.newDirectoryStream(path).forEach { ls.add(it.fileName.toString()) }
        assertEquals(ls.joinToString(separator = "\n", postfix = "\n"), shell.execute("ls " + path))
    }
}