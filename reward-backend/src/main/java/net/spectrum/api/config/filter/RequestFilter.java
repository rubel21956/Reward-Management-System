package net.spectrum.api.config.filter;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.setAttribute("Request-Received-Time-In-Ms", String.valueOf(DateTime.now().getMillis()));
        if(Strings.isNullOrEmpty(httpRequest.getHeader("Trace-Id"))){
            httpRequest.setAttribute("Trace-Id", MDC.get("X-B3-TraceId"));
        }
        try {
            chain.doFilter(httpRequest, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

