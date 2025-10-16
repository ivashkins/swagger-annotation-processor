package ru.vtb.nik.sap2u.handler.types;

import com.intellij.psi.PsiField;
import ru.vtb.nik.sap2u.handler.ClassAnnotationsHandler;

import static ru.vtb.nik.sap2u.AnnotationUtils.INT_MAX_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.INT_MIN_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.MAX_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.MIN_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.SCHEMA_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.addFieldAnnotations;

public class IntegerHandler implements ClassAnnotationsHandler {

    public void fillAnnotations(PsiField field, boolean isList) {
            addFieldAnnotations(field, MIN_ANNOTATION, INT_MIN_VALUE);
            addFieldAnnotations(field, MAX_ANNOTATION, INT_MAX_VALUE);
            addFieldAnnotations(field, SCHEMA_ANNOTATION, "@Schema(description =\"" + field.getName() + "\")");
    }
}
