package cn.dong.viewbinder.compiler

import cn.dong.viewbinder.annotation.BindView
import com.google.auto.service.AutoService
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic
import kotlin.collections.LinkedHashMap

/**
 * @author dong on 2018/07/28.
 */
@AutoService(Processor::class) // 自动生成 META-INF 信息
class ViewBinderProcessor : AbstractProcessor() {
    private lateinit var elementUtils: Elements  // 元素操作辅助类
    private lateinit var messager: Messager  // 日志辅助类
    private lateinit var filer: Filer // 文件辅助类

    @Synchronized
    override fun init(env: ProcessingEnvironment) {
        super.init(env)
        elementUtils = env.elementUtils
        messager = env.messager
        filer = env.filer
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val types = LinkedHashSet<String>()
        types.add(BindView::class.java.canonicalName)
        return types
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(elements: Set<TypeElement>, env: RoundEnvironment): Boolean {
        messager.printMessage(Diagnostic.Kind.NOTE, "view binder process start~")

        // 解析注解信息
        val targetMap = findAndParseTargets(env)

        // 生成代码
        targetMap.forEach { (key, value) ->
            val javaFile = value.generateBinderJavaFile()
            try {
                javaFile.writeTo(filer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }

    private fun findAndParseTargets(env: RoundEnvironment): Map<TypeElement, BindingSet> {
        val targetMap = LinkedHashMap<TypeElement, BindingSet>()

        env.getElementsAnnotatedWith(BindView::class.java).forEach {
            try {
                parseBindView(it, targetMap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return targetMap
    }

    private fun parseBindView(element: Element, targetMap: HashMap<TypeElement, BindingSet>) {
        // 被注解的类
        val targetTypeElement = element.enclosingElement as TypeElement
        // 被注解的字段
        val bindViewField = BindViewField(element)
        val bindingSet = getBindingSet(targetMap, targetTypeElement)
        bindingSet.fields.add(bindViewField)
    }

    private fun getBindingSet(targetMap: HashMap<TypeElement, BindingSet>, targetTypeElement: TypeElement): BindingSet {
        return targetMap[targetTypeElement] ?: run {
            val bindingSet = BindingSet(targetTypeElement, elementUtils)
            targetMap[targetTypeElement] = bindingSet
            bindingSet
        }
    }
}
