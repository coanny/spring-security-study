package com.example.springsecuritystudy.form;

import com.example.springsecuritystudy.common.SecurityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.security.Security;
import java.util.concurrent.Callable;

@Controller
public class SampleController {

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping(path = "/")
    public String index(Model model, Principal principal) {
        if(principal == null) {
            model.addAttribute("message", "hello spring security");
        } else {
            model.addAttribute("message", "hello " + principal.getName());
        }

        return "index";
    }

    @GetMapping(path = "/info")
    public String info(Model model) {
        model.addAttribute("message", "info");
        return "info";
    }

    @GetMapping(path = "/dashboard")
    public String dashboard(Model model, Principal principal) {
        sampleService.dashboard();
        model.addAttribute("message", "hello dashboard " +principal.getName());
        return "dashboard";
    }

    @GetMapping(path = "/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "hello admin " + principal.getName());
        return "admin";
    }

    @GetMapping(path = "/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("message", "hello user " + principal.getName());
        return "admin";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");
        return () -> {
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC, before async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");
        return "Async Service";
    }
}
