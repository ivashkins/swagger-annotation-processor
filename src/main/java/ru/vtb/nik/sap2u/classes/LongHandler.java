package ru.vtb.nik.sap2u.classes;

import com.intellij.psi.PsiField;

import static ru.vtb.nik.sap2u.AnnotationUtils.addAnnotationIfAbsent;

public class LongHandler implements ClassAnnotationsHandler {


    @Override
    public void fillAnnotations(PsiField field, boolean isList) {
            if (isList) {
                addAnnotationIfAbsent(field, "io.swagger.v3.oas.annotations.media.ArraySchema", "@ArraySchema(schema = @Schema(description = \"Id запроса\", minimum = \"0\", maximum = \"999999999999999\"), maxItems = Integer.MAX_VALUE)");
                return;
            }
            addAnnotationIfAbsent(field, "jakarta.validation.constraints.Min", "@Min(0)");
            addAnnotationIfAbsent(field, "jakarta.validation.constraints.Max", "@Max(999999999999999L)");
            addAnnotationIfAbsent(field, "io.swagger.v3.oas.annotations.media.Schema", "@Schema(description =\"" + field.getName() + "\")");

    }
}
