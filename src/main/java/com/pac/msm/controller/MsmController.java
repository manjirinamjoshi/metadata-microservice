package com.pac.msm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msm")
public class MsmController {

	@RequestMapping("/hi")
	@ResponseBody
	String echo() throws Exception {
		return "MSM service is running.";
	}

}