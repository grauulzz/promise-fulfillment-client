package com.amazon.ata.deliveringonourpromise.comparators;

import com.amazon.ata.deliveringonourpromise.types.Promise;

import java.io.Serializable;
import java.util.Comparator;

/**
 * The type Promise asin comparator.
 */
public class PromiseAsinComparator implements Comparator<Promise>, Serializable {


    Comparator<Promise> comp = Comparator.comparing(o -> o.getAsin().substring(1));

    @Override
    public int compare(Promise o1, Promise o2) {
        return comp.compare(o1, o2);
    }


}
