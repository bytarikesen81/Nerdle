/**
 * 
 */
package program;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.*;

/**
 * @author bytar
 *
 */
public class GenerateEquationTest extends TestCase{
	private static String equation;
	private static String parts[];
	private static double result;
	/**
	 * @throws java.lang.Exception
	 */
	
	//Test hazirlik sinifi
	@BeforeClass
	protected void setUp() throws Exception {
		equation = GameOps.generateEquation();
		parts = equation.split("=");
	}

	//Denklemin uygun uzunluk araligina sahip olup olmadigini test eder
	@Test
	public void testLength() {
		assertFalse(equation.length() > 9 || equation.length() < 7);
	}
	
	@Test
	public void testContainsOneEquals() {
		int i = 0,count = 0;
		while(i<equation.length() &&  count < 2) {
			if(equation.charAt(i) == '=') ++count;
			++i;
		}
		assertEquals(count,1);
	}
	
	//Denklemin once yapisal, sonra ise islemsel olarak dogrulugunu test eder
	@Test
	public void testIsMathematicallyCorrect() throws ArithmeticException{
		//Gerekli degiskenleri hazirla
		String numbers = "0123456789";
		int i,mul = 1;
		Integer Lmul;
		LinkedList<String> exp = new LinkedList<String>();
		Character c;
		String tempNum = "";
		Double sum;
		
		/*Esitligin sol tarafini duzenle ve sayi-operator ayrimini yap*/
		//Esitligin ilk sayi veya *,/ operatorune gelene kadarki kismini organize et
		i = 0;
		c = parts[0].charAt(i);
		while((c.toString().equals("+") || c.toString().equals("-")) && i < parts[0].length()) {
			if(c.toString().compareTo("-") == 0)
				mul *= -1;
			c = parts[0].charAt(++i);
		}
		
		//Esitligin ilgili degerlere geldikten sonraki kismini organize et
		while(i < parts[0].length()) {
			c = parts[0].charAt(i);
			if(numbers.contains(c.toString())) {
				tempNum += c;
				++i;
			}
			else{
				Lmul = (mul * Integer.parseInt(tempNum));
				exp.add(Lmul.toString());
				exp.add(c.toString());
				tempNum = "";
				mul = 1;
				c = parts[0].charAt(++i);
				while((c.toString().equals("+") || c.toString().equals("-"))) {
					if(c.toString().compareTo("-") == 0)
						mul *= -1;
					c = parts[0].charAt(++i);
				}
				if(numbers.contains(c.toString())) {
					tempNum += c;
					++i;
				}
			}
		}
		Lmul = (mul * Integer.parseInt(tempNum));
		exp.add(Lmul.toString());
		
		//Islemleri yapmak icin gerekli stackleri ve degerleri hazirla
		String s1,s2,op;
		Stack<String> opStack1 = new Stack<String>();
		Stack<String> opStack2 = new Stack<String>(); 
		
		for(i = exp.size()-1; i >= 0; i--) {
			opStack1.push(exp.get(i));
		}
		
		//Esitligin sol tarafindaki oncelikli carpma ve bolme islemlerini yap
		while(!opStack1.isEmpty()) {
			op = opStack1.pop();
			if(op.equals("*") || op.equals("/")) {
				s1 = opStack2.pop();
				s2 = opStack1.pop();
				if(op.equals("*")) 
					sum = Double.parseDouble(s1) * Double.parseDouble(s2);
				else
					sum = Double.parseDouble(s1) / Double.parseDouble(s2);
				opStack2.push(sum.toString());
			}
			else {
				opStack2.push(op);
			}
		}	
		
		//Esitligin sol tarafindaki toplama ve cikarma islemlerini yap
		
		//Stack'i yeniden organize et
		while(!opStack2.isEmpty()) {
			opStack1.push(opStack2.pop());
		}
		
		while(opStack1.size() > 1) {
			s1 = opStack1.pop();
			op = opStack1.pop();
			s2 = opStack1.pop();
			if(op.equals("+")) 
				sum = Double.parseDouble(s1) + Double.parseDouble(s2);
			else
				sum = Double.parseDouble(s1) - Double.parseDouble(s2);
			opStack1.push(sum.toString());
		}
		
		result = Double.parseDouble(opStack1.pop());
		assertEquals(Integer.parseInt(parts[1]),Math.round(result));
	}
	
	//Sonucun tam sayi cikip cikmadigini test eder
	@Test
	public void testResultIsInteger() {
		assertTrue(Math.ceil(result) == result || Math.floor(result) == result);
	}
	
	//Ifadede sifira bolmenin bulunmadigini test eder
	@Test
	public void testHasNotDivideByZero() {
		assertTrue(Math.round(result) != Double.POSITIVE_INFINITY && Math.round(result) != Double.NEGATIVE_INFINITY);
	}
}
