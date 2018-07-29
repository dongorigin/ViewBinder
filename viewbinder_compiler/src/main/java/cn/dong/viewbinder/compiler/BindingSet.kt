package cn.dong.viewbinder.compiler

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

/**
 * @author dong on 2018/07/29.
 */
class BindingSet(
        val targetTypeElement: TypeElement,
        val elementUtils: Elements) {
    val fields = mutableListOf<BindViewField>()

    companion object {
        val BINDER = ClassName.get("cn.dong.viewbinder", "Binder")
        val PROVIDER = ClassName.get("cn.dong.viewbinder.provider", "Provider")
    }

    fun generateBinderJavaFile(): JavaFile {
        val targetType = TypeName.get(targetTypeElement.asType())

        // method bind()
        val bindBuilder = MethodSpec.methodBuilder("bind")
                .addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetType, "target")
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(PROVIDER, "provider")
                .returns(TypeName.VOID)
        fields.forEach {
            bindBuilder.addStatement("target.${it.fieldName} = (${it.fieldTypeName}) provider.findView(source, ${it.resId})")
        }
        val bindMethod = bindBuilder.build()

        // class
        val binder = TypeSpec.classBuilder("${targetTypeElement.simpleName}\$\$Binder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(BINDER, targetType))
                .addMethod(bindMethod)
                .build()
        val packageName = elementUtils.getPackageOf(targetTypeElement).qualifiedName.toString()
        return JavaFile.builder(packageName, binder).build()
    }
}