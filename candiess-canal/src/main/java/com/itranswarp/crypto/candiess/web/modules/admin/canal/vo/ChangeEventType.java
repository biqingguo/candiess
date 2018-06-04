package com.itranswarp.crypto.candiess.web.modules.admin.canal.vo;

public enum ChangeEventType {
	/**
	 * <code>INSERT = 1;</code>
	 */
	INSERT(0, 1),
	/**
	 * <code>UPDATE = 2;</code>
	 */
	UPDATE(1, 2),
	/**
	 * <code>DELETE = 3;</code>
	 */
	DELETE(2, 3);
	/**
	 * <code>INSERT = 1;</code>
	 */
	public static final int INSERT_VALUE = 1;
	/**
	 * <code>UPDATE = 2;</code>
	 */
	public static final int UPDATE_VALUE = 2;
	/**
	 * <code>DELETE = 3;</code>
	 */
	public static final int DELETE_VALUE = 3;

	public final int getNumber() {
		return value;
	}

	public static ChangeEventType valueOf(int value) {
		switch (value) {
		case 1:
			return INSERT;
		case 2:
			return UPDATE;
		case 3:
			return DELETE;
		default:
			return null;
		}
	}

	private final int index;
	private final int value;

	private ChangeEventType(int index, int value) {
		this.index = index;
		this.value = value;
	}

}
