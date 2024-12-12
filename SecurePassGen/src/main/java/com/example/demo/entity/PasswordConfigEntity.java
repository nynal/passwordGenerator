package com.example.demo.entity;

public record PasswordConfigEntity(
		int length,
		boolean includeSymbols,
		boolean includeLowercase,
		boolean includeUppercase,
		boolean includeNumbers,
		String customCharacters) {

}
