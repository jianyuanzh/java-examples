package cc.databus.springboot.demo.controller;

import cc.databus.springboot.demo.pojo.JsonResponse;
import cc.databus.springboot.demo.pojo.SysUser;
import cc.databus.springboot.demo.pojo.User;
import cc.databus.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

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

    @RequestMapping("/saveUser")
    public JsonResponse saveUser() {
        SysUser sysUser = new SysUser();
        sysUser.setId("" + System.nanoTime());
        sysUser.setNickname("lee" + System.currentTimeMillis() );
        sysUser.setUsername("lee" + System.currentTimeMillis());
        sysUser.setPassword("dfagda");
        sysUser.setAge(18);
        sysUser.setFaceImage("user/dfdg/");
        sysUser.setJob(1);
        sysUser.setSex(2);

        userService.saveUser(sysUser);

        return new JsonResponse(200, "OK", userService.queryUserByIdCustom(sysUser.getId()));
    }

    @RequestMapping("/query")
    public JsonResponse query(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        SysUser user = new SysUser();
        user.setNickname("lee");
        return new JsonResponse(200, "OK", userService.queryUserListPaged(user, page, pageSize));
    }
}
