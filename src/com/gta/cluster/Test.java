package com.gta.cluster;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        DBScan dbscan = new DBScan(0.0001, 3, 3);
        String s1 = "ϰ��ƽ9�½����� �����״������Ϲ�����ݽ�";
        String s2 = "֤������27�ռ������۹�Ʊ���й����������˲�";
        String s3 = "���繫˾��ί���ʵ���ٱ����³�����ְȨ";
        String s4 = "���ʴ�ʹ��Ӧ�������Ƿ�μӿ�սʤ��70�����ı�";
        dbscan.addDataNode(s1);
        dbscan.addDataNode(s2);
        dbscan.addDataNode(s3);
        dbscan.addDataNode(s4);
        dbscan.analysis();
	}

}
