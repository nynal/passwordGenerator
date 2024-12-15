package com.example.demo.entity;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record PasswordEntity(
		String password) {
	private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";
	private static final String SYMBOLS = "-./:@[]_#&?";
	private static final SecureRandom random = new SecureRandom();

	public PasswordEntity(String password) {
		this.password = password;
	}

	// 設定にもとづいてパスワードを1個生成する
	public PasswordEntity generatePassword(PasswordConfigEntity config) {
		if(!checkConfig(config)) throw new IllegalArgumentException("チェックを1つ以上入れてください");
		String customCharacters = config.customCharacters();
		int targetLength = config.length();
		if(customCharacters.length() > targetLength) throw new IllegalArgumentException("カスタム文字列が長すぎます");
		
		StringBuilder newPassword = new StringBuilder();

		int upperTime = 0;
		int symbolTime = 0;
		int numberTime = 0;
		int totalLength = 0;
		int availableLength = targetLength - customCharacters.length();
		
		// 英小文字を含めない場合
		if (!config.includeLowercase()) {
			while (totalLength < availableLength) {
				if (config.includeNumbers() && totalLength < availableLength) {
					numberTime++;
					totalLength++;
				}
				if (config.includeSymbols() && totalLength < availableLength) {
					symbolTime++;
					totalLength++;
				}
				if (config.includeUppercase() && totalLength < availableLength) {
					upperTime++;
					totalLength++;
				}
			}
			// 指定の文字種をそれぞれの回数追加
			newPassword.append(generateRandomChar(numberTime, NUMBERS));
			newPassword.append(generateRandomChar(symbolTime, SYMBOLS));
			newPassword.append(generateRandomChar(upperTime, UPPERCASE));

		// 英小文字を含める場合
		} else {
			// 必須の文字種を1~2個のみ追加
			if (config.includeUppercase())
				newPassword.append(generateRandomChar(random.nextInt(1, 3), UPPERCASE));
			if (config.includeNumbers())
				newPassword.append(generateRandomChar(random.nextInt(1, 3), NUMBERS));
			if (config.includeSymbols())
				newPassword.append(generateRandomChar(random.nextInt(1, 3), SYMBOLS));
			// 余りを小英文字で埋める
			newPassword.append(generateRandomChar(availableLength - newPassword.length(), LOWERCASE));
		}
		
		//	パスワードをシャッフル
		StringBuilder shuffledPassword = shuffle(newPassword);

		// カスタム文字列があれば追加
		Optional.of(customCharacters).ifPresent(shuffledPassword::append);

		// 文字数確認
		if (checkLength(targetLength, shuffledPassword)) {
			return new PasswordEntity(shuffledPassword.toString());
		} else {
			throw new IllegalArgumentException("パスワード生成失敗。最大文字列の長さを増やしてください");
		}
	}

	// characterSetからランダムな文字を指定数生成する
	private String generateRandomChar(int additionalTimes, String characterSet) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < additionalTimes; i++) {
			text.append(String.valueOf(characterSet.charAt(random.nextInt(characterSet.length()))));
		}
		return text.toString();
	}

	// 文字列をシャッフルする
	private StringBuilder shuffle(StringBuilder password) {
		List<Character> characters = new ArrayList<>();
		for (char c : password.toString().toCharArray()) {
			characters.add(c);
		}
		Collections.shuffle(characters);

		StringBuilder shuffledPassword = new StringBuilder();
		for (char c : characters) {
			shuffledPassword.append(c);
		}

		return shuffledPassword;
	}
	
	private boolean checkConfig(PasswordConfigEntity config) {
		return config.includeLowercase() || config.includeUppercase() || 
					 config.includeNumbers() || config.includeSymbols();
	}

	// 指定された長さのパスワードかチェックする
	private boolean checkLength(int length, StringBuilder password) {
		if (password.length() == length)
			return true;
		return false;
	}

}
