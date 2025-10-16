package ru.vtb.nik.sap2u.classes;

import com.intellij.psi.PsiField;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TypeBasedDelegator  {
    private final Map<String, ClassAnnotationsHandler> handlers = new HashMap<>();
    private final ClassAnnotationsHandler defaultHandler = new CommonHandler();


    public TypeBasedDelegator() {
        handlers.put(BigDecimal.class.getCanonicalName(), new BigDecimalHandler());
        handlers.put(Integer.class.getCanonicalName(), new IntegerHandler());
        handlers.put(Long.class.getCanonicalName(), new LongHandler());
        handlers.put(String.class.getCanonicalName(), new StringHandler());
    }

    public void handle(String type, PsiField field, boolean isList) {
        handlers.getOrDefault(type, defaultHandler).fillAnnotations(field, isList);
    }

}
