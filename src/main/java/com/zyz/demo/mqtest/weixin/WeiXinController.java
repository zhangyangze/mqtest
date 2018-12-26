package com.zyz.demo.mqtest.weixin;/**
 * Created by zhangyangze on 2018/12/25
 */

import com.zyz.demo.mqtest.weixin.utils.SecurityKit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * class name WeiXinController
 *
 * @author zhang yang ze
 * @date 2018/12/25
 */
@Controller
public class WeiXinController {

    public static String TONKEN = "zhichengda";

    @RequestMapping("/wget")
    public void wget(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        String[] arr = {WeiXinController.TONKEN,timestamp,nonce};
        Arrays.sort(arr);
        StringBuffer stringBuffer = new StringBuffer();
        for(String val : arr){
            stringBuffer.append(val);
        }
        String sha1Msg = SecurityKit.sha1(stringBuffer.toString());
        if(sha1Msg.equals(signature)){
            res.getWriter().println(echostr);
        }


    }

}
