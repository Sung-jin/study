package DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
    private Object target;

    UppercaseHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);

        if(ret instanceof String && method.getName().endsWith("Hi")) {
            return ((String)ret).toUpperCase();
        } else {
            return ret;
        }
    }
}
