package interceptor;


import Invocation.MethodInvocation;
import advisor.BeforeMethodAdvice;

public class BeforeMethodAdviceInterceptor implements AopMethodInterceptor {

    private BeforeMethodAdvice advice;

    public BeforeMethodAdviceInterceptor(BeforeMethodAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        advice.before(mi.getMethod(),mi.getArguments(),mi);
        return mi.proceed();
    }
}
