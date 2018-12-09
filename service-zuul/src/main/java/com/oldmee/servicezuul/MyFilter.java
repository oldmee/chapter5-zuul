package com.oldmee.servicezuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author R.oldmee
 * @date 2018/12/7 8:06 PM
 */
@Component
public class MyFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger("ZuulFilter");

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        logger.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));

        Object object = request.getParameter("token");
        if (object == null) {
            logger.warn("token is empty");
            requestContext.setSendZuulResponse(false);
            try {
                requestContext.getResponse().getWriter().write("token null");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        logger.info("OK");
        return null;
    }
}
