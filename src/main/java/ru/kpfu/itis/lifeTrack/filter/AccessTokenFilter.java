package ru.kpfu.itis.lifeTrack.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kpfu.itis.lifeTrack.security.jwt.SecurityUserDetails;
import ru.kpfu.itis.lifeTrack.security.jwt.SecurityUserDetailsService;
import ru.kpfu.itis.lifeTrack.security.jwt.JwtHelper;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessTokenFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final SecurityUserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> accessToken = parseAccessToken(request);
            if (accessToken.isPresent() && jwtHelper.validateAccessToken(accessToken.get())) {
                String userId = jwtHelper.getUserIdFromAccessToken(accessToken.get());
                SecurityUserDetails userDetails = userService.findById(userId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("Just logged in user with userId {}", userId);
            }
        } catch (Exception e) {
            log.error("Cannot set authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> parseAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.replace("Bearer ", ""));
        }
        return Optional.empty();
    }
}
