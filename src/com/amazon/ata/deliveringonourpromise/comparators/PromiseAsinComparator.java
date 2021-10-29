package com.amazon.ata.deliveringonourpromise.comparators;

import com.amazon.ata.deliveringonourpromise.types.Promise;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

/**
 * The type Promise asin comparator.
 */
public class PromiseAsinComparator implements Comparator<Promise>, Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @Override
    public int compare(Promise o1, Promise o2) {

        String p1 = o1.getAsin().substring(1);
        String p2 = o2.getAsin().substring(1);

        if (isNum(p1) && isNum(p2)) {

            int compare1 = Integer.parseInt(p1);
            int compare2 = Integer.parseInt(p2);

            return compare1 - compare2;
        }

        return 0;
    }

    public boolean isNum(String str) {
        return str != null && str.matches("[0-9]+");
    }
}
