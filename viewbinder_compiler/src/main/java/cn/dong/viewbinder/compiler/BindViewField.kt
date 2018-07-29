package cn.dong.viewbinder.compiler

import cn.dong.viewbinder.annotation.BindView
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.VariableElement

/**
 * @author dong on 2018/07/28.
 */
class BindViewField(element: Element) {
    val fieldElement: VariableElement
    val fieldName: String
    val fieldTypeName: String
    val resId: Int

    init {
        if (element.kind != ElementKind.FIELD) {
            throw IllegalArgumentException("Only field can be annotated with @${BindView::class.java.simpleName}")
        }
        fieldElement = element as VariableElement
        fieldName = fieldElement.simpleName.toString()
        fieldTypeName = TypeName.get(fieldElement.asType()).toString()
        resId = fieldElement.getAnnotation(BindView::class.java).value
    }
}
