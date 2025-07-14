//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.interceptor;

import com.bohemian.intellect.service.JwtService;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;

    public AuthHandshakeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletServerHttpRequest) {
            String token = servletServerHttpRequest.getServletRequest().getParameter("token");
            if (token != null && !this.jwtService.isTokenExpired(token)) {
                String username = this.jwtService.extractUsername(token);
                attributes.put("user", new UsernamePasswordAuthenticationToken(username, (Object)null));
            }
        }

        return true;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
