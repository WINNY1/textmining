package com.gta.namedentity;
/*
import java.io.IOException;

import org.wltea.analyzer.lucene.IKAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
*/
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] s = new String[3];
		s[0] = "������޳ɷ�����ֻҪ�������Ͳ��������";
		s[1] = "�����߿��濼�¼�9�˱�ץ ����δ�������ʶ��";
		s[2] = "������֮�ǡ������ھ��ع⣺����ʱ�Ӷ���ŵ��";
/*
		String str1 = "ͬ־�ǣ������ǣ���Һã��й���������������ܾ֣����ҽ����3��ʱ�ⲻ�㳤���߼�����ʦ";
		IKAnalyzer analyzer = new IKAnalyzer(true);
		try {
			TokenStream stream = analyzer.tokenStream("", str1);
			CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
			stream.reset();
			while (stream.incrementToken()) {
				System.out.println(cta.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
*/
		Corpus corpus = new Corpus();
		corpus.getResult(s[0]);
		corpus.getResult(s[1]);
		corpus.getResult(s[2]);
	}

}
