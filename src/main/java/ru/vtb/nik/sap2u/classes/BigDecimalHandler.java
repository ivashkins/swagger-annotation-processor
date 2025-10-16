package ru.vtb.nik.sap2u.classes;

import com.intellij.psi.PsiField;

import static ru.vtb.nik.sap2u.AnnotationUtils.addAnnotationIfAbsent;

public class BigDecimalHandler implements ClassAnnotationsHandler {


    public void fillAnnotations(PsiField field, boolean isList) {
            addAnnotationIfAbsent(field, "jakarta.validation.constraints.DecimalMin", "@DecimalMin(\"0.0\")");
            addAnnotationIfAbsent(field, "jakarta.validation.constraints.DecimalMax", "@DecimalMax(\"999999999999999\")");
            addAnnotationIfAbsent(field, "io.swagger.v3.oas.annotations.media.Schema", "@Schema(description =\"" + field.getName() + "\")");
    }
}
