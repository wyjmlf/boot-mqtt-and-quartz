package com.zh.wx;

import com.zh.util.CheckUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping(value = "/wxCheck")
public class WxCheck {


    //用户开发者配置中的路径，微信会验证token。是get请求
    @RequestMapping(value="/weixin",method=RequestMethod.GET)
    public void weixin(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        System.out.println(signature+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        PrintWriter pw = response.getWriter();
        boolean bool= CheckUtil.checkSignature(signature, timestamp, nonce);
        if(bool) {
            pw.append(echostr);
            pw.flush();
        }else {
            pw.append("123");
            pw.flush();
        }

    }
}
