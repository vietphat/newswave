package com.vietphat.newswave.config;

import com.vietphat.newswave.enums.UserRole;
import com.vietphat.newswave.utils.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.List;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        List<String> authorities = SecurityUtils.getAuthorities();
        String targetUrl = determineTargetUrl(authorities);

        if (response.isCommitted()) {
            System.out.println("Can not redirect");
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    public String determineTargetUrl(List<String> authorities) {
        // TODO: redirect dựa theo quyền
        // ROLE_ADMIN, ROLE_AUTHOR -> dashboard
        // ROLE_USER -> homepage

        String url = "";

        if (authorities.contains("ROLE_" + UserRole.SUPER_ADMIN)) {
            url = "/quan-tri";
        } else if (authorities.contains("ROLE_" + UserRole.USER)) {
            url = "/trang-chu";
        }

        return url;
    }

    @Override
    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
