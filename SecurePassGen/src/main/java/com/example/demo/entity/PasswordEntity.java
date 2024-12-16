package com.example.demo.entity;

import java.util.Optional;

import com.example.demo.util.PasswordUtils;

public record PasswordEntity(String password) {

	public PasswordEntity(final String password) {
		this.password = password;
	}

	/* 設定にもとづいたパスワードを1個生成する
	 * <手順> ---
	 * ① 設定をチェックする
	 * ② カスタム文字列の長さをチェックする
	 * ③ パスワードを作る
	 * ④ パスワードをシャッフルする
	 * ⑤ カスタム文字列をパスワードの末尾に追加する
	 * ⑥ 指定文字数を満たしているか確認する
	 * ---
	 * @param config: パスワード生成する際の設定
	 * @return passwordEntity: ランダムに生成されたパスワード文字列
	 */
	public PasswordEntity generatePassword(final PasswordConfigEntity config) {
		//		①	設定をチェックする
		if (!PasswordUtils.isConfigValid(config)) {
			throw new IllegalArgumentException("チェックを1つ以上入れてください");
		}
		//		② カスタム文字列の長さチェック
		String customCharacters = config.customCharacters();
		int targetLength = config.length();
		if (customCharacters.length() > targetLength) {
			throw new IllegalArgumentException("カスタム文字列が長すぎます");
		}

		//		③ パスワードを作る
		int availableLength = targetLength - customCharacters.length(); // カスタム文字列追加前のパスワードの長さ
		String basePassword = PasswordUtils.generateBasePassword(config, availableLength);
		//		④ パスワードをシャッフルする
		StringBuilder finalPassword = PasswordUtils.shuffle(basePassword);
		//		⑤ カスタム文字列をパスワードの末尾に追加する
		Optional.of(customCharacters).ifPresent(finalPassword::append);

		// ⑥ 指定文字数を満たしているか確認する
		if (finalPassword.length() == targetLength) {
			return new PasswordEntity(finalPassword.toString());
		} else {
			throw new IllegalArgumentException("パスワード生成失敗。最大文字列の長さを増やしてください");
		}
	}

}
