package com.gta.titlemerge;

import java.util.List;

public class Title {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		
		String str1 = "ϰ��ƽ���캼�ݸ�����ҵ�������� ̸�����߶˼����˲�";
		String str2 = "ϰ��ƽ���캼�� ̸�����߶˼����˲�";
*/
		String str1 = "ϰ��ƽ�Ӽ�פ�㲿�Ӹɲ����᳹ǿ��Ŀ����ʵ��";
		String str2 = "ϰ��ƽ�ڽӼ�פ�㲿���쵼�ɲ�ʱǿ�� �᳹ǿ��Ŀ���ʵ����ʵ�� Ŭ���������ӽ����¾���";
		String str3 = "ϰ��ƽ���᳹ǿ��Ŀ���ʵ����ʵ�� Ŭ���������ӽ����¾���";
		
		String str4 = "16ʡ�ݳ�̨�����ƶȸĸ����";
		String str5 = "16ʡ�ݳ�̨�����ƶȸĸ���� ����ʵ��ס֤�ƶ�";
		String str6 = "M5W9��ͷ�ٱ����ֳ�����ͷ ��ͨȫ����������";
		String str7 = "M5W9��ͷ��Ԫ�ٱ��ֳ�����ͷ ��ͨȫ����������";
		
		String str8 = "һ��ʦ��Ȣ�°����Ů�� Ը��50ͷţ��Ƹ��(ͼ)";
		String str9 = "��������ʦ��Ȣ�°����Ů�� Ը��50ͷţ��Ƹ��";
		
		String str10 = "����᣺�����ƶ���������ʱ���������ҵ";
		String str11 = "����᣺����һ���ƶ���Ӫ�ʱ���������ҵ";
		
		String str12 = "���������ؾ����������������";
		String str13 = "���������ؾ��������»��������";
		String str14 = "���������ؾ���������������"; 
		
		String str15 = "̨������������̨��֤��ͨ�� �������ǩע";
		String str16 = "̨������������̨��֤��ͨ��";
		String str17 = "����̨�������������ǩע ����̨��֤����ʵʩ";

		TitleMerge tm = new TitleMerge();
		List<TitleDict> td1 = tm.tokenizer(str16);
		List<TitleDict> td2 = tm.tokenizer(str17);
		if (tm.anslysisTerms(td1, td2) > 0)
		{
			System.out.println("match");
		}
		else 
		{
			System.out.println("false");
		}
			
	}
}
