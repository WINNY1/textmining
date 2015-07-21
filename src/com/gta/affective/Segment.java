package com.gta.affective;

import java.io.File;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Segment {
	private final String regex = "[������]";
	private String[] strEnum;                   // ��������
	private String[] negtiveEnum;               // �񶨴�����
	private Set<ConjunctionClassify> conSet;    // ���ʼ���
	private Map<String, Dict> polarMap;         // ���Դʻ��
	private Map<Integer, Float> weightMap;      // ����Ȩ�ؿ�
	private Map<String, EntityDict> companyMap; // ��˾�ʻ��
	private int conIndex;                       // ����λ��
	private int classify;                       // ��������
	private int[] objCount;
	private float[] affectCount;
	
	public Segment() {
		initNegtive();
		initConjunction();
		initPolar();
		initWeight();
//		initCompany();
	}

	
	/**
	 * ��ʼ���񶨴��б�
	 */
	public void initNegtive() {
		try {
			File file = new File("negtive.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			if ((s = br.readLine())!= null) {
				negtiveEnum = s.split("��");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * ��ʼ�������б�
	 */
	public void initConjunction() {
		conSet = new HashSet<ConjunctionClassify>();
		try {
			File file = new File("conjunction.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine())!= null) {
				String []elementEnum = s.split("\t");
				String []conjunctions = elementEnum[0].split("��");
				conSet.add(new ConjunctionClassify(conjunctions[0], conjunctions[1], Integer.parseInt(elementEnum[1])));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * ��ʼ����м��Դʻ��
	 */
	public void initPolar() {
		polarMap = new HashMap<String, Dict>();
		try {
			File file = new File("dict.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				String []element = s.split("\t");
				polarMap.put(element[0], new Dict(element[4], Short.parseShort(element[5]), Short.parseShort(element[6])));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
 	}

	
	/**
	 * ��ʼ�������ʵ�Ȩ��
	 */
	public void initWeight() {
		weightMap = new HashMap<Integer, Float>();
		weightMap.put(new Integer(1), new Float(0.5));
		weightMap.put(new Integer(2), new Float(0.5));
		weightMap.put(new Integer(3), new Float(0.4));
		weightMap.put(new Integer(4), new Float(0.2));
		weightMap.put(new Integer(5), new Float(0.4));
		weightMap.put(new Integer(6), new Float(0.6));
	}
	
	
	public void initCompany() {
		companyMap = new HashMap<String, EntityDict>();
		try {
			File file = new File("user.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			int uid = 1;
			while ((s = br.readLine()) != null) {
				String [] entityEnum = s.split("	");
				companyMap.put(preString(entityEnum[1]), new EntityDict(uid, entityEnum[0], entityEnum[2]));
				uid ++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
/*		
		try {
			File file = new File("company.txt");
			BufferedWriter bw= new BufferedWriter(new FileWriter(file));
			for (Map.Entry<String, EntityDict> entry : companyMap.entrySet()) {
				bw.write(entry.getKey());
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
*/		
	}
	

	/**
	 * �������й�˾����
	 * @param str
	 * @return the correct string of the company
	 */
	public String preString(String str) {
		String s= str;
		if (str.startsWith("*ST")) {
			s = str.substring(3);
		} else if (str.startsWith("ST")) {
			s = str.substring(2);
		} else if (str.startsWith("S")) {
			s = str.substring(1);
		} else if (str.endsWith("��") || str.endsWith("A") ||str.endsWith("��") ||str.endsWith("B")) {
			s = str.substring(0, str.length()-1);
		}
		s = tripWhiteSpace(s);
		return s;
	}
	

	/**
	 * ���ַ���Ǳ�ڿո�ȥ��
	 * @param s
	 * @return the last string of the company
	 */
	public String tripWhiteSpace(String s) {
		if (s.contains(" ") || s.contains("  ")) {
			int index = s.indexOf(" ");
			if (index == -1) {
				index = s.indexOf("  ");
			}
			s = s.substring(0, index)+s.substring(index+1);
			s = tripWhiteSpace(s);
		}
		return s;
	}
	

	public int getConIndex() {
		return conIndex;
	}

	
	public void setConIndex(int conIndex) {
		this.conIndex = conIndex;
	}

	
	public int getClassify() {
		return classify;
	}

	
	public void setClassify(int classify) {
		this.classify = classify;
	}
	
	
	public void releaseCount() {
		for (int i = 0; i < companyMap.size(); i++) {
			objCount[i] = 0;
			affectCount[i] = 0;
		}
	}

	
	/**
	 * �����ı����ݣ���ȡ�ı��������
	 * @param str  �ı�����
	 */
	public float analysis(String str) {
		Pattern p = Pattern.compile(regex);
		strEnum = p.split(str);
		float sumScores = 0;
//		objCount = new int[companyMap.size()];
//		affectCount = new float[companyMap.size()];
		for (int i = 0; i < strEnum.length; i++) {
			List<String> list = tokenizer(strEnum[i]);
//			countKeyCompany(list);
			
			findConjunction(list);
			List<Integer> neglist = findNegative(list);
			neglist = assertNegative(neglist);
			sumScores += countScores(list, neglist);
			
		}
//		releaseCount();
		return sumScores;
	}
	
	/**
	 * �����ӽ����ض��ִ��з֣���ȡ��Ԫ�б�
	 * @param str
	 * @return ���ķִ��б�
	 */
	public List<String> tokenizer(String str) {
		List<String> list = new ArrayList<String>();
		IKAnalyzer analyzer = new IKAnalyzer(true);
		try {
			TokenStream stream = analyzer.tokenStream("", str);
			CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
			stream.reset();
			while (stream.incrementToken()) {
				list.add(cta.toString());
			}
			analyzer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	/**
	 * ��ѯ��ƥ���ִ��Ƿ��ڸþ�����
	 * @param list ���Ӽ��ִʴ���
	 * @param words ����ѯ�ִ�
	 * @return
	 */
	public int findKeyCompany(List<String> list, String words) {
		int count = 0;
		for (String t : list) {
			if (t.equals(words)) {
				count ++;
			}
		}
		return count;
	}
	
		
	public float objCountScores(List<String> list, String words) {
		float count = 0;
		if (list.contains(words)) {
			findConjunction(list);
			List<Integer> neglist = findNegative(list);
			neglist = assertNegative(neglist);
			count = countScores(list, neglist);
		}
		return count;
	}
	
	
	public void countKeyCompany(List<String> list) {
		for (Map.Entry<String, EntityDict> entry : companyMap.entrySet()) {
			objCount[entry.getValue().getUid()-1] +=  findKeyCompany(list, entry.getKey());
			affectCount[entry.getValue().getUid()-1] += objCountScores(list, entry.getKey());   
		}
	}
	
	
	public void getObjCount() {
		
	}
	
	
	public void getCompanyCount() {
		
	}
	
	
	/**
	 * �жϾ������Ƿ�������ʣ����������ʣ��������к�һ���ؼ���λ�ý��б��棬ͬʱȷ��������ȷ���Ĺ�ϵ����
	 * @param list
	 */
	public void findConjunction (List<String> list) {
		int conIndex = -1;
		int classify = 0;
		for (String key : list) {
			conIndex++;
			for (ConjunctionClassify conClassify : conSet) {
				if (conClassify.getMiddle().equals(key)) {
					String prefix = conClassify.getPrefix();
					if (prefix.equals("")) {  // one conjunction word
						if (conIndex < list.size()) {
							classify = conClassify.getClassify();
							setClassify(classify);
							setConIndex(conIndex);
							return;
						}
						else {
							setClassify(0);
							setConIndex(-1);
							return;
						}
					}
					else {  // two conjunction word
						int preIndex = list.indexOf(prefix);
						if (preIndex < 0) {  // not found
							setClassify(0);
							setConIndex(-1);
							return;
						}
						else {
							if (conIndex <= preIndex) {  // index out of bound
								setClassify(0);
								setConIndex(-1);
								return;
							}
							else {
								classify = conClassify.getClassify();
								setClassify(classify);
								setConIndex(conIndex);
								return;
							}
						}
					}
				}
			}
		}
		setClassify(0);
		setConIndex(-1);
		return;    // not find key
	}

	
	/**
	 * �жϾ������Ƿ���ڷ񶨴ʣ��������򽫷񶨴�λ����ӽ��б���
	 * @param list
	 * @return �񶨴��б�
	 */
	public List<Integer> findNegative(List<String> list) {
		List<Integer> neglist = new ArrayList<Integer>();
		int index = -1;
		for (String key : list) {
			index ++;
			for (int i = 0; i < negtiveEnum.length; i++) {
				if (key.equals(negtiveEnum[i])) {
					neglist.add(new Integer(index));
				}
			}
		}
		return neglist;
	}

	
	/**
	 * �ж��Ƿ����˫�ط񶨣����������Ƴ�˫�ط񶨴ʵ�λ����Ϣ
	 * @param neglist
	 * @return �񶨴��б�
	 */
	public List<Integer> assertNegative(List<Integer> neglist) {
		List<Integer> newlist = new ArrayList<Integer>();
		for (Integer it : neglist) {
			newlist.add(it);
		}
		
		if (neglist.size() <= 1) 
			return newlist;
		else {
			int index = neglist.get(0).intValue();
			for (Integer key : neglist) {
				if (key.intValue() - index == 1) { // double negative
					newlist.remove(key);
					newlist.remove(new Integer(index));
				}
				index = key.intValue(); 
			}
			return newlist;
		}
	}

	
	/**
	 * �����Ԫ����м�������
	 * @param level       �ʿ��е����ǿ��
	 * @param judge       �ʿ��еļ���
	 * @param weight      ����ǰ����ռ����
	 * @param converse    �Ƿ��Է�ת
	 * @return  ��Ԫ�������Լ�����
	 */
	public float countPolar(short level, short judge, float weight, boolean converse) {
		float sum = 0;
		if (converse) {
			if (judge == 0) 
				sum -= level * weight;
			else if (judge == 1)
				sum -= level * weight;
			else 
				sum += level * weight;
		}
		else {
			if (judge == 2) 
				sum -= level * weight;
			else if (judge == 1)
				sum += level * weight;
			else 
				sum = 0;
		}
		return sum;
	}

	/**
	 * ������ӵ���м�������
	 * @param list
	 * @param neglist
	 * @return ���ӵļ�������
	 */
	public float countScores(List<String> list, List<Integer> neglist) {
		float sumScores = 0;
		boolean negFound = false;
		if (this.getConIndex() > 0) {  // has conjunction
			float weight = weightMap.get(new Integer(this.classify));
			int keyIndex = -1;
			for (String key : list) {
				keyIndex ++;
				Dict dict = null;
				if ((dict = polarMap.get(key)) != null) {
					if (keyIndex < this.getConIndex()) {
						for (Integer negIndex : neglist) {
							if (keyIndex - negIndex.intValue() == 1) {
								negFound = true;
								sumScores += countPolar(dict.getLevel(), dict.getJudgement(), weight, negFound);
							}
						}
						if (!negFound) {
							sumScores += countPolar(dict.getLevel(), dict.getJudgement(), weight, negFound);
						}
					}
					else {
						for (Integer negIndex : neglist) {
							if (keyIndex - negIndex.intValue() == 1) {
								negFound = true;
								sumScores += countPolar(dict.getLevel(), dict.getJudgement(), 1-weight, negFound);
							}
						}
						if (!negFound) {
							sumScores += countPolar(dict.getLevel(), dict.getJudgement(), 1-weight, negFound);
						}
					}
				}
			negFound = false;
			}
		} 
		else {
			int keyIndex = -1;
			for (String key : list) {
				keyIndex ++;
				Dict dict = null;
				if ((dict = polarMap.get(key)) != null) {
					for (Integer negIndex : neglist) {
						if (keyIndex - negIndex.intValue() == 1) {
							negFound = true;
							sumScores += countPolar(dict.getLevel(), dict.getJudgement(), 1, negFound);
						}
					}
					if (!negFound) {
						sumScores += countPolar(dict.getLevel(), dict.getJudgement(), 1, negFound);
					}
				}
			negFound = false;
			}
		}
		return sumScores;
	}
		
	
}
