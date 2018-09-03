package io.starteos.dappsdk.processor;

import com.google.auto.service.AutoService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import io.starteos.dappsdk.annotation.Namespace;

@AutoService(Processor.class)
public class NamespaceProcessor extends AbstractProcessor {
    private Types types;
    private Elements elements;
    private Filer filer;
    private Messager messager;
    private int index = 0;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.types = processingEnvironment.getTypeUtils();
        this.elements = processingEnvironment.getElementUtils();
        this.filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "init!");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Namespace.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Namespace.class);
        Map<String, List<String>> map = new HashMap<>();
        for (Element element : elements) {
            if (!checkAnnotationValid(element, Namespace.class)) {
                return false;
            }
            String className = ((TypeElement) element).getQualifiedName().toString();
            Namespace namespace = element.getAnnotation(Namespace.class);
            String namespaceValue = namespace.value();
            if (map.containsKey(namespaceValue)) {
                map.get(namespaceValue).add(className);
            } else {
                List<String> classNames = new ArrayList<>();
                classNames.add(className);
                map.put(namespaceValue, classNames);
            }
        }
        if (map.size() != 0) {
            createProxy(map, elements);
        }
        return true;
    }

    private void createProxy(Map<String, List<String>> map, Set<? extends Element> elements) {
        String json = new Gson().toJson(map);
        messager.printMessage(Diagnostic.Kind.NOTE, json);
        try {
            JavaFileObject javaFileObject = filer.createSourceFile(
                    "io.starteos.dappsdk.proxy.Builder" + index,
                    elements.toArray(new Element[]{})
            );
            Writer writer = javaFileObject.openWriter();
            writer.write(generateCode(index, json));
            writer.flush();
            writer.close();
            index++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateCode(int index, String json) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package io.starteos.dappsdk.proxy;\n");
        stringBuilder.append("public class Builder" + index + " {\n");
        stringBuilder.append("public String getNamespaces(){\n");
        stringBuilder.append("return \"" + json.replace("\"", "\\\"") + "\";\n");
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    private boolean checkAnnotationValid(Element annotatedElement, Class clazz) {
        if (annotatedElement.getKind() != ElementKind.CLASS) {
            error(annotatedElement, "%s must be declared on class.", clazz.getSimpleName());
            return false;
        }
        if (ClassValidator.isPrivate(annotatedElement)) {
            error(annotatedElement, "%s() must can not be private.", annotatedElement.getSimpleName());
            return false;
        }
        return true;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
