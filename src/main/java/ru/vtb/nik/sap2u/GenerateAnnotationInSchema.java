package ru.vtb.nik.sap2u;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiType;
import org.jetbrains.annotations.NotNull;
import ru.vtb.nik.sap2u.classes.TypeBasedDelegator;

import java.util.List;

public class GenerateAnnotationInSchema extends AnAction {
    private final TypeBasedDelegator delegator = new TypeBasedDelegator();


    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiElement element = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.PSI_ELEMENT);
        e.getPresentation().setEnabledAndVisible(element instanceof PsiClass);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project == null) return;
        PsiFile file = anActionEvent.getData(CommonDataKeys.PSI_FILE);
        if (notJavaFile(file)) return;
        WriteCommandAction.runWriteCommandAction(project, () -> { //1263348

            for (PsiClass psiClass : ((PsiJavaFile) file).getClasses()) {

                for (PsiField field : psiClass.getFields()) {
                    var isList = PsiType.getTypeByName(List.class.getCanonicalName(), project, field.getResolveScope()).isAssignableFrom(field.getType());
                    var canonicalType = PsiTypeUtils.getCanonicalType(field.getType());

                    delegator.handle(canonicalType, field, isList);
                }
            }
        });
    }

    private static boolean notJavaFile(PsiFile file) {
        return !(file instanceof PsiJavaFile);
    }

}





