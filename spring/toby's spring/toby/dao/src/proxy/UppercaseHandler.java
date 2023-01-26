package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
//    Hello target;
//
//    public UppercaseHandler(Hello target) {
//        this.target = target;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        String ret = (String)method.invoke(target, args);
//        return ret.toUpperCase(); // 부가기능 제공
//    }

    Object target;
    private UppercaseHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);

        if (ret instanceof String) {
            return ((String)ret).toUpperCase();
        }
        return ret;
    }
}
