package adapter;


import advisor.Advisor;
import advisor.AfterRunningAdvice;
import interceptor.AfterRunningAdviceInterceptor;
import interceptor.AopMethodInterceptor;

public class AfterRunningAdviceAdapter implements AdviceAdapter{

    private AfterRunningAdviceAdapter(){

    }

    private static final AfterRunningAdviceAdapter INSTANTS = new AfterRunningAdviceAdapter();

    public static AfterRunningAdviceAdapter getInstants(){
        return INSTANTS;
    }

    @Override
    public AopMethodInterceptor getInterceptor(Advisor advisor) {
        AfterRunningAdvice advice = (AfterRunningAdvice) advisor.getAdvice();
        return new AfterRunningAdviceInterceptor(advice);
    }
}
