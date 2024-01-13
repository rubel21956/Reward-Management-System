package net.spectrum.api.config.filter;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Component
@Slf4j
public class AddResponseHeaderFilter  extends OncePerRequestFilter {
    Long time;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse=(HttpServletResponse)response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        httpServletResponse.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
        httpServletResponse.setHeader("Request-Id", String.valueOf(httpRequest.getHeader("Request-Id")));
        httpServletResponse.setHeader("Request-Received-Time", String.valueOf(httpRequest.getHeader("Request-Time")));
        String traceId = httpRequest.getHeader("Trace-Id");
        if(Strings.isNullOrEmpty(traceId)){
            traceId = String.valueOf(httpRequest.getAttribute("Trace-Id"));
        }
        httpServletResponse.setHeader("Trace-Id", traceId);
        String receiveTime = String.valueOf(httpRequest.getAttribute("Request-Received-Time-In-Ms"));
        time = DateTime.now().getMillis() - Long.parseLong(receiveTime);
        httpServletResponse.setHeader("Response-Processing-Time-In-Ms", String.valueOf(time));
        httpServletResponse.setHeader("Response-Time", DateTime.now().toString("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"));

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(httpServletResponse) {
            @Override
            public void setStatus(int sc) {
                super.setStatus(sc);
                handleStatus(sc);
            }
            @Override
            @SuppressWarnings("deprecation")
            public void setStatus(int sc, String sm) {
                super.setStatus(sc, sm);
                handleStatus(sc);
            }
            @Override
            public void sendError(int sc, String msg) throws IOException {
                super.sendError(sc, msg);
                handleStatus(sc);
            }
            @Override
            public void sendError(int sc) throws IOException {
                super.sendError(sc);
                handleStatus(sc);
            }
            private void handleStatus(int code) {
                if(code != 0)
                {
                    time = DateTime.now().getMillis() - Long.parseLong(receiveTime);
                    httpServletResponse.setHeader("Response-Processing-Time-In-Ms", String.valueOf(time));
                    httpServletResponse.setHeader("Response-Time", DateTime.now().toString("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"));
                }
            }
        };

        chain.doFilter(httpRequest, wrapper);

        log.info("Response processing time for {} is {} ms",request.getRequestURI(), time);
    }
}
