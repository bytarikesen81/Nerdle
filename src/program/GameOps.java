package program;

import java.math.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.lang.*;

//Oyun kapsamindaki tum aritmetik islemleri gercekleyen sinif
public class GameOps {
	public static double sum;
	
	/*Denklem uretici ana metod*/
	public static String generateEquation() {
		
		//Degiskenleri hazirla
		boolean divZero = false;
		long sumS;
		String equation;
		int opCount,length;
		
		//Denklemdeki sayi,operator ve esitlik hanesini tutan linkli liste
		LinkedList<String> exp = new LinkedList<String>(); 
		
		//7-9 arasinda uzunluk degerini sec
		Random rand = new Random();
		length = 7+rand.nextInt(3);
		
		//Gerekli kosullari karsilayan denklem uretim islemine basla
		do {
			divZero = false; //0'a bolunme kontrolu
			equation = "";
			exp.clear();
			
			//Operator sayisini uret
			opCount = opCount();
			
			//Operatorlerin arasina gelecek uygun uzunlukta sayilari ve aralarinda operatorleri uret
			for(int i=0; i<opCount; i++) {
				exp.add(selectNumber(opCount));
				exp.add(selectOp());
			}
			
			//Esittir hanesinden onceki son sayiyi da listeye ekle
			exp.add(selectNumber(opCount));
			
			try {
				sum = calcSum(exp); //Denklem sonucunu hesapla
				
				//Sonucun tam sayi olup olmadiginin kontrolu
				if(isInt(sum)) { //Tam sayiysa linkli listedeki esitlik solundaki degerleri sirayla string'e kopyala
					for(String digit : exp) {
						equation += digit;
					}
					//Sonuc degerini hazirla
					sumS = Math.round(sum); //Sonucun sonundaki .0'i at
					equation += "="+sumS; //Esitlik solu stringine = sembolunu ve sonuc degerini ekle
				}
			//0'a bolunme varsa kontrol degiskenini guncelle
			}catch(ArithmeticException e) {
				divZero = true;
			}
		//Denklemin gerekli sartlara uyup uymadigini kontrol et, uymuyorsa gerekli degerleri guncelleyerek denklemi yeniden yapilandir
		}while(equation.length() != length || !isInt(sum) || divZero == true);
		return equation;
	}
	
	//Rastgele sayi secimini yapan metod
	private static String selectNumber(int opCount) {
		Random rand = new Random();
		Integer genRand = 0;
		switch(opCount) {
			case 1:
				genRand = -999+rand.nextInt(1998);
				return genRand.toString();
			case 2:
				genRand = -99+rand.nextInt(198);
				return genRand.toString();
			case 3:
				genRand = -9+rand.nextInt(18);
				return genRand.toString();
			default:
				return genRand.toString();
		}
	}
	
	//Rastgele operator secimini yapan metod
	private static String selectOp() {
		Random rand = new Random();
		Character symbols[] = {'+','-','*','/'};
		Integer genNum = rand.nextInt(4);
		return symbols[genNum].toString();
	}
	
	//Denklem icinde rastgele operator sayisi belirleyen metod
	private static int opCount() {
		Random rand = new Random();
		return 1 + rand.nextInt(3);
	}
	
	//Birim islem yapici metod
	private static double makeOp(String op, Double op1,Double op2){
		switch(op) {
			case "+":
				return op1+op2;
			case "-":
				return op1-op2;
			case "*":
				return op1*op2;
			case "/":
				return op1/op2;
			default: 
				return 0;
		}
	}
	
	//Esitligin solundaki sayi ve operatorleri alarak sonucu hesaplayan metod
	public static Double calcSum(LinkedList<String> exp) throws ArithmeticException,EmptyStackException{		
		//Islemleri yapmak icin gerekli stackleri ve degerleri hazirla
		String s1,s2,op;
		Double sum;
		Stack<String> opStack1 = new Stack<String>();
		Stack<String> opStack2 = new Stack<String>(); 
		
		for(int i = exp.size()-1; i >= 0; i--) {
			opStack1.push(exp.get(i));
		}
		
		//Esitligin sol tarafindaki oncelikli carpma ve bolme islemlerini yap
		while(!opStack1.isEmpty()) {
			op = opStack1.pop();
			if(isMulOrDiv(op)) {
				s1 = opStack2.pop();
				s2 = opStack1.pop();
				sum = makeOp(op,Double.parseDouble(s1),Double.parseDouble(s2));
				opStack2.push(sum.toString());
			}
			else {
				opStack2.push(op);
			}
		}
		
		//Stack'i yeniden organize et
		while(!opStack2.isEmpty()) {
			opStack1.push(opStack2.pop());
		}
		
		//Esitligin sol tarafindaki toplama ve cikarma islemlerini yap
		while(opStack1.size() > 1) {
			s1 = opStack1.pop();
			op = opStack1.pop();
			s2 = opStack1.pop();
			sum = makeOp(op,Double.parseDouble(s1),Double.parseDouble(s2));
			opStack1.push(sum.toString());
		}
		return Double.parseDouble(opStack1.pop());
	}
	
	//Sayinin tamsayi olup olmadigini kontrol eden metod
	public static boolean isInt(double num) {
		if(Math.ceil(num) == num || Math.floor(num) == num)
			return true;
		else return false;
	}
	
	/*Alinan bir ifadenin once yapisal olarak, daha sonra matematiksel olarak dogru bir matematiksel ozdeslik 
	olup olmadigini kontrol eden metod*/
	public static boolean checkEquation(String fullexp) throws ArithmeticException {
		//Gerekli degiskenleri hazirla
		int i,mul = 1;
		Integer Lmul;
		LinkedList<String> exp = new LinkedList<String>();
		Character c;
		String equ[],tempNum = "";
		Double sum;
		
		//Ifadede esittir bulunmuyorsa direkt gecersiz say
		if(!fullexp.contains("="))
			return false;
		else {
			//Esittir bulunuyorsa ifadeyi esittirden itibaren sol ve sag parcalara ayir
			equ = fullexp.split("=");
			
			try {
				/*Esitligin sol tarafini duzenle ve sayi-operator ayrimini yap*/
				
				//Esitligin ilk sayi veya *,/ operatorune gelene kadarki kismini organize et
				i = 0;
				c = equ[0].charAt(i);
				while(isAddOrSub(c.toString()) && i < equ[0].length()) {
					if(c.toString().compareTo("-") == 0)
						mul *= -1;
					c = equ[0].charAt(++i);
				}
				
				//Esitligin ilgili degerlere geldikten sonraki kismini organize et
				while(i < equ[0].length()) {
					c = equ[0].charAt(i);
					if(isNumber(c.toString())) {
						tempNum += c;
						++i;
					}
					else{
						Lmul = (mul * Integer.parseInt(tempNum));
						exp.add(Lmul.toString());
						exp.add(c.toString());
						tempNum = "";
						mul = 1;
						c = equ[0].charAt(++i);
						while(isAddOrSub(c.toString())) {
							if(c.toString().compareTo("-") == 0)
								mul *= -1;
							c = equ[0].charAt(++i);
						}
						if(isNumber(c.toString())) {
							tempNum += c;
							++i;
						}
					}
				}
				Lmul = (mul * Integer.parseInt(tempNum));
				exp.add(Lmul.toString());
				
				//Sonucu matematiksel olarak hesapla
				sum = calcSum(exp);
				
				//Sifira bolme
				if(Math.floor(sum) == Double.POSITIVE_INFINITY || Math.floor(sum) == Double.NEGATIVE_INFINITY) {
					JOptionPane.showMessageDialog(null,"Sifira bolme Hatasi","Hata",JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				//Ifade ve islem dogru
				else if(Math.floor(sum) == Integer.parseInt(equ[1])) {
					return true;
				}
				//Ifade dogru fakat islem sonucu yanlis
				else {
					return false;
				}
			//İfade gecerli bir matematiksel ozdeslik degil
			}catch(Exception e) {
		         return false;
			}
		}
	}
	
	//Alinan bir hanenin rakam olup olmadiginin kontrolunu yapan metod
	private static boolean isNumber(String digit) {
		String numbers = "0123456789";
		if(numbers.contains(digit)) return true;
		else return false;
	}
	
	//Alinan bir hanenin + veya - operatorlerinden biri olup olmadiginin kontrolunu yapan metod
	private static boolean isAddOrSub(String digit) {
		String ops = "+-";
		if(ops.contains(digit)) return true;
		else return false;
	}
	
	//Alinan bir hanenin * veya / operatorlerinden biri olup olmadiginin kontrolunu yapan metod
	private static boolean isMulOrDiv(String digit) {
		String ops = "*/";
		if(ops.contains(digit)) return true;
		else return false;
	}
	
	//Alinan bir hanenin kumeye cevrilen bir ifadede olup olmadigini teyit eden ara metod
	public static int arrayContains(char[] array,int size,char c) {
		int i = 0;
		while(i < size && array[i] != c) ++i;
		if(i != size) return i;
		else return -1;
	}
}
