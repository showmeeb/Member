package com.example.member.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.member.model.UserInfo;
import com.example.member.model.service.MemberService;

@RestController
@SuppressWarnings("rawtypes")
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@GetMapping(path = "/api/user")
	public ResponseEntity getUserInfoList() {
		logger.info("getUserInfoListController");
		List<UserInfo> resultList = memberService.getUserInfoList();

		if (resultList.isEmpty()) {
			return getErrorResponse("no data");
		}

		return getResponse(resultList);
	}

	@GetMapping(path = "/api/user/{userId}")
	public ResponseEntity getUserInfo(@PathVariable Integer userId) {
		logger.info("getUserInfoController");
		UserInfo result = memberService.getUserInfo(userId);

		if (result != null) {
			return getResponse(result);
		}

		return getErrorResponse("empty userId");
	}

	@PostMapping(path = "/api/user")
	public ResponseEntity insertUserInfo(@RequestBody UserInfo userInfo) {
		logger.info("insertUserInfo - {}", userInfo);
		if (isUserInfoValid(userInfo)) {
			memberService.insert(userInfo);
		} else {
			return getErrorResponse("please check input data");
		}
		
		return getResponse(Collections.singletonMap("status", "insert success"));
	}
	
	@PutMapping(path = "/api/user/{userId}")
	public ResponseEntity updateUserInfo(@RequestBody UserInfo userInfo, @PathVariable Integer userId) {
		logger.info("updateUserInfo - {}", userInfo);
		if(isUserInfoValid(userInfo)) {
			memberService.update(userInfo, userId);
		}else {
			return getErrorResponse("plaese check input data");
		}
		
		return getResponse(Collections.singletonMap("status", "update " + userInfo.getAccount() + " success"));
	}
	
	@DeleteMapping(path = "/api/user/{userId}")
	public ResponseEntity deleteUserInfo(@PathVariable Integer userId) {
		logger.info("deleteUserInfo - {}", userId);
		memberService.delete(userId);
		
		return getResponse(Collections.singletonMap("status", "delete " + userId + " success"));
	}

	private boolean isUserInfoValid(UserInfo userInfo) {
		return StringUtils.hasText(userInfo.getAccount()) && StringUtils.hasText(userInfo.getPassword());
	}

	private ResponseEntity getResponse(Object body) {
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	private ResponseEntity getErrorResponse(String errorMsg) {
		return new ResponseEntity<>(Collections.singletonMap("errorMsg", errorMsg), HttpStatus.BAD_REQUEST);
	}
}
