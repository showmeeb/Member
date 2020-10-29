package com.example.member.exception;

public enum MemberExceptionCode {

	insertError(100, "insert error"), updateError(101, "update error");

	private final Integer number;
	private final String message;

	private MemberExceptionCode(Integer number, String message) {
		this.number = number;
		this.message = message;
	}

	public static MemberExceptionCode getError(Integer param) {
		for (MemberExceptionCode memberExceptionCode : values()) {
			if (memberExceptionCode.getNumber() == param) {
				return memberExceptionCode;
			}
		}
		return null;
	}

	public Integer getNumber() {
		return number;
	}

	public String getMessage() {
		return message;
	}
}
