package com.ericliu.checks

import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test

class ImportClassDetectorTest {

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