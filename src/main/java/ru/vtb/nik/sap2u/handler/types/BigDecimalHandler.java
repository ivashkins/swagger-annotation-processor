package ru.vtb.nik.sap2u.handler.types;

import com.intellij.psi.PsiField;
import ru.vtb.nik.sap2u.handler.ClassAnnotationsHandler;

import static ru.vtb.nik.sap2u.AnnotationUtils.BIG_DECIMAL_MAX_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.BIG_DECIMAL_MIN_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.DECIMAL_MAX_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.DECIMAL_MIN_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.SCHEMA_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.addFieldAnnotations;

public class BigDecimalHandler implements ClassAnnotationsHandler {


    public void fillAnnotations(PsiField field, boolean isList) {
            addFieldAnnotations(field, DECIMAL_MIN_ANNOTATION, BIG_DECIMAL_MIN_VALUE);
            addFieldAnnotations(field, DECIMAL_MAX_ANNOTATION, BIG_DECIMAL_MAX_VALUE);
            addFieldAnnotations(field, SCHEMA_ANNOTATION, "@Schema(description =\"" + field.getName() + "\")");
    }
}
