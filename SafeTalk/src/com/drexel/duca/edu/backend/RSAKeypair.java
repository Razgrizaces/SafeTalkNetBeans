package com.drexel.duca.backend;

import java.util.Scanner;

public class RSAKeypair {
	private int e, d, c;

	public RSAKeypair() {
		/*
		 * get 2 prime numbers; a,b. C=a*b, m = totient(c) = (a-1)(b-1) e = any
		 * number coprime to M. d=modular inverse of e%m r^(totient(m)-1) % m
		 * public key is e, C. private key is d, C
		 */
		int a = getNthPrime((int) (Math.random() * 100));
		int b = getNthPrime((int) (Math.random() * 100));
		c = a * b;
		int m = (a - 1) * (b - 1);
		e = getCoprime(m);
		d = modulo(e, (totient(m) - 1), m);
	}
	public RSAKeypair(int e, int c) {
		this.e = e;
		this.c = c;
		this.d = 0;
	}
	public int getC()
	{
	    return c;
	}
	public int getD()
    {
        return d;
    }
	public int getE()
    {
        return e;
    }
	
	public static int getNthPrime(int n) {
		int p = 2;
		int out = 0;
		while (out < n) {
			p++;
			if (isPrime(p)) {
				out++;
			}
		}
		return p;
	}

	public static boolean isPrime(int n) {
		boolean output = true;
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				output = false;
			}

		}
		return output;
	}

	public static int GCD(int a, int b) {
		if (b == 0)
			return a;
		return GCD(b, a % b);
	}

	public static int getCoprime(int n) {
		int coprime = -1;
		for (int i = 1; i < 3 * n; i++) {
			if (GCD(i, n) == 1)
				coprime = i;
		}
		return coprime;
	}

	// count all numbers coprime to n, from 1 to n-1
	public static int totient(int n) {
		int count = 0;
		for (int i = 1; i < n; i++) {
			if (GCD(n, i) == 1)
				count++;
		}
		return count;
	}

	public static int modulo(int a, int b, int c) {
		long x = 1;
		long y = a;
		while (b > 0) {
			if (b % 2 == 1)
				x = (x * y) % c;
			y = (y * y) % c;
			b /= 2;
		}
		return (int) x % c;
	}

	// @param word is any string.
	// E is any number coprime to M
	// c is a*b.
	public String encrypt(String word) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			int let = word.charAt(i);
			int enc = modulo(let, e, c);
			sb.append(enc);
			sb.append(" ");
		}
		return sb.toString();
	}

	// @Param
	// int[] encrypted - an array of encrypted letters, numbers, or symbols.
	// int d = private key
	// int c = private key
	public String decrypt(String encrypted) {
		StringBuilder sb = new StringBuilder();
		Scanner s = new Scanner(encrypted);
		while (s.hasNextInt()) {
			int num = s.nextInt();
			int dec = modulo(num, d, c);
			char ch = (char) dec;
			sb.append(ch);
		}
		return sb.toString();
	}
	
}
