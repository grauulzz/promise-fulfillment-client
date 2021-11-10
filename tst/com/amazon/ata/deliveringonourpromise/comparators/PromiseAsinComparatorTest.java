package com.amazon.ata.deliveringonourpromise.comparators;

import com.amazon.ata.deliveringonourpromise.types.Promise;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PromiseAsinComparatorTest {

    PromiseAsinComparator promiseAsinComparator = new PromiseAsinComparator();

    @Test
    void compareTwoPromisesByAsin_returnsTrue() {

        Promise p1 = Promise.builder().withAsin("123").build();
        Promise p2 = Promise.builder().withAsin("123").build();

        int comp = promiseAsinComparator.compare(p1, p2);
        boolean isNumCheck = isNum(Integer.toString(comp));

        assertTrue(isNumCheck);
    }

    public boolean isNum(String str) {
        return str != null && str.matches("[0-9]+");
    }
}


