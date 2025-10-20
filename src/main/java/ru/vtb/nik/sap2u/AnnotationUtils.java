package ru.vtb.nik.sap2u;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCompiledElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiRecordComponent;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.List;

public class AnnotationUtils {
    public static final List<String> controllersClasses = List.of(
            "org.springframework.web.bind.annotation.RestController",
            "org.springframework.stereotype.Controller"
    );

    public static final List<String> mappingAnnotations = List.of(
            "org.springframework.web.bind.annotation.GetMapping",
            "org.springframework.web.bind.annotation.PostMapping",
            "org.springframework.web.bind.annotation.PutMapping",
            "org.springframework.web.bind.annotation.DeleteMapping",
            "org.springframework.web.bind.annotation.PatchMapping",
            "org.springframework.web.bind.annotation.RequestMapping"
    );

    public static final String MAX_ANNOTATION = "jakarta.validation.constraints.Max";
    public static final String MIN_ANNOTATION = "jakarta.validation.constraints.Min";
    public static final String SCHEMA_ANNOTATION = "io.swagger.v3.oas.annotations.media.Schema";
    public static final String ARRAY_SCHEMA_ANNOTATION = "io.swagger.v3.oas.annotations.media.ArraySchema";
    public static final String DECIMAL_MIN_ANNOTATION = "jakarta.validation.constraints.DecimalMin";
    public static final String DECIMAL_MAX_ANNOTATION = "jakarta.validation.constraints.DecimalMax";
    public static final String LONG_MIN_VALUE = "@Min(0)";
    public static final String LONG_MAX_VALUE = "@Max(999999999999999L)";
    public static final String BIG_DECIMAL_MIN_VALUE = "@DecimalMin(\"0.0\")";
    public static final String BIG_DECIMAL_MAX_VALUE = "@DecimalMax(\"999999999999999\")";
    public static final String INT_MIN_VALUE = "@Min(0)";
    public static final String INT_MAX_VALUE = "@Max(Integer.MAX_VALUE)";

    /**
     * Class who add annotation, and imports in classes/records
     *
     * @param element        PsiElement, element where will be added annotation
     * @param fullAnName     Full annotation import
     * @param annotationText Annotation text, with @ and params in text format
     */
    public static void addFieldAnnotations(PsiNamedElement element, String fullAnName, String annotationText) {

        if (!(element instanceof PsiField || element instanceof PsiRecordComponent)) return;

        Project project = element.getProject();
        PsiModifierList modifierList;

        if (element instanceof PsiField field) {
            modifierList = field.getModifierList();
        } else {
            modifierList = ((PsiRecordComponent) element).getModifierList();
        }

        if (modifierList == null) return;


        if (element instanceof PsiCompiledElement) return;
        if (modifierList.findAnnotation(fullAnName) != null) return;

        PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
        PsiAnnotation newAnnotation = factory.createAnnotationFromText(annotationText, element);

        PsiElement firstModifier = modifierList.getFirstChild();
        if (firstModifier != null) {
            modifierList.addBefore(newAnnotation, firstModifier);
        } else {
            modifierList.add(newAnnotation);
        }
        CodeStyleManager.getInstance(project).reformat(modifierList);


        PsiJavaFile javaFile = PsiTreeUtil.getParentOfType(element, PsiJavaFile.class);
        if (javaFile != null) {
            PsiImportList importList = javaFile.getImportList();
            if (importList != null) {
                PsiClass annotationClass = JavaPsiFacade.getInstance(project)
                        .findClass(fullAnName, element.getResolveScope());
                if (annotationClass != null) {
                    JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(project);
                    styleManager.addImport(javaFile, annotationClass);
                }
            }
        }
    }

}

