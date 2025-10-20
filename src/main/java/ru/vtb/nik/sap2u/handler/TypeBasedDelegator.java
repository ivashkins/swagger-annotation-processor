package ru.vtb.nik.sap2u.handler;

import com.intellij.psi.PsiNamedElement;
import ru.vtb.nik.sap2u.handler.types.BigDecimalHandler;
import ru.vtb.nik.sap2u.handler.types.CommonHandler;
import ru.vtb.nik.sap2u.handler.types.IntegerHandler;
import ru.vtb.nik.sap2u.handler.types.LongHandler;
import ru.vtb.nik.sap2u.handler.types.StringHandler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for delegate work to handler who processed this class type
 */
public class TypeBasedDelegator  {
    private final Map<String, ClassAnnotationsHandler> handlers = new HashMap<>();
    private final ClassAnnotationsHandler defaultHandler = new CommonHandler();


    public TypeBasedDelegator() {
        handlers.put(BigDecimal.class.getCanonicalName(), new BigDecimalHandler());
        handlers.put(Integer.class.getCanonicalName(), new IntegerHandler());
        handlers.put(Long.class.getCanonicalName(), new LongHandler());
        handlers.put(String.class.getCanonicalName(), new StringHandler());
    }

    public void handle(String type, PsiNamedElement field, boolean isList) {
        handlers.getOrDefault(type, defaultHandler).fillAnnotations(field, isList);
    }

}
