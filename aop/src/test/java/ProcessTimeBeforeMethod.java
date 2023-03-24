import advisor.BeforeMethodAdvice;

import java.lang.reflect.Method;
import java.util.UUID;

public class ProcessTimeBeforeMethod implements BeforeMethodAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("打印随机数值: "+ UUID.randomUUID());
    }
}
