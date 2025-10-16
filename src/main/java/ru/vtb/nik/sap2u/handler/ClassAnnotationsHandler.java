package ru.vtb.nik.sap2u.handler;

import com.intellij.psi.PsiField;

public interface ClassAnnotationsHandler {
   void fillAnnotations(PsiField field,  boolean isList);
}
