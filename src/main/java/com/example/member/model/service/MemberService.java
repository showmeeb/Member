package com.example.member.model.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.member.exception.DemoException;
import com.example.member.model.UserInfo;
import com.example.member.model.mapper.MemberRepo;


@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepo memberRepo;

	public List<UserInfo> getUserInfoList() {
		return memberRepo.selectList(new QueryWrapper<>());
	}

	public UserInfo getUserInfo(Integer id) {
		return memberRepo.selectById(id);
	}

	public boolean isUserInfoDataValid(UserInfo userInfo) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("account", userInfo.getAccount());
		return memberRepo.selectByMap(paramMap).isEmpty();
	}
	
	public boolean isUserInfoDataExist(Integer id) {
		return memberRepo.selectById(id) == null;
	}

	public void insert(UserInfo userInfo) {

		if (isUserInfoDataValid(userInfo)) {
			Timestamp currentTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			userInfo.setBuildTime(currentTime);
			memberRepo.insert(userInfo);
		}else {
			throw new DemoException("100", "insert error");
		}
	}

	public void update(UserInfo userInfo, Integer id) {

		if (!isUserInfoDataExist(id)) {
			Timestamp currentTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			userInfo.setId(id);
			userInfo.setUpdateTime(currentTime);
			memberRepo.updateById(userInfo);
		}else {
			throw new DemoException("101", "no data to update");
		}
	}

	public void delete(Integer id) {

		memberRepo.deleteById(id);
	}
}
