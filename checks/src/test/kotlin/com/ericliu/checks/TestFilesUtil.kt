package com.ericliu.checks

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles
import com.google.common.io.Resources
import org.junit.Assert
import java.io.File
import java.io.IOException

val testResourcesPath = "sample/"

fun testResourcesKotlinFile(path: String): String {
    try {
        return Resources.toString(
            Resources.getResource(testResourcesPath + File.separator + path),
            Charsets.UTF_8
        )
    } catch (e: IOException) {
        Assert.fail("Could not find file $path")
        throw AssertionError(e)
    }
}

fun kotlin(relativePath: String): TestFile {
    return TestFiles.kotlin("src/$relativePath", testResourcesKotlinFile(relativePath))
}
