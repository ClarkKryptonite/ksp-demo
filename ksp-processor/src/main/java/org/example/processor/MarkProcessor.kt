package org.example.processor

import com.example.launchbase.util.appendText
import com.example.launchbase.util.newLine
import com.example.launchbase.util.toEscapeStr
import com.example.launchbase.util.toJsonStr
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import org.example.IMarkNode
import org.example.MarkNode
import org.example.bean.MarkNodeInfo
import org.example.util.generatedFilePackageName

class MarkProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return MarkProcessor(environment.codeGenerator, environment.logger)
    }
}

class MarkProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    private var isInitScanFile = true
    private var hasGeneratedFileAfterInitScan = true

    private fun log(content: String) {
        logger.info("MarkProcessor:--:$content")
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        when {
            isInitScanFile -> {
                log("scanning...")
                resolver.traversalLaunchNodeAnnotation()
                isInitScanFile = false
            }
            hasGeneratedFileAfterInitScan -> {
                log("peek")
                resolver.getDeclarationsFromPackage(generatedFilePackageName).forEach {
                    log("getFromPackage it:$it annotations:${it.annotations.joinToString()}")
                }
                hasGeneratedFileAfterInitScan = false
            }

            else -> {
                log("process else")
            }
        }
        return emptyList()
    }

    private fun Resolver.traversalLaunchNodeAnnotation() {
        val visitor = MarkNodeVisitor()
        getSymbolsWithAnnotation(MarkNode::class.java.name)
            .forEach {
                it.accept(visitor, Unit)
            }
    }

    inner class MarkNodeVisitor : KSVisitorVoid() {
        @OptIn(KspExperimental::class)
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            if (classDeclaration.superTypes.none { it.toString() == IMarkNode::class.java.simpleName }) {
                logger.exception(NotImplementedError("${classDeclaration.simpleName.asString()} not implement IMarkNode"))
            }
            classDeclaration.getAnnotationsByType(MarkNode::class).forEach { markNode ->
                log("MarkNodeVisitor MarkNode.name:${markNode.name} priority:${markNode.priority}")
                val fileName = "${classDeclaration.simpleName.asString()}MarkTransit"
                val markNodeInfo = MarkNodeInfo(
                    classDeclaration.packageName.asString(),
                    classDeclaration.simpleName.asString(),
                    priority = markNode.priority
                )
                codeGenerator.createNewFile(
                    Dependencies(true, classDeclaration.containingFile!!),
                    generatedFilePackageName,
                    fileName
                ).use { outputStream ->
                    with(outputStream) {
                        // package
                        appendText("package $generatedFilePackageName")
                        newLine(2)

                        // import
                        appendText("import org.example.MarkNodeTransit")
                        newLine(2)

                        // annotation
                        appendText(
                            "@MarkNodeTransit(\"${
                                markNodeInfo.toJsonStr().toEscapeStr()
                            }\")"
                        )
                        newLine()

                        // class start
                        appendText("val ${fileName.replaceFirstChar { it.lowercase() }} = null")
                    }
                }
            }
        }
    }
}