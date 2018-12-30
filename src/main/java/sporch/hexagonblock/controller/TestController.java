package sporch.hexagonblock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping(value = "/",method = RequestMethod.GET,params = {"id","name"})
    public String test(@RequestParam String id){
        System.out.println("id->"+id);
        return "hello world";
    }
}
