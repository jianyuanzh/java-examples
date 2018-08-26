package cc.databus.springboot.demo.controller;

import cc.databus.springboot.demo.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("tmf")
public class ThymeleafController {

    @RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("name", "thymeleaf test");
        return "thymeleaf/index";
    }

    @RequestMapping("/center")
    public String center() {
        return "thymeleaf/center/center";
    }

    @RequestMapping("/user")
    public String testUser(ModelMap modelMap) {
        User user = new User();
        user.setName("superadmin");
        user.setAge(19);
        user.setBirthday(new Date());
        user.setPassword("1243ddsd");
        user.setDescription("<font color='green'> <b>hello vincent</b></font>");

        modelMap.addAttribute("user", user);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        User u1 = new User();
        u1.setName("vincent");
        u1.setAge(27);
        u1.setBirthday(new Date());
        u1.setPassword("412dfs");
        userList.add(u1);
        modelMap.addAttribute("userList", userList);
        return "thymeleaf/user";
    }

    @RequestMapping("/postform")
    public String postform(User u) {
        System.out.println(u.getName());
        System.out.println(u.getAge());
        return "redirect:/tmf/user";
    }
}
