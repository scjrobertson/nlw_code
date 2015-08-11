package src.main.utils;

import java.math.BigInteger;
import java.util.Random;

public class LargeInteger {

	private BigInteger N;

	private LargeInteger(String N) { this.N = new BigInteger(N); }

	private LargeInteger(String N, int rad) { this.N = new BigInteger(N, rad); }

	private LargeInteger(int N) { this.N =  new BigInteger(N + ""); }

	private LargeInteger(long N) { this.N = new BigInteger(N + ""); }

	private LargeInteger(short N) { this.N = new BigInteger(N + ""); }

	private LargeInteger(byte[] N) { this.N = new BigInteger(N); }

	private LargeInteger (int signum, byte[] N) { this.N = new BigInteger(signum, N); }

	private LargeInteger (int bitlength, int certainty, Random rnd) {
		this.N = new BigInteger(bitlength, certainty, rnd);
	}

	private LargeInteger (int bitlength, Random rnd) { 
		this.N = new BigInteger(bitlength, rnd); 
	}

	public static LargeInteger getInstance (String N) { return new LargeInteger(N); }

	public static LargeInteger getInstance (String N, int rad) { 
		return new LargeInteger(N, rad);
	}

	public static LargeInteger getInstance (int N) { return new LargeInteger(N); }

	public static LargeInteger getInstance (long N) { return new LargeInteger(N); }

	public static LargeInteger getInstance (short N) { return new LargeInteger(N); }

	public static LargeInteger getInstance (byte[] N) { return new LargeInteger(N); }

	public static LargeInteger getInstance (int signum, byte[] N) { 
		return new LargeInteger(signum, N); 
	}

	public static LargeInteger getInstance (int bitlength, int certainty, Random rnd) { 
		return new LargeInteger(bitlength, certainty, rnd); 
	}

	public static LargeInteger getInstance (int bitlength, Random rnd) {
		return new LargeInteger(bitlength, rnd);
	}

	public LargeInteger add (String N) {
		BigInteger result = this.N.add(new BigInteger(N));
		return new LargeInteger(result.toString());
	}

	public LargeInteger add (int N) {
		BigInteger result = this.N.add(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger add (long N) {
		BigInteger result = this.N.add(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger add (short N) {
		BigInteger result = this.N.add(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}

	public LargeInteger add (LargeInteger M) {
		BigInteger result = this.N.add(M.N);
		return new LargeInteger(result.toString());
	}

	public LargeInteger divide (String N) {
		BigInteger result = this.N.divide(new BigInteger(N));
		return new LargeInteger(result.toString());
	}

	public LargeInteger divide (int N) {
		BigInteger result = this.N.divide(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger divide (long N) {
		BigInteger result = this.N.divide(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger divide (LargeInteger M) {
		BigInteger result = this.N.divide(M.N);
		return new LargeInteger(result.toString());
	}

	public LargeInteger divide (short N) {
		BigInteger result = this.N.divide(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger multiply (String N) {
		BigInteger result = this.N.multiply(new BigInteger(N));
		return new LargeInteger(result.toString());
	}

	public LargeInteger multiply (int N) {
		BigInteger result = this.N.multiply(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger multiply (long N) {
		BigInteger result = this.N.multiply(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger multiply (short N) {
		BigInteger result = this.N.multiply(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}

	public LargeInteger multiply (LargeInteger M) {
		BigInteger result = this.N.multiply(M.N);
		return new LargeInteger(result.toString());
	}

	public LargeInteger subtract (String N) {
		BigInteger result = this.N.subtract(new BigInteger(N));
		return new LargeInteger(result.toString());
	}

	public LargeInteger subtract (int N) {
		BigInteger result = this.N.subtract(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger subtract (long N) {
		BigInteger result = this.N.subtract(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}

	public LargeInteger subtract (short N) {
		BigInteger result = this.N.subtract(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}

	public LargeInteger subtract (LargeInteger M) {
		BigInteger result = this.N.subtract(M.N);
		return new LargeInteger(result.toString());
	}
	
	public LargeInteger mod (String N) {
		BigInteger result = this.N.mod(new BigInteger(N));
		return new LargeInteger(result.toString());
	}

	public LargeInteger mod (int N) {
		BigInteger result = this.N.mod(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger mod (long N) {
		BigInteger result = this.N.mod(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}


	public LargeInteger mod (short N) {
		BigInteger result = this.N.mod(new BigInteger(N + ""));
		return new LargeInteger(result.toString());
	}

	public LargeInteger mod (LargeInteger N) {
		BigInteger result = this.N.mod(N.N);
		return new LargeInteger(result.toString());
	}

	public LargeInteger pow (String N) {
		BigInteger result = this.N.pow(Integer.parseInt(N));
		return new LargeInteger(result.toString());
	}

	public LargeInteger pow (int N) {
		BigInteger result = this.N.pow(N);
		return new LargeInteger(result.toString());
	}

	public LargeInteger pow (LargeInteger N) {
		BigInteger result = this.N.pow(Integer.parseInt(N.toString()));
		return new LargeInteger(result.toString());
	}

	public LargeInteger shiftLeft (int n) {
		return new LargeInteger(this.N.shiftLeft(n).toString());
	}

	public LargeInteger shiftRight (int n) {
		return new LargeInteger(this.N.shiftRight(n).toString());
	}

	public LargeInteger xor (LargeInteger M) {
		return new LargeInteger(this.N.xor(M.N).toString());
	}

	public static LargeInteger probablePrime (int bitlength, Random rnd) {
		BigInteger p = BigInteger.probablePrime(bitlength, rnd);
		return new LargeInteger(p.toString());
	}
	
	public int compareTo(LargeInteger M) {
		return this.N.compareTo(M.N);
	}

	@Override public int hashCode () { return this.N.hashCode(); }

	@Override public boolean equals (Object x) {
		if ( x instanceof LargeInteger ) {
			if (this.hashCode() == x.hashCode()) return true;
		}
		return false;
	}

	@Override public String toString() { return this.N.toString(); }
}
