package com.leslie.springbootrestserver;


/* a little REST service that tests the Application start */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestService {

    public static final Logger logger = LoggerFactory.getLogger(RestService.class);

    @GetMapping(value="/")
    public ResponseEntity<String> pong(){
        logger.info("Starting of services ok .....");
        return new ResponseEntity<String>("Server Response : " + HttpStatus.OK.name(),HttpStatus.OK);
    }
}
