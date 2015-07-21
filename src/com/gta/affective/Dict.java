package com.gta.affective;


public class Dict {
	private String category;   // ���Է���
	private short level;       // ����ǿ��
	private short judgement;   // �����ж�
	
	public Dict() {
		this.category = "";
		this.level = 1;
		this.judgement = 0;
	}
	
	public Dict(String category, short level, short judgement) {
		this.category = category;
		this.level = level;
		this.judgement = judgement;
	}
	
	public String getCategory() {
		return category;
	}

	public short getLevel() {
		return level;
	}

	public short getJudgement() {
		return judgement;
	}
}
