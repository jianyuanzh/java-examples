package cc.databus.springboot.demo.controller;

import cc.databus.springboot.demo.pojo.Resource;
import cc.databus.springboot.demo.pojo.JsonResponse;
import cc.databus.springboot.demo.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/")
public class HelloController {

    @Autowired
    Resource resource;

    @RequestMapping("/hello")
    public Object hello() {
        return "hello from spring boot";
    }

    @RequestMapping("/resource")
    public JsonResponse getResource() {
        Resource resource = new Resource();
        BeanUtils.copyProperties(this.resource, resource);
        return new JsonResponse(200, "OK", resource);
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public JsonResponse getUser() {
        User user = new User();
        user.setName("imoooc.cn");
        user.setAge(19);
        user.setBirthday(new Date());
        user.setPassword("1243ddsd");
        user.setDescription("test one");
        return new JsonResponse(200, "ok", user);
    }
}
