package com.example.encrypt;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RSAthucong {
	public static int gcd(int e, int z) {
		if (e == 0)
			return z;
		else
			return gcd(z % e, e);
	}
	public static String RsaEncryption(String M, String P, String Q) {
		int p, q, n, z, d = 0, e, i;
		int msg = Integer.valueOf(M);
		double c;
		//BigInteger msgback;
		p = Integer.valueOf(P);
		q = Integer.valueOf(Q);
		n = p * q;
		z = (p - 1) * (q - 1);
		System.out.println("z = " + z);
		for (e = 2; e < z; e++) {
			if (gcd(e, z) == 1)
			{
				break;
			}
		}
		System.out.println("e = " + e);
		for (i = 0; i <= 9; i++) {
			int x = 1 + (i * z);
			if (x % e == 0) 
			{
				d = x / e;
				break;
			}
		}
		System.out.println("d = " + d);
		c = (Math.pow(msg, e)) % n;
		System.out.println("Encrypted message is : -");
		System.out.println(c);
		return String.valueOf(c);
		// converting int value of n to BigInteger
//		BigInteger N = BigInteger.valueOf(n);
//		// converting float value of c to BigInteger
//		BigInteger C = BigDecimal.valueOf(c).toBigInteger();
//		msgback = (C.pow(d)).mod(N);
//		System.out.println("Derypted message is : -");
//		System.out.println(msgback);
	}
	public static String RsaDecryption(String C, String P, String Q) {
		int p, q, n, z, d = 0, e, i;
		//int msg = Integer.valueOf(M);
		double c = Double.valueOf(C);
		BigInteger msgback;
		p = Integer.valueOf(P);
		q = Integer.valueOf(Q);
		n = p * q;
		z = (p - 1) * (q - 1);
		System.out.println("z = " + z);
		for (e = 2; e < z; e++) {
			if (gcd(e, z) == 1)
			{
				break;
			}
		}
		System.out.println("e = " + e);
		for (i = 0; i <= 9; i++) {
			int x = 1 + (i * z);
			if (x % e == 0) 
			{
				d = x / e;
				break;
			}
		}
//		System.out.println("d = " + d);
//		c = (Math.pow(msg, e)) % n;
//		System.out.println("Encrypted message is : -");
//		System.out.println(c);
//		return String.valueOf(c);
		//converting int value of n to BigInteger
		BigInteger N = BigInteger.valueOf(n);
		// converting float value of c to BigInteger
		BigInteger C1 = BigDecimal.valueOf(c).toBigInteger();
		msgback = (C1.pow(d)).mod(N);
		System.out.println("Derypted message is : -");
		System.out.println(msgback);
		return String.valueOf(msgback);
	}
}
