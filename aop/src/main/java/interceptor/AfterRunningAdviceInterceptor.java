package interceptor;

import Invocation.MethodInvocation;
import advisor.AfterRunningAdvice;



public class AfterRunningAdviceInterceptor implements AopMethodInterceptor {

    private AfterRunningAdvice advice;

    public AfterRunningAdviceInterceptor(AfterRunningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object returnVal = mi.proceed();
        advice.after(returnVal,mi.getMethod(),mi.getArguments(),mi);
        return returnVal;
    }
}
