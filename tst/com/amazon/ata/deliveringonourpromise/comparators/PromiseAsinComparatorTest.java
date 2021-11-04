package com.amazon.ata.deliveringonourpromise.comparators;

import com.amazon.ata.deliveringonourpromise.types.Promise;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PromiseAsinComparatorTest {

    PromiseAsinComparator promiseAsinComparator = new PromiseAsinComparator();

    @Test
    void compareTwoPromises_returnsTrue() {

        Promise p1 = Promise.builder().withAsin("123").build();
        Promise p2 = Promise.builder().withAsin("123").build();
        promiseAsinComparator.compare(p1, p2);

        boolean isNum = promiseAsinComparator.isNum(p1.getAsin()) && p1.getAsin().matches("[0-9]+");

        assertTrue(isNum);


    }

}


