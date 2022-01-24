package com.pavelurusov.bitarray;

public class PrimeSieve {

    private static int countPrimes(int n) {

        BitArray boolSet = new BitArray(n + 1);
        boolSet.setAll();
        boolSet.clear(0, 2);
        for (int i = 2; i <= Math.sqrt(n); i++) {
            int j = i * i;
            while (j <= n) {
                boolSet.clear(j);
                j += i;
            }
        }

        return boolSet.cardinality();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println("Calculating the number of primes up to " + n);
        System.out.println(countPrimes(n));
    }
}
