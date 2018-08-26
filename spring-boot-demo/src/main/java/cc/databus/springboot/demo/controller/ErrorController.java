package cc.databus.springboot.demo.controller;

import cc.databus.springboot.demo.pojo.JsonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("err")
public class ErrorController {
    @RequestMapping("/error")
    public int error() {
        int a = 1/0;
        return a;
    }

    @RequestMapping("/getAjaxerror")
    public @ResponseBody  JsonResponse getAjaxError() {
        int a = 1/0;
        return new JsonResponse(200, "OK", "");
    }

    @RequestMapping("/ajaxerror")
    public String ajaxerror() {
        return "thymeleaf/ajaxerror";
    }
}
