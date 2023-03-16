package dodam.b1nd.dgit.global.interceptor;

import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.global.annotation.AuthCheck;
import dodam.b1nd.dgit.global.lib.jwt.JwtType;
import dodam.b1nd.dgit.global.lib.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (!(handlerMethod.getMethod().isAnnotationPresent(AuthCheck.class))) {
            return true;
        }

        try {
            String token = getTokenOfRequest(request).split(" ")[1];
            User user = jwtUtil.getUserByToken(token);

            request.setAttribute("user", user);

        } catch (Exception e) {
            e.getMessage();
        }

        return true;
    }

    private String getTokenOfRequest(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");

        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value != null) {
                return value;
            }
        }

        return Strings.EMPTY;
    }
}