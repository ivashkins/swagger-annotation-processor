package ru.vtb.nik.sap2u.classes;

import com.intellij.psi.PsiField;

import static ru.vtb.nik.sap2u.AnnotationUtils.addAnnotationIfAbsent;

public class IntegerHandler implements ClassAnnotationsHandler {

    public void fillAnnotations(PsiField field, boolean isList) {
            addAnnotationIfAbsent(field, "jakarta.validation.constraints.Min", "@Min(1)");
            addAnnotationIfAbsent(field, "jakarta.validation.constraints.Max", "@Max(Integer.MAX_VALUE)");
            addAnnotationIfAbsent(field, "io.swagger.v3.oas.annotations.media.Schema", "@Schema(description =\"" + field.getName() + "\")");
    }
}
