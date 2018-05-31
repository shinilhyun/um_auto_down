package com.enjoybt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 0. Project  : 화산재해대응시스템
 * 1. Package : com.enjoybt.controller
 * 2. Comment : 
 * 3. Auth  : aiden_shin
 * 4. Date  : 2018-05-31 오후 4:31
 * 5. History : 
 * 이름     : 일자          : 근거자료   : 변경내용
 * ------------------------------------------------------
 * aiden_shin : 2018-05-31 :            : 신규 개발.
 *
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home(){
        return "home";
    }
}
