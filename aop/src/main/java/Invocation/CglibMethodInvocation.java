package Invocation;


import interceptor.AopMethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;
import java.util.List;

public class CglibMethodInvocation extends ReflectioveMethodeInvocation {

    private final MethodProxy methodProxy;

    public CglibMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, List<AopMethodInterceptor> interceptorList, MethodProxy methodProxy) {
        super(proxy, target, method, arguments, interceptorList);
        this.methodProxy = methodProxy;
    }

    @Override
    protected Object invokeOriginal() throws Throwable {
        return methodProxy.invoke(target,arguments);
    }
}
