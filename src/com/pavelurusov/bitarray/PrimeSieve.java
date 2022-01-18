package com.pavelurusov.bitarray;

public class PrimeSieve {

    private static int countPrimes(int n) {

        int count = 0;

        BitArray boolSet = new BitArray(n + 1);
        boolSet.setAll();
        boolSet.clear(0);
        boolSet.clear(1);
        for (int i = 2; i <= Math.sqrt(n); i++) {
            int j = i * i;
            while (j <= n) {
                boolSet.clear(j);
                j += i;
            }
        }

        for (Boolean b: boolSet) {
            if (b) {
                count++;
            }
        }

        return count;

    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println("Calculating the number of primes up to " + n);
        System.out.println(countPrimes(n));

    }
}
