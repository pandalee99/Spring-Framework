package advisor;
import lombok.Data;


@Data
public class Advisor {
    //干什么
    private Advice advice;
    //在哪里
    private Pointcut pointcut;

}
