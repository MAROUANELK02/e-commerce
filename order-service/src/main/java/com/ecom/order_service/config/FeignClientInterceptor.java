package com.ecom.order_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private HttpServletRequest request;

    @Override
    public void apply(RequestTemplate template) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token != null) {
            template.header(AUTHORIZATION_HEADER, token);
        }
    }
}
