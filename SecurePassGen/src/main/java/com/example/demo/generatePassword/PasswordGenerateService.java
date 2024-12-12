package com.example.demo.generatePassword;

import java.util.List;

import com.example.demo.entity.PasswordConfigEntity;
import com.example.demo.entity.PasswordEntity;

public interface PasswordGenerateService {

	List<PasswordEntity> generate(PasswordConfigEntity form);
}
