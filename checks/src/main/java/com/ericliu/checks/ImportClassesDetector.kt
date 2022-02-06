package com.ericliu.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UFile
import org.jetbrains.uast.UImportStatement

class ImportClassesDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(
            UFile::class.java,
            UImportStatement::class.java
        )
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return ImportVisitor(context)
    }

    private class ImportVisitor(val context: JavaContext) : UElementHandler() {
        private val viewImports = mutableListOf<String>()

        override fun visitFile(node: UFile) {
            viewImports.clear()
        }


        override fun visitImportStatement(statement: UImportStatement) {
            val resolved = statement.resolve()
            if (resolved is PsiClass) {
                val qualifiedName = (resolved as PsiClass).qualifiedName ?: ""
                if (qualifiedName.contains("android.view.")) {
                    viewImports.add(qualifiedName)

                    if (viewImports.count() >= 3) {
                        context.report(
                            ISSUE,
                            context.getLocation(statement),
                            "Don't import here. I don't like it!!!"
                        )
                    }

                }
            }

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