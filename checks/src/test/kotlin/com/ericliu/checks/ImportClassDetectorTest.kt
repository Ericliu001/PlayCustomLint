package com.ericliu.checks

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.ericliu.checks.ImportClassesDetector.Companion
import com.google.common.io.Resources
import junit.framework.Assert.fail
import org.junit.Test
import java.io.File
import java.io.IOException

class ImportClassDetectorTest {

    val testResourcesPath = "sample/"

    private fun testResourcesKotlinFile(path: String): String {
        try {

            return Resources.toString(
                Resources.getResource(testResourcesPath + File.separator + path),
                Charsets.UTF_8
            )
        } catch (e: IOException) {
            fail("Could not find file $path")
            throw AssertionError(e)
        }
    }

    private fun kotlin(relativePath: String): TestFile {
        return TestFiles.kotlin("src/$relativePath", testResourcesKotlinFile(relativePath))
    }


    @Test
    fun importsMoreThan2ViewClasses_shouldWarning() {
        val haha = true
        assert(haha).equals(true)

        TestLintTask.lint().files(
            kotlin("ImportAndroidView.kt")
        )
            .detector(ImportClassesDetector())
            .issues(ImportClassesDetector.ISSUE)
            .allowCompilationErrors()
            .run()
            .expectWarningCount(1)
    }
}