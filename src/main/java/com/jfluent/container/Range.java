package com.jfluent.container;

import java.util.Iterator;

/**
 * Created by nestorsokil on 18.04.2017.
 */
public class Range implements Iterable<Long> {
    private long from;
    private long to;

    private Range(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Iterator<Long> iterator() {
        return new Iterator<Long>() {
            @Override
            public boolean hasNext() {
                return from < to;
            }

            @Override
            public Long next() {
                return from ++;
            }
        };
    }

    public static Range range(long from, long to) {
        return new Range(from, to);
    }
    private static class RangeIncomplete {

        private long start;

        public RangeIncomplete(long start) {
            this.start = start;
        }
        public Range to(long end) {
            return new Range(start, end);
        }

    }

    public static RangeIncomplete from(long start) {
        return new RangeIncomplete(start);
    }
}
