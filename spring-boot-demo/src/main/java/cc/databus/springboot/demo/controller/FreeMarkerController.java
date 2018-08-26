package cc.databus.springboot.demo.controller;

import cc.databus.springboot.demo.pojo.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ftl")
public class FreeMarkerController {

    @Autowired
    private Resource resource;

    @RequestMapping(value = "/center")
    public String center() {
        return "freemarker/center/center";
    }

    @RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("resource", resource);
        return "freemarker/index";
    }
}
