package adapter;


import advisor.Advisor;
import interceptor.AopMethodInterceptor;

public interface AdviceAdapter {

    AopMethodInterceptor getInterceptor(Advisor advisor);
}
