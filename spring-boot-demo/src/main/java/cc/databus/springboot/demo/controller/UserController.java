package cc.databus.springboot.demo.controller;

import cc.databus.springboot.demo.pojo.JsonResponse;
import cc.databus.springboot.demo.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/user")
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
