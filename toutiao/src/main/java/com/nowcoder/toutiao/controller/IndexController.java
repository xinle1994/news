package com.nowcoder.toutiao.controller;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowcoder.model.User;
import com.nowcoder.toutiao.dao.UserDAO;

/**
 *
 */



@Controller
public class IndexController {
	
	
	   @Autowired
	    UserDAO userDAO;

//    @RequestMapping(path = {"/index"})
//    @ResponseBody
    public String index(){
    	
    	
        return "hello nowcoder";

    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type",defaultValue = "1")int type,
                          @RequestParam(value = "key",defaultValue = "nowcoder")String key){
        return  "";
    }
}
