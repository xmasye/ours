package com.xmasye.ours.util;

import com.xmasye.ours.constance.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    public ResponseUtil() {
    }

    public static void setAllowOrigin(HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.trim().equals("") && !referer.startsWith("https://")) {
            String requestDomain = "";
            if (referer.startsWith("http://") && referer.indexOf("/", 7) > 0) {
                requestDomain = referer.substring(0, referer.indexOf("/", 7));
            } else {
                requestDomain = referer;
            }

            response.setHeader("Access-Control-Allow-Origin", requestDomain);
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
    }

    public static void setNoCache(HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
    }

    public static void setHtmlHeader(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
    }

    public static void sendRedirect(HttpServletResponse response, String url) {
        if (response != null && !StringUtils.isEmpty(url)) {
            try {
                response.sendRedirect(url);
            } catch (Exception var3) {
                logger.error("错误", var3);
            }

        }
    }

    public static void write(HttpServletResponse response, String content) {
        if (response != null && !StringUtils.isEmpty(content)) {
            PrintWriter out = null;

            try {
                out = response.getWriter();
                out.print(content);
                out.flush();
            } catch (Exception var7) {
                logger.error("错误", var7);
            } finally {
                if (out != null) {
                    out.close();
                }

            }

        }
    }

    public static void renderText(HttpServletResponse response, String text) {
        setNoCache(response);
        setHtmlHeader(response);
        write(response, text);
    }

    public static void renderXml(HttpServletResponse response, String xml) {
        setNoCache(response);
        response.setContentType("text/xml;charset=UTF-8");
        write(response, xml);
    }

    public static void renderJson(HttpServletResponse response, String json) {
        setNoCache(response);
        response.setContentType("application/json;charset=UTF-8");
        write(response, json);
    }

    public static void renderResult(HttpServletResponse response, ApiResult result) {
        renderJson(response, JsonUtil.toJSONString(result));
    }

    public static void renderStream(HttpServletRequest request, HttpServletResponse response, byte[] bytes) {
        setNoCache(response);
        response.setContentType("application/octet-stream;charset=UTF-8");
        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception var13) {
            logger.error("错误", var13);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception var12) {
                    ;
                }
            }

        }

    }

}
