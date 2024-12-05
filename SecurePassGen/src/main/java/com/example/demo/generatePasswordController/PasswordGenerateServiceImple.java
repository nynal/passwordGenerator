package com.example.demo.generatePasswordController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
