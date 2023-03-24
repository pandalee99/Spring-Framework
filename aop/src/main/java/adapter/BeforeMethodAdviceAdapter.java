package adapter;


import advisor.Advisor;
import advisor.BeforeMethodAdvice;
import interceptor.AopMethodInterceptor;
import interceptor.BeforeMethodAdviceInterceptor;

public class BeforeMethodAdviceAdapter implements AdviceAdapter{

    private BeforeMethodAdviceAdapter() {
    }

    private static final BeforeMethodAdviceAdapter INSTANTS = new BeforeMethodAdviceAdapter();

    public static BeforeMethodAdviceAdapter getInstants(){
        return INSTANTS;
    }

    @Override
    public AopMethodInterceptor getInterceptor(Advisor advisor) {
        BeforeMethodAdvice advice = (BeforeMethodAdvice) advisor.getAdvice();
        return new BeforeMethodAdviceInterceptor(advice);
    }
}
