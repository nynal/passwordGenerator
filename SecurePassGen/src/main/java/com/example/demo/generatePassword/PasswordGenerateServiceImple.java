package com.example.demo.generatePassword;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PasswordConfigEntity;
import com.example.demo.entity.PasswordEntity;

@Service
public class PasswordGenerateServiceImple implements PasswordGenerateService {

	@Override
	public List<PasswordEntity> generate(PasswordConfigEntity config) {
		List<PasswordEntity> passwordList = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			passwordList.add(new PasswordEntity("").generatePassword(config));
		}
		return passwordList;
	}
}
