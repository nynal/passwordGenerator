package com.example.demo.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.entity.PasswordConfigEntity;

public class PasswordUtils {
	private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";
	private static final String SYMBOLS = "-./:@[]_#&?";
	private static final SecureRandom random = new SecureRandom();

	// パスワードを生成する際の設定が妥当か調べる
	// configの文字種が1つも指定されていない場合falseを返す
	public static boolean isConfigValid(final PasswordConfigEntity config) {
		return config.includeLowercase() || config.includeUppercase() ||
				config.includeNumbers() || config.includeSymbols();
	}
//	パスワードを生成する
	public static String generateBasePassword(final PasswordConfigEntity config, int availableLength) {
		StringBuilder newPassword = new StringBuilder();

		
		// case1: 英小文字を含める場合
		if (config.includeLowercase()) {
			if (config.includeUppercase())
				newPassword.append(appendRandomChars(random.nextInt(1, 3), UPPERCASE));
			if (config.includeNumbers())
				newPassword.append(appendRandomChars(random.nextInt(1, 3), NUMBERS));
			if (config.includeSymbols())
				newPassword.append(appendRandomChars(random.nextInt(1, 3), SYMBOLS));
			newPassword.append(appendRandomChars(availableLength - newPassword.length(), LOWERCASE)); // 余白を小英文字で埋める

			// case2: 英小文字を含めない場合
		} else {
			int upperTime = 0;
			int symbolTime = 0;
			int numberTime = 0;
			int totalLength = 0;

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

			// 記号・大英文字・数字をパスワードに追加する
			newPassword.append(appendRandomChars(numberTime, NUMBERS));
			newPassword.append(appendRandomChars(symbolTime, SYMBOLS));
			newPassword.append(appendRandomChars(upperTime, UPPERCASE));
		}

		return newPassword.toString();
	}

	/* ランダムな文字をn個生成する
	 * @param additionalTimes : 生成したい文字列の個数
	 * @param characterSet       : 文字列の候補
	 * @return : ランダムな文字列(characterSetからadditionalTimes回抽出されたランダムな文字列)
	 */
	private static String appendRandomChars(final int additionalTimes, final String characterSet) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < additionalTimes; i++) {
			text.append(String.valueOf(characterSet.charAt(random.nextInt(characterSet.length()))));
		}
		return text.toString();
	}

	// 文字列をシャッフルする
	public static StringBuilder shuffle(final String password) {
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
}
