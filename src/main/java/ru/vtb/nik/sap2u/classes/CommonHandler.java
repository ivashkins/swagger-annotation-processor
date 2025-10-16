package ru.vtb.nik.sap2u.classes;

import com.intellij.psi.PsiField;

import static ru.vtb.nik.sap2u.AnnotationUtils.addAnnotationIfAbsent;

public class CommonHandler implements ClassAnnotationsHandler{

    public void fillAnnotations(PsiField field, boolean isList) {
        if (isList) {
            addAnnotationIfAbsent(field, "io.swagger.v3.oas.annotations.media.ArraySchema", "@ArraySchema(schema = @Schema(description = \"" + field.getName() + "\"), maxItems = Integer.MAX_VALUE)");
            return;
        }
        addAnnotationIfAbsent(field, "io.swagger.v3.oas.annotations.media.Schema", "@Schema(description = \"" + field.getName() + "\"), maxItems = Integer.MAX_VALUE)" );
    }
}
