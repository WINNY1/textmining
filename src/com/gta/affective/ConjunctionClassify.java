package com.gta.affective;

public class ConjunctionClassify {
	private String prefix;    // ǰһ�����Ӵ�
	private String middle;    // ��һ�����Ӵ�
	private int classify;     // ��������
	
	public ConjunctionClassify(String prefix, String middle, int classify) {
		this.prefix = prefix;
		this.middle = middle;
		this.classify = classify;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getMiddle() {
		return middle;
	}

	public int getClassify() {
		return classify;
	}
}
