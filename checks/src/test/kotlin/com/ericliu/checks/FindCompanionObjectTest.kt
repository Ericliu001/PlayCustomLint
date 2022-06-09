package com.ericliu.checks

import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test

class FindCompanionObjectTest {
    @Test
    fun findComponionObjects() {
        TestLintTask.lint().files(
            kotlin("ClassWithCompanionObject.kt"),
        )
            .detector(CompanionObjectDetector())
            .issues(CompanionObjectDetector.ISSUE)
            .allowCompilationErrors()
            .run()
            .expectWarningCount(1)
    }
}