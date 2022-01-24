package com.pavelurusov.bitarray;

import java.util.Arrays;
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
        if (size <= 0) {
            throw new IllegalArgumentException("Size can't be less than or equal to 0");
        }
        byte_count = size / 8 + (size % 8 == 0 ? 0 : 1);
        store = new byte[byte_count];
        this.size = size;
    }

    public BitArray(BitArray bitArray) {
        this.byte_count = bitArray.byte_count;
        this.store = Arrays.copyOf(bitArray.store, bitArray.byte_count);
        this.size = bitArray.size;
    }

    // get the bit at the specified index. 1 = true, 0 = false
    public boolean get(int index) {
        checkIndex(index);
        // the bitwise AND operation will return 1 only if the bit is set
        return (store[index / 8] & (1 << (index % 8))) != 0;
    }

    // set the bit at the specified index
    public void set(int index) {
        checkIndex(index);
        // the bit at the specified index is ORed with 1 to set it
        store[index / 8] |= 1 << (index % 8);
    }

    // set all bits from the specified fromIndex (inclusive) to toIndex (exclusive)
    // throws IllegalArgumentException if (fromIndex > toIndex)
    public void set(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex is higher than toIndex");
        }
        for (int i = fromIndex; i < toIndex; i++) {
            set(i);
        }
    }

    // clear the bit at the specified index
    public void clear(int index) {
        checkIndex(index);
        // the bit at the specified index is ANDed with 0 to clear it
        store[index / 8] &= ALL_SET - (1 << (index % 8));
    }

    // clear all bits from the specified fromIndex (inclusive) to toIndex (exclusive)
    // throws IllegalArgumentException if (fromIndex > toIndex)
    public void clear (int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex is higher than toIndex");
        }
        for (int i = fromIndex; i < toIndex; i++) {
            clear(i);
        }
    }

    // flip the bit at the specified index
    public void flip(int index) {
        checkIndex(index);
        // the bit at the specified index is XORed with 1 to flip it
        store[index / 8] ^= 1 << (index % 8);
    }

    // flip all bits from the specified fromIndex (inclusive) to toIndex (exclusive)
    // throws IllegalArgumentException if (fromIndex > toIndex)
    public void flip(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex is higher than toIndex");
        }
        for (int i = fromIndex; i < toIndex; i++) {
            flip(i);
        }
    }

    // returns the number of bits stored in the BitArray object
    public int getSize() {
        return size;
    }

    // returns the number of bits in the store set to 1
    public int cardinality() {
        int result = 0;
        for (int i = 0; i < size; i++) {
            if (get(i)) {
                result++;
            }
        }
        return result;
    }

    // returns true if the store contains no bits set to 1
    public boolean isEmpty() {
        for (int i = 0; i < byte_count; i++) {
            if (store[byte_count] != 0) {
                return false;
            }
        }
        return true;
    }

    // clears all bits in the store
    public void clearAll() {
        for (int i = 0; i < byte_count; i++) {
            store[i] = 0;
        }
    }

    // sets all bits in the store
    public void setAll() {
        for (int i = 0; i < byte_count; i++) {
            store[i] = ALL_SET;
        }
    }

    // flips all bits in the store
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

    private void checkIndex(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
    }
}