package ru.vtb.nik.sap2u.handler;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiParameter;

import java.util.Objects;

import static ru.vtb.nik.sap2u.AnnotationUtils.INT_MAX_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.INT_MIN_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.LONG_MAX_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.LONG_MIN_VALUE;
import static ru.vtb.nik.sap2u.AnnotationUtils.MAX_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.MIN_ANNOTATION;
import static ru.vtb.nik.sap2u.AnnotationUtils.mappingAnnotations;
import static ru.vtb.nik.sap2u.PsiTypeUtils.getCanonicalType;

public class ControllerAnnotationHandler {
    public void processClass(PsiClass psiClass, Project project) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        for (PsiMethod method : psiClass.getMethods()) {

            PsiModifierList modifiers = method.getModifierList();
            if (isNotControllerEndpoint(modifiers)) {
                continue;
            }
            var classModifiers = psiClass.getModifierList();
            addAnnotations(factory, Objects.requireNonNull(classModifiers), "io.swagger.v3.oas.annotations.tags.Tag", "@Tag(name = \"\",\n" +
                    "        description = \"\")");


            // Добавляем только если аннотации отсутствуют
            addAnnotations(factory, modifiers, "io.swagger.v3.oas.annotations.Operation", "@Operation(summary = \"\")");
            addAnnotations(factory, modifiers, "org.springframework.security.access.annotation.Secured", "@Secured({})");

            addAnnotations(factory, modifiers, "io.swagger.v3.oas.annotations.responses.ApiResponses",
                    """
                            @ApiResponses(value = {
                                @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = @Content(schema = @Schema(type = "string", format = "binary", maxLength = 255))),
                                @ApiResponse(responseCode = "400", description = "Синтаксическая ошибка запроса", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
                                @ApiResponse(responseCode = "403", description = "Ограничения доступа", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
                                @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
                                @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = APPLICATION_JSON_VALUE))
                            })"""
            );
            for (PsiParameter parameter : method.getParameterList().getParameters()) {
                var canonType = getCanonicalType(parameter.getType());
                if (Long.class.getCanonicalName().equals(canonType)) {
                    addAnnotations(factory, Objects.requireNonNull(parameter.getModifierList()), MIN_ANNOTATION, LONG_MIN_VALUE);
                    addAnnotations(factory, parameter.getModifierList(), MAX_ANNOTATION, LONG_MAX_VALUE);
                } else if (Integer.class.getCanonicalName().equals(canonType)) {
                    addAnnotations(factory, Objects.requireNonNull(parameter.getModifierList()), MIN_ANNOTATION, INT_MIN_VALUE);
                    addAnnotations(factory, parameter.getModifierList(), MAX_ANNOTATION, INT_MAX_VALUE);
                }
            }
        }
    }

    private void addAnnotations(PsiElementFactory factory, PsiModifierList modifiers,
                                String fqn, String annotationText) {
        if (modifiers.findAnnotation(fqn) == null) {
            PsiAnnotation annotation = factory.createAnnotationFromText(annotationText, null);
            modifiers.addAfter(annotation, modifiers.getFirstChild());
        }
    }

    private boolean isNotControllerEndpoint(PsiModifierList modifiers) {
        if (modifiers == null) return true;

        for (String mapping : mappingAnnotations) {
            if (modifiers.findAnnotation(mapping) != null) {
                return false;
            }
        }
        return true;
    }
}
