package org.mi.web.controller.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

   @RequestMapping("/index")
   public String index(HttpServletRequest request, Map<String, Object> map) {
		return "redirect:/module/security/index.html";
    }
	
	@RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response, 
    		Map<String, Object> model) throws IOException{
		
	        return "login";
    }
}
