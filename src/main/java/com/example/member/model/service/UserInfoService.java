package com.example.member.model.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.member.exception.MemberException;
import com.example.member.model.UserInfo;
import com.example.member.model.mapper.UserInfoRepo;


@Service
@Transactional
public class UserInfoService {

	@Autowired
	private UserInfoRepo memberRepo;

	public List<UserInfo> getUserInfoList() {
		return memberRepo.selectList(new QueryWrapper<>());
	}

	public UserInfo getUserInfo(Integer userId) {
		return memberRepo.selectById(userId);
	}

	public boolean isUserInfoDataValid(UserInfo userInfo) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("account", userInfo.getAccount());
		return memberRepo.selectByMap(paramMap).isEmpty();
	}
	
	public boolean isUserInfoDataExist(Integer userId) {
		return memberRepo.selectById(userId) == null;
	}

	public void insert(UserInfo userInfo) {

		if (isUserInfoDataValid(userInfo)) {
			userInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			memberRepo.insert(userInfo);
		}else {
			throw new MemberException("100", "insert error");
		}
	}

	public void update(UserInfo userInfo, Integer userId) {

		if (!isUserInfoDataExist(userId)) {
			userInfo.setId(userId);
			userInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			memberRepo.updateById(userInfo);
		}else {
			throw new MemberException("101", "no data to update");
		}
	}

	public void delete(Integer userId) {

		memberRepo.deleteById(userId);
	}
}
