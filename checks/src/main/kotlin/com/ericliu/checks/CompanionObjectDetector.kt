package com.ericliu.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.kotlin.KtNodeTypes.OBJECT_DECLARATION
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UFile

class CompanionObjectDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(
            UFile::class.java,
            UClass::class.java,
        )
    }


    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitClass(node: UClass) {
                if (node.sourcePsi.toString() == "OBJECT_DECLARATION") {
                    context.report(
                        ISSUE,
                        context.getLocation(node as UElement),
                        "Haha, found a companion object."
                    )
                }
            }

            override fun visitFile(node: UFile) {
                /* no-op */
            }
        }
    }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "CompanionObjectUsages",
            briefDescription = "Let's find companion objects",
            explanation = """
                This check goes through class declarations and find the companion objects that I don't like.
            """,
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                CompanionObjectDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

}