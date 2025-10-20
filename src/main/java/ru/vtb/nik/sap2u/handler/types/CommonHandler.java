package ru.vtb.nik.sap2u.handler.types;

import com.intellij.psi.PsiNamedElement;
import ru.vtb.nik.sap2u.handler.ClassAnnotationsHandler;

import static ru.vtb.nik.sap2u.AnnotationUtils.ARRAY_SCHEMA_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.SCHEMA_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.addFieldAnnotations;

public class CommonHandler implements ClassAnnotationsHandler {

    public void fillAnnotations(PsiNamedElement field, boolean isList) {
        if (isList) {
            addFieldAnnotations(field, ARRAY_SCHEMA_ANNOTATION, "@ArraySchema(schema = @Schema(description = \"" + field.getName() + "\"), maxItems = Integer.MAX_VALUE)");
            return;
        }
        addFieldAnnotations(field, SCHEMA_ANNOTATION, "@Schema(description = \"" + field.getName() + "\")" );
    }
}
