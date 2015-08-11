package src.main.utils;
import java.math.BigInteger;

public class TonelliShanks {

	private TonelliShanks () {}

	public static LargeInteger modPower(LargeInteger m, LargeInteger exp, LargeInteger a) {
		LargeInteger zero = LargeInteger.getInstance(0);
		LargeInteger one = LargeInteger.getInstance(1);
		LargeInteger two = LargeInteger.getInstance(2);
		if (exp.compareTo(one) == 0) return a.mod(m);
		if (exp.compareTo(zero) == 0) return one;
		if(exp.mod(two).compareTo(zero) == 0)
		{
			LargeInteger x = modPower(m,exp.divide(two),a);
			return (x.multiply(x)).mod(m) ;
		}
		else
		{
			LargeInteger x = modPower(m,exp.subtract(one).divide(two),a);
			return (x.multiply(x).multiply(a)).mod(m);
		}
	}	

	public static LargeInteger legendreSymbol (BigInteger n, BigInteger p) {
		return LargeInteger.getInstance(0);
	}

	public static void main (String[] args) {
		LargeInteger m = LargeInteger.getInstance(9);
		LargeInteger exp = LargeInteger.getInstance(13);
		System.out.println(modPower(m, exp.subtract(1).divide(2), exp));
	}
}
