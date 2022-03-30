package telran.oos.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import telran.oos.aop.dto.WebSocketMessageDto;
import telran.oos.api.dto.ProductDto;

import static telran.oos.api.ApiConstants.*;

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
        String theme = getTheme(jp);
        return proceed(jp, theme, "created");
    }

    private String getTheme(ProceedingJoinPoint jp) {
        String serviceClassName = jp.getSignature().getDeclaringType().getSimpleName();
        switch (serviceClassName) {
            case "ProductServiceImpl" -> { return WEBSOCKET_PRODUCT_THEME; }
            default -> { return null; }
        }
    }

    private Object proceed(ProceedingJoinPoint jp, String theme, String action) throws Throwable {
        DtoType DtoType = getDtoType(jp);
        switch (DtoType) {
            case PRODUCT -> {
                ProductDto productDto = (ProductDto) jp.proceed();
                WebSocketMessageDto message =  new WebSocketMessageDto(action, productDto.getId());
                smt.convertAndSend(theme,message);
                return productDto;
            }
            default -> { return null;}
        }
    }

    private DtoType getDtoType(ProceedingJoinPoint jp) {
        String serviceClassName = jp.getSignature().getDeclaringType().getSimpleName();
        switch (serviceClassName) {
            case "ProductServiceImpl" -> { return DtoType.PRODUCT; }
            default -> { return null; }
        }
    }
}
