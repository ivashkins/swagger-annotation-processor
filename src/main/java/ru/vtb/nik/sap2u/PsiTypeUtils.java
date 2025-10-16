package ru.vtb.nik.sap2u;
import com.intellij.psi.*;

public class PsiTypeUtils {

    public static String getCanonicalType(PsiType type) {
        if (type == null) return null;

        // Массивы
        if (type instanceof PsiArrayType arrayType) {
            PsiType component = arrayType.getComponentType();
            return getCanonicalType(component) + "[]";
        }

        // Generic (например List<Long>)
        if (type instanceof PsiClassType classType) {
            PsiClass resolved = classType.resolve();
            if (resolved != null) {
                PsiType[] params = classType.getParameters();
                if (params.length > 0) {
                    // Берём первый параметр
                    return getCanonicalType(params[0]);
                }

                // Примитивные обёртки или обычные классы
                String canonical = type.getCanonicalText();
                return primitiveToWrapper(canonical);
            }
        }

        // Wildcard типа <? extends Long> или <? super Integer>
        if (type instanceof PsiWildcardType wildcard) {
            PsiType bound = wildcard.getBound();
            if (bound != null) return getCanonicalType(bound);
        }

        // Примитив
        return primitiveToWrapper(type.getCanonicalText());
    }

    /**
     * Преобразует примитивы в их обёртки, остальные оставляет как есть
     */
    private static String primitiveToWrapper(String canonical) {
        return switch (canonical) {
            case "int" -> "java.lang.Integer";
            case "long" -> "java.lang.Long";
            case "short" -> "java.lang.Short";
            case "byte" -> "java.lang.Byte";
            case "boolean" -> "java.lang.Boolean";
            case "char" -> "java.lang.Character";
            case "float" -> "java.lang.Float";
            case "double" -> "java.lang.Double";
            default -> canonical;
        };
    }
}

