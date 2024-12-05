package com.example.demo.generatePasswordController;

public record PasswordConfigEntity(
		int length,
		boolean includeSymbols,
		boolean includeLowercase,
		boolean includeUppercase,
		boolean includeNumbers,
		String customCharacters) {

}
