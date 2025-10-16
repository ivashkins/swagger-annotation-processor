package ru.vtb.nik.sap2u;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

public class AnnotationUtils {

    /**
     * Добавляет аннотацию к полю, если её ещё нет
     *
     * @param field          PsiField, куда добавляем аннотацию
     * @param fullAnName           Полное имя класса аннотации (для проверки и импорта)
     * @param annotationText Текст аннотации (например "@Min(0)" или сложная с вложенной)
     */
    public static void addAnnotationIfAbsent(PsiField field, String fullAnName, String annotationText) {
        PsiModifierList modifierList = field.getModifierList();
        if (modifierList == null) return;

        // Проверяем наличие
        if (modifierList.findAnnotation(fullAnName) != null) return;

        Project project = field.getProject();
        PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();

        // Создаём аннотацию
        PsiAnnotation newAnnotation = factory.createAnnotationFromText(annotationText, field);

        // Вставляем и форматируем в WriteCommandAction

            PsiElement firstModifier = modifierList.getFirstChild();
            if (firstModifier != null) {
                modifierList.addBefore(newAnnotation, firstModifier);
            } else {
                modifierList.add(newAnnotation);
            }
            CodeStyleManager.getInstance(project).reformat(modifierList);

        // Добавляем импорт
        PsiJavaFile javaFile = (PsiJavaFile) field.getContainingFile();
        PsiImportList importList = javaFile.getImportList();
        if (importList != null) {
            WriteCommandAction.runWriteCommandAction(project, () -> {
                PsiClass annotationClass = JavaPsiFacade.getInstance(project)
                        .findClass(fullAnName, field.getResolveScope());
                if (annotationClass != null) {
                    var styleManager = JavaCodeStyleManager.getInstance(project);
                    styleManager.addImport(javaFile, annotationClass);
                }
            });
        }
    }
}

