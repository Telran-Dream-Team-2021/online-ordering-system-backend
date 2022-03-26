package telran.exceptions.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import telran.exceptions.dto.ResponseDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Component
public class SecurityExceptionsHandler implements AccessDeniedHandler, AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(SecurityExceptionsHandler.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
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
                         AuthenticationException authException) throws IOException, ServletException {
        String message = String.format("Authentication error %s", request.getRequestURL());

        log.error(message);
        response.setHeader("Content-type", "application/json");
        response.getWriter().println(objectMapper.writeValueAsString(new ResponseDto(message)));
        response.setStatus(401);
    }
}
