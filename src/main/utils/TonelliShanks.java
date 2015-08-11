package src.main.utils;
import java.math.BigInteger;
import java.util.Stack;

public class TonelliShanks {

	private TonelliShanks () {}

	public static LargeInteger pow(LargeInteger a, LargeInteger e, LargeInteger m) {
		LargeInteger result = LargeInteger.getInstance(1);
		a = a.mod(m);
		for (int i = 0; i < e.bitLength(); ++i) {
			if(e.testBit(i)) result = result.multiply(a).mod(m);
			a = a.multiply(a).mod(m);
		}
		return result;
	}	

	public static int legendreSymbol (LargeInteger a, LargeInteger p) {
		LargeInteger ls = pow(a, p.subtract(1).divide(2), p);
		if (ls.equals(p.subtract(1))) return -1;
		else return ls.intValue();
	}


	public static boolean isQuadraticResidue(LargeInteger q, LargeInteger p) {
		LargeInteger[] qr = quadraticResidues(q, p);
		if (qr == null) return false;
		return true;
	}

	public static LargeInteger[] quadraticResidues 
		(LargeInteger a, LargeInteger p) {
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

	public static void main (String[] args) {
		LargeInteger q = LargeInteger.getInstance("11608072225007531083");
		LargeInteger p = LargeInteger.getInstance("526541896879662857").add(2);

		System.out.println(isQuadraticResidue(q, p));
	}
}
