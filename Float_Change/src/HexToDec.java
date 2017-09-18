/*��ӻԣ�20170913*/
import java.util.*;
import java.util.regex.*;
class Trans{
	//��������Ƿ�淶
	public static boolean checkString(String in){
		boolean isTrue = true;
		//������ʽ
		Pattern pattern = Pattern.compile("^[0][xX][0-9A-Fa-f]{3}$");
		Matcher match=pattern.matcher(in);
		isTrue = match.matches();
		return isTrue;
	}
	//��16�����ַ���ת����2�����ַ���
	public static String hexToBin(String hexString){
		String binRes = "";
		for(int i = 0;i<hexString.length();i++){
			//��16������ת��10������
			int _16_10num = Integer.parseInt(hexString.charAt(i)+"",16);
			//��10������ת��2����
			String _10_2num = Integer.toBinaryString(_16_10num);
			//��2�������淶��
			binRes += String.format("%04d",Integer.parseInt(_10_2num));
		}
		return binRes;
	}
	//��2����С����֮���ַ���ת��Ϊ10�����ַ���
	public static double binToDec(String binString){
		if(binString.length()==1) return (binString.charAt(0)-'0')/2.0;
        return (binString.charAt(0)-'0')/2.0 + binToDec(binString.substring(1))/2.0;	
	}
}
public class HexToDec {
	public static void main(String args[]){
		final int totalLength = 3;//������Ч�ַ�����
		final int orderLength = 5;//���볤��
		final int tailLength = 3;//β������
		final int orderBias = 15;
		Scanner reader = new Scanner(System.in);
		System.out.println("�����������ת���ĸ���������ʽ��0xFF8��");
		//�����ַ���
		String inString = reader.next();
		//��������Ƿ����
		while(!Trans.checkString(inString)){
			System.out.println("��������,����������");
			inString = reader.next();
		}
		//��ȡ�м�8λ
		String hexString = inString.substring(2,2+totalLength);
		//��16����ת����2����
		String binString = Trans.hexToBin(hexString);
		//����������λ
		char binString_symbol = binString.charAt(3);
		//����������
		int binString_order = Integer.valueOf(binString.substring(4, 4+orderLength),2)-orderBias;
		//������β��
		double binString_tail = 1+Trans.binToDec(binString.substring(4+orderLength));
		System.out.print("ת�������ֵΪ��");
		if(binString.substring(4,4+orderLength).equals("00000")&&binString.substring(4+orderLength,4+orderLength+tailLength).equals("000")){
			System.out.println("0");
		}else if(binString.substring(4,4+orderLength).equals("11111")&&!(binString.substring(4+orderLength,4+orderLength+tailLength).equals("000"))){
			System.out.println("NaN");
		}else if((binString.substring(3,4).equals("0"))&&binString_order==16&&binString_tail==1.0){
			System.out.println("+INF");
		}else if((binString.substring(3,4).equals("1"))&&(binString_order==16)){
			System.out.println("-INF");
		}else{
			//�������
			System.out.print(binString_symbol == '0'?"":"-");
			//������
			System.out.println(binString_tail*Math.pow(2, binString_order));
		}
		reader.close();
	}
}