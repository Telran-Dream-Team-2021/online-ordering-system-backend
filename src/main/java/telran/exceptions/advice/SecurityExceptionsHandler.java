package telran.exceptions.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import telran.exceptions.dto.ResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@Component
public class SecurityExceptionsHandler implements AccessDeniedHandler, AuthenticationEntryPoint {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        Principal principal = request.getUserPrincipal();
        String message = String.format("User %s disallowed to run request %s", principal.getName(),
                request.getRequestURL());

        log.error(message);
        response.setHeader("Content-type", "application/json");
        response.getWriter().println(objectMapper.writeValueAsString(new ResponseDto(message)));
        response.setStatus(403);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String message = String.format("Authentication error %s", request.getRequestURL());

        log.error(message);
        response.setHeader("Content-type", "application/json");
        response.getWriter().println(objectMapper.writeValueAsString(new ResponseDto(message)));
        response.setStatus(401);
    }
}
