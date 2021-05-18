package hz.aspect;

import hz.Utils.JsonUtil;
import hz.annotation.LogFilter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hz
 * @date 2021/4/30
 */
@Aspect
@Slf4j
public class LogFilterAspect {
    @Before(value = "@annotation(hz.annotation.LogFilter)")
    public void before(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        //拿取参数key值
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        List<String> nameList =  Arrays.stream(method.getParameters()).map(Parameter::getName).collect(Collectors.toList());

        StringBuffer result = new StringBuffer();
        Object arg;
        for (int i = 0; i < nameList.size(); i++) {
            arg = args[i];
            if(arg == null){
                arg = "null";
            }else if(arg instanceof String){
                arg = String.valueOf(arg);
            }else{
                arg = JsonUtil.toJson(arg);
            }
            result.append(nameList.get(i)).append(":").append(arg);
            if (i != nameList.size()-1) {
                result.append(",");
            }
        }

        log.info("接口名称[{}],请求参数[{}]", method.getAnnotation(LogFilter.class).handler(), result.toString());
    }


}
