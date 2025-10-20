package ru.vtb.nik.sap2u.handler.types;

import com.intellij.psi.PsiNamedElement;
import ru.vtb.nik.sap2u.handler.ClassAnnotationsHandler;

import static ru.vtb.nik.sap2u.AnnotationUtils.ARRAY_SCHEMA_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.LONG_MAX_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.LONG_MIN_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.MAX_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.MIN_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.SCHEMA_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.addFieldAnnotations;

public class LongHandler implements ClassAnnotationsHandler {


    @Override
    public void fillAnnotations(PsiNamedElement field, boolean isList) {
            if (isList) {
                addFieldAnnotations(field, ARRAY_SCHEMA_ANNOTATION, "@ArraySchema(schema = @Schema(description = \"Id запроса\", minimum = \"0\", maximum = \"999999999999999\"), maxItems = Integer.MAX_VALUE)");
                return;
            }
            addFieldAnnotations(field, MIN_ANNOTATION, LONG_MIN_VALUE);
            addFieldAnnotations(field, MAX_ANNOTATION, LONG_MAX_VALUE);
            addFieldAnnotations(field, SCHEMA_ANNOTATION, "@Schema(description =\"" + field.getName() + "\")");

    }
}
