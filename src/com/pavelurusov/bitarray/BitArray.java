package com.pavelurusov.bitarray;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BitArray implements Iterable<Boolean> {
    // in Java, all numeric data types are signed,
    // with negative numbers stored as 2's complement.
    // Therefore, 11111111b = -1d
    private static final byte ALL_SET = -1;
    private final byte[] store;
    private final int size;
    private final int byte_count;

    public BitArray(int size) {
        byte_count = size / 8 + (size % 8 == 0 ? 0 : 1);
        store = new byte[byte_count];
        this.size = size;
    }

    // get the bit at the specified position. 1 = true, 0 = false
    public boolean get(int position) {
        checkPosition(position);
        // the bitwise AND operation will return 1 only if the bit is set
        return (store[position / 8] & (1 << (position % 8))) != 0;
    }

    // set the bit at the specified position
    public void set(int position) {
        checkPosition(position);
        // the bit at the specified position is ORed with 1 to set it
        store[position / 8] |= 1 << (position % 8);
    }

    // clear the bit at the specified position
    public void clear(int position) {
        checkPosition(position);
        // the bit at the specified position is ANDed with 0 to clear it
        store[position / 8] &= ALL_SET - (1 << (position % 8));
    }

    // flip the bit at the specified position
    public void flip(int position) {
        checkPosition(position);
        // the bit at the specified position is XORed with 1 to flip it
        store[position / 8] ^= 1 << (position % 8);
    }

    public int getSize() {
        return size;
    }

    public void clearAll() {
        for (int i = 0; i < byte_count; i++) {
            store[i] = 0;
        }
    }

    public void setAll() {
        for (int i = 0; i < byte_count; i++) {
            store[i] = ALL_SET;
        }
    }

    public void flipAll() {
        for (int i = 0; i < byte_count; i++) {
            store[i] = (byte) ~store[i];
        }
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new Iterator<Boolean>() {
            private int position = 0;
            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public Boolean next() {
                if (hasNext()) {
                    position++;
                    return get(position - 1);
                } else throw new NoSuchElementException();
            }
        };
    }

    private void checkPosition (int position) {
        if(position < 0 || position >= size) {
            throw new IndexOutOfBoundsException(String.valueOf(position));
        }
    }
}