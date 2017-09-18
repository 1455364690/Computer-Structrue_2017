/*孙加辉，20170913*/
import java.util.*;
import java.util.regex.*;
class Trans{
	//检查输入是否规范
	public static boolean checkString(String in){
		boolean isTrue = true;
		//正则表达式
		Pattern pattern = Pattern.compile("^[0][xX][0-9A-Fa-f]{3}$");
		Matcher match=pattern.matcher(in);
		isTrue = match.matches();
		return isTrue;
	}
	//将16进制字符串转化成2进制字符串
	public static String hexToBin(String hexString){
		String binRes = "";
		for(int i = 0;i<hexString.length();i++){
			//将16进制数转成10进制数
			int _16_10num = Integer.parseInt(hexString.charAt(i)+"",16);
			//将10进制数转成2进制
			String _10_2num = Integer.toBinaryString(_16_10num);
			//将2进制数规范化
			binRes += String.format("%04d",Integer.parseInt(_10_2num));
		}
		return binRes;
	}
	//将2进制小数点之后字符串转化为10进制字符串
	public static double binToDec(String binString){
		if(binString.length()==1) return (binString.charAt(0)-'0')/2.0;
        return (binString.charAt(0)-'0')/2.0 + binToDec(binString.substring(1))/2.0;	
	}
}
public class HexToDec {
	public static void main(String args[]){
		final int totalLength = 3;//输入有效字符长度
		final int orderLength = 5;//阶码长度
		final int tailLength = 3;//尾数长度
		final int orderBias = 15;
		Scanner reader = new Scanner(System.in);
		System.out.println("请重新输入待转化的浮点数，格式如0xFF8：");
		//输入字符串
		String inString = reader.next();
		//检查输入是否错误
		while(!Trans.checkString(inString)){
			System.out.println("输入有误,请重新输入");
			inString = reader.next();
		}
		//截取中间8位
		String hexString = inString.substring(2,2+totalLength);
		//将16进制转换成2进制
		String binString = Trans.hexToBin(hexString);
		//浮点数符号位
		char binString_symbol = binString.charAt(3);
		//浮点数阶码
		int binString_order = Integer.valueOf(binString.substring(4, 4+orderLength),2)-orderBias;
		//浮点数尾数
		double binString_tail = 1+Trans.binToDec(binString.substring(4+orderLength));
		System.out.print("转化后的数值为：");
		if(binString.substring(4,4+orderLength).equals("00000")&&binString.substring(4+orderLength,4+orderLength+tailLength).equals("000")){
			System.out.println("0");
		}else if(binString.substring(4,4+orderLength).equals("11111")&&!(binString.substring(4+orderLength,4+orderLength+tailLength).equals("000"))){
			System.out.println("NaN");
		}else if((binString.substring(3,4).equals("0"))&&binString_order==16&&binString_tail==1.0){
			System.out.println("+INF");
		}else if((binString.substring(3,4).equals("1"))&&(binString_order==16)){
			System.out.println("-INF");
		}else{
			//输出符号
			System.out.print(binString_symbol == '0'?"":"-");
			//输出结果
			System.out.println(binString_tail*Math.pow(2, binString_order));
		}
		reader.close();
	}
}