package telran.oos.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import telran.oos.aop.dto.WebSocketMessageDto;
import telran.oos.aop.inter.Idable;
import telran.oos.aop.inter.WebSocketMessagable;

/**
 * To add your service to messaging follow next steps:
 * 1. Add implementation of "Idable" interface to your Dto
 * 2. Add implementation of "WebSocketMessagable" interface to your service
 */
@Slf4j
@Service
@Aspect
public class ProductPushNotificationWebSocketAspect {
    private SimpMessagingTemplate smt;
    public ProductPushNotificationWebSocketAspect(SimpMessagingTemplate smt) {
        this.smt = smt;
    }

    @Around("execution(* telran.oos.service..create(..))")
    Object sendCreateMessage(ProceedingJoinPoint jp) throws Throwable {
        log.debug("Advice CREATE intercepted method");
        String theme = getTheme(jp);
        return proceed(jp, theme, "created");
    }

    @Around("execution(* telran.oos.service..update(..))")
    Object sendUpdateMessage(ProceedingJoinPoint jp) throws Throwable {
        log.debug("Advice UPDATE intercepted method");
        String theme = getTheme(jp);
        return proceed(jp, theme, "updated");
    }

    @Around("execution(* telran.oos.service..remove(..))")
    Object sendRemoveMessage(ProceedingJoinPoint jp) throws Throwable {
        log.debug("Advice REMOVE intercepted method");
        String theme = getTheme(jp);
        return proceed(jp, theme, "removed");
    }

    private String getTheme(ProceedingJoinPoint jp) {
        return ((WebSocketMessagable) jp.getThis()).getTheme();
    }

    private Object proceed(ProceedingJoinPoint jp, String theme, String action) throws Throwable {
        Idable entityDto = (Idable) jp.proceed();
        WebSocketMessageDto message =  new WebSocketMessageDto(action, entityDto.getId());
        smt.convertAndSend(theme, message);
        log.debug("message {} on theme {} sent via web socket",message, theme);
        return entityDto;
    }

}
