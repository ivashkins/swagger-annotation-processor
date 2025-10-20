package ru.vtb.nik.sap2u.handler;

import com.intellij.psi.PsiNamedElement;

/**
 * Handler for annotated field in classes or records
 */
public interface ClassAnnotationsHandler {
   void fillAnnotations(PsiNamedElement field, boolean isList);
}
