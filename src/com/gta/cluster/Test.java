package com.gta.cluster;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        DBScan dbscan = new DBScan(0.70, 3, 3);
        String s1 = "���ɹŰ��������췢��3.0��������Դ���0ǧ��";
        String s2 = "���Ͼ����з���3.2��������Դ���5ǧ��";
        String s3 = "�½�Ƥɽ�ط���3.3��������Դ���10ǧ��";
        String s4 = "�½�Ƥɽ�ط���4.2��������Դ���10ǧ��";
        String s5 = "�½�Ƥɽ�ط���3.3��������Դ���10ǧ�ף�ͼ��";
        String s6 = "�½�Ƥɽ�ط���3.6��������Դ���10ǧ��";
        String s7 = "���ɱ�Ⱥ������4.9��������Դ���20ǧ��";
        String s8 = "���ඨ���ط���4.9��������Դ���11ǧ��";
        String s9 = "�½�Ƥɽ�ط���3.3��������Դ���10ǧ�ף�ͼ��";
        String s10 = "�����������������ط���3.7��������Դ���6ǧ��";
        String s11 = "���������ط���3.7��������Դ���6ǧ��";
        String s12 = "ɽ����̨����������4.0��������Դ���5ǧ��";
        String s13 = "ɽ����̨����������4.0��������Դ���5ǧ��";
        String s14 = "���ض����ط���3.1��������Դ���7ǧ��";
        String s15 = "�½�Ƥɽ�ط���4.6��������Դ���10ǧ��";
        
        dbscan.addDataNode(s1);
        dbscan.addDataNode(s2);
        dbscan.addDataNode(s3);
        dbscan.addDataNode(s4);
        dbscan.addDataNode(s5);
        dbscan.addDataNode(s6);
        dbscan.addDataNode(s7);
        dbscan.addDataNode(s8);
        dbscan.addDataNode(s9);
        dbscan.addDataNode(s10);
        dbscan.addDataNode(s11);
        dbscan.addDataNode(s12);
        dbscan.addDataNode(s13);
        dbscan.addDataNode(s14);
        dbscan.addDataNode(s15);
        dbscan.addDataNode(s15);
        dbscan.analysis();
	}

}
