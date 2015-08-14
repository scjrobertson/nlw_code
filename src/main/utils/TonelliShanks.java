/*************************************************************************************************************************************
 * Compilation: javac src/main/utils/TonelliShanks.java
 * Execution: java src.main.utils.TonelliShanks
 * Dependencies: 
 *
 * Contains useful functions such as modulus power, legendre symbols which are used by the Tonnelli-Shanks algorithm.
 *******************************************************/
package src.main.utils;
import java.math.BigInteger;
import java.util.Stack;
/**
 * This library implements the Tonelli-Shanks algorithm.This algortihm solves
 * the congruence x^2 = q (mod p) given q and p.
 *
 *@author SCJ Robertson, 16579852
 *@since 11/08/15
 */
public class TonelliShanks {

	/**
	 * Library of static functions.
	 */
	private TonelliShanks () {}

	/**
	 * Solves the modulus power (a^e)%m for large integers, typically 20 digits ( approx 64 bits)
	 * and over.
	 *
	 * <p> Complexity: O(N) where N is the number of bits in the exponent's binary representation.
	 *
	 * @param a The base of the exponent
	 * @param e The exponent 
	 * @param m The modulus m
	 * @return The result of the equation as a large integer.
	 */
	public static LargeInteger pow(LargeInteger a, LargeInteger e, LargeInteger m) {
		LargeInteger result = LargeInteger.getInstance(1);
		a = a.mod(m);
		for (int i = 0; i < e.bitLength(); ++i) {
			if(e.testBit(i)) result = result.multiply(a).mod(m);
			a = a.multiply(a).mod(m);
		}
		return result;
	}	

	/**
	 * The Legendre symbol for a given a prime p.It will return 1 if a
	 * is a quadratic residue, else it will return -1.
	 *
	 * <p> Complexity: O(N) where N is the number of bits in (p-1)/2's binary representation.
	 *
	 * @param a A integer which is potentially a quadratic residue
	 * @param p A prime which is used as the mod
	 * @return The Legendre symbol for a given p
	 */
	public static int legendreSymbol (LargeInteger a, LargeInteger p) {
		LargeInteger ls = pow(a, p.subtract(1).divide(2), p);
		if (ls.equals(p.subtract(1))) return -1;
		else return ls.intValue();
	}

	/**
	 * Determines whether a number is a quadratic residue modulo p.
	 * 
	 * <p> Complexity: O(N) where N is the number of bits in (p-1)/2's binary representation.
	 *
	 * @param a A integer which is potentially a quadratic residue
	 * @param p A prime which is used as the mod
	 * @return A boolean whether a is a residue or not.
	 */
	public static boolean isQuadraticResidue(LargeInteger a, LargeInteger p) {
		a = a.mod(p);
		if (legendreSymbol(a, p) != 1) { return false; }
		return true;
	}

	/**
	 * Determines the solution of the congruence x^2 = q (mod p), given q and p.
	 *
	 * <p> Complexity: O(2m + 2k + S(S-1)/4) + 1/(2^(S-1)))
	 * m is the number of bits in q and k the number of 1s in p's binary representation.
	 * S is the number of even primes when (p-1) is decomposed into a product of primes.
	 *
	 * @param a A integer which is potentially a quadratic residue
	 * @param p A prime which is used as the mod
	 * @return An array of at most two solutions to the congruence.
	 */
	public static LargeInteger[] quadraticResidues (LargeInteger a, LargeInteger p) {
			LargeInteger[] qr = new LargeInteger[2];
			LargeInteger b, c, d, e, q, t, x, z;
			int i, m, s;
			
			a = a.mod(p);
			qr[0] = a;
			
			if (a.equals(0)) return qr;
			if (p.equals(2)) return qr;

			if (legendreSymbol(a, p) != 1) {
				qr = null;
				return qr;
			}

			if(p.mod(4).compareTo(3) == 0) {
				x = pow(a, p.add(1).divide(4), p);
				qr[0] = x; qr[1] = p.subtract(x);
				return qr;
			}

			q = p.subtract(1); s = 0;
			while (q.mod(2).compareTo(0) == 0) {
				s++; q = q.divide(2);
			}

			z = LargeInteger.getInstance(1);
			while (legendreSymbol(z, p) != -1) { 
				z = z.add(1); 
			}
			c = pow(z, q, p);

			x = pow(a, q.add(1).divide(2), p);
			t = pow(a, q, p);
			m = s;
			while (t.compareTo(1) != 0){
				e = LargeInteger.getInstance(2);
				for ( i = 1; i < m; i++ ) {
					if (pow(t, e, p).compareTo(1) == 0) break;
					e = e.multiply(2);
				}
				d = LargeInteger.getInstance( (int) Math.pow(2.0, m - i - 1.0));
				b = pow(c, d, p);
				x = x.multiply(b).mod(p);
				t = t.multiply(b).multiply(b).mod(p);
				c = b.multiply(b).mod(p);
				m = i;
			}
			qr[0] = x; qr[1] = p.subtract(x);
			return qr;
	}

	/**
	 * A small example of output.
	 *
	 * @param args Standard input.
	 */
	public static void main (String[] args) {
		LargeInteger p = LargeInteger.getInstance("11608072225007531083");
		LargeInteger q = LargeInteger.getInstance("526541896879662857");
		System.out.println(isQuadraticResidue(q, p));
	}
}
