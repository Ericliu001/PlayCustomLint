package com.ericliu.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UImportStatement

class ImportClassesDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(UImportStatement::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return ImportVisitor(context)
    }

    private class ImportVisitor(val context: JavaContext) : UElementHandler() {
        override fun visitImportStatement(statement: UImportStatement) {
            context.report(
                ISSUE,
                context.getLocation(statement),
                "Don't import here. I don't like it!!!"
            )
        }
    }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "EricLiu",
            briefDescription = "I don't like this import.",
            explanation = """
                This check goes through import statements and find the imports that I don't like.
            """,
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                ImportClassesDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}