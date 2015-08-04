package com.gta.simhash;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
		Test content = new Test();
		String s1 = content.getFileData("text1.txt");
		String s2 = content.getFileData("text2.txt");

		String s3 = "�������Ϻ���������죬���������������������Ͼ������ݣ����������ţ����ϣ��ൺ���人�����ݣ����ڣ��ɶ���������̫ԭ����ɳ�����֣�������֣�ݣ���������������³ľ�룬���ݣ��������Ϸʣ��ߺ�";
		String s4 = "�������Ϻ���������죬���������������������Ͼ������ݣ����������ţ����ϣ��ൺ���人�����ݣ����ڣ��ɶ���������̫ԭ����ɳ�����֣�������֣�ݣ�����";
		SimHash hash1 = new SimHash(s1, 64, 8);
		SimHash hash2 = new SimHash(s2, 64, 8);
		hash1.getResult(hash2);
	}
	
	
	public String getFileData(String fileName)
	{
		try {
			File file = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s= br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
