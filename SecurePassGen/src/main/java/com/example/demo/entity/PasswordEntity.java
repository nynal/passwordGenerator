package com.example.demo.entity;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record PasswordEntity(
		String password
) {
	private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";
	private static final String SYMBOLS = "-./:@[]_#&?";
	private static final SecureRandom random = new SecureRandom();

	public PasswordEntity(String password) {
		this.password = password;
	}

	// パスワードを1個生成する
	public PasswordEntity generatePassword(PasswordConfigEntity config) {
		int targetLength = config.length();
		StringBuilder newPassword = new StringBuilder();

		// 必須の文字種を追加
		if (config.includeUppercase())
			newPassword.append(generateRandomChar(random.nextInt(1, 3), UPPERCASE));
		if (config.includeNumbers())
			newPassword.append(generateRandomChar(random.nextInt(1, 3), NUMBERS));
		if (config.includeSymbols())
			newPassword.append(generateRandomChar(random.nextInt(1, 3), SYMBOLS));

		// 指定文字列を挿入すると文字数オーバーする場合
		String customCharacters = config.customCharacters();
		if (targetLength < newPassword.length() + customCharacters.length()) {
			throw new IllegalArgumentException(
					"生成するパスワードの長さが不足しています。指定文字数を見直してください。");
		}

		// 余りを英小文字で埋める
		newPassword.append(generateRandomChar(
				targetLength - newPassword.length() - customCharacters.length(), LOWERCASE));
		
		//		パスワードをシャッフル
		StringBuilder shuffledPassword = shuffle(newPassword);
		
		// 指定文字列があれば追加
		Optional.of(customCharacters).ifPresent(shuffledPassword::append);

		return new PasswordEntity(shuffledPassword.toString());
	}

	// characterSetからランダムな文字を指定数生成する
	private String generateRandomChar(int additionalTimes, String characterSet) {
		StringBuilder text = new StringBuilder();

		for (int i = 0; i < additionalTimes; i++) {
			text.append(String.valueOf(characterSet.charAt(random.nextInt(characterSet.length()))));
		}
		return text.toString();
	}

	private StringBuilder shuffle(StringBuilder password) {
		// 文字列をリストに変換
		List<Character> characters = new ArrayList<>();
		for (char c : password.toString().toCharArray()) {
			characters.add(c);
		}
		// リストをシャッフル
		Collections.shuffle(characters);

		// リストを文字列に戻す
		StringBuilder shuffledPassword = new StringBuilder();
		for (char c : characters) {
			shuffledPassword.append(c);
		}

		return shuffledPassword;
	}

}
