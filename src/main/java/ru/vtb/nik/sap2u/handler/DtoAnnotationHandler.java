package ru.vtb.nik.sap2u.handler;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiRecordComponent;
import com.intellij.psi.PsiType;
import ru.vtb.nik.sap2u.PsiTypeUtils;

import java.util.List;

public class DtoAnnotationHandler {
    private final TypeBasedDelegator delegator = new TypeBasedDelegator();

    public void processClass(PsiClass psiClass, Project project) {
        if (psiClass.isRecord()) {
            for (PsiRecordComponent component : psiClass.getRecordComponents()) {
                var isList = PsiType.getTypeByName(List.class.getCanonicalName(), project, component.getResolveScope()).isAssignableFrom(component.getType());
                var canonicalType = PsiTypeUtils.getCanonicalType(component.getType());

                delegator.handle(canonicalType, component, isList);
            }
        } else {
            for (PsiField field : psiClass.getFields()) {
                var isList = PsiType.getTypeByName(List.class.getCanonicalName(), project, field.getResolveScope()).isAssignableFrom(field.getType());
                var canonicalType = PsiTypeUtils.getCanonicalType(field.getType());

                delegator.handle(canonicalType, field, isList);
            }
        }
    }
}
