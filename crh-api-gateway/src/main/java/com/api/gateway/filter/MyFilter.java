package com.api.gateway.filter;

import com.common.service.util.ResponseBean;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import crh.token.api.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyFilter extends ZuulFilter {
    public static Logger log = LoggerFactory.getLogger(MyFilter.class);

    @Autowired
    private FilterConfig filterConfig;

    @Autowired
    private TokenService tokenService;


    @Override
    public String filterType() {
        /**
         * pre：可以在请求被路由之前调用
         * route：在路由请求时候被调用
         * post：在route和error过滤器之后被调用
         * error：处理请求时发生错误时被调用*/
        return "pre";
    }

    /*执行顺序 数字越大优先级越低*/
    @Override
    public int filterOrder() {
        return 0;
    }

    /*是否开启过滤器*/
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*过滤逻辑*/
    @Override
    public Object run() {
        String ig = filterConfig.getIgnores();
        String[] igs = ig.split(",");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        log.info(">>======log=======<<", String.format("%s request to%s"), request.getMethod(), request.getRequestURI());

        String token = request.getHeader("token");
        if (token == null || "null".equals(token)) {
            token = null;
        }
        boolean flag = false;
        for (int i = 0; i < igs.length; i++) {
            if (request.getRequestURL().toString().contains(igs[i])) {
                flag = true;
            }
        }
        if (!flag || token == null) {
            log.info(">>======log=======<<token 为空!");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("token is empty！");
        } else if (!flag && token != null) {

            ResponseBean responseBean = null;

            responseBean = tokenService.checkToken(token);
            if (!"success".equals(responseBean.getMsg())) {
                log.info(">>=====filter=====<<" + responseBean.getMsg());
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody(responseBean.getMsg());
            }

            log.info(">>======checkToken=====<<" + responseBean.getMsg());

        }
        log.info(">>======log=======<<验证通过");
        return null;
    }
}
