package com.example.demo.generatePasswordController;

import java.util.List;

public interface PasswordGenerateService {

	List<PasswordEntity> generate(PasswordConfigEntity form);
}
