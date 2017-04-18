package com.jfluent.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.PrintStream;

import static com.jfluent.util.Prelude.*;

/**
 * Created by nestorsokil on 18.04.2017.
 */
@State(Scope.Thread)
public class MaybeBenchmark {

    private class DummyLog {
        public void warn(String msg) { }
    }

    private DummyLog logger;

    @Setup
    public void init() {
        logger = new DummyLog();
    }

    @Benchmark
    public void runWithMaybe() {
        boolean result = maybe((String) null)
                .ifPresent(str -> logger.warn("Value is: " + str))
                .ifEmpty(() -> logger.warn("No value!"))
                .or(() -> "alternative")
                .filter(str -> str.contains("o"))
                .map(String::length)
                .matches(i -> i == 11);
        assert result;
    }

    @Benchmark
    public void runWithoutMaybe() {
        String v = null;
        if(v != null) {
            logger.warn("Value is: " + v);
        } else {
            logger.warn("No value!");
            v = "alternative";
            if(!v.contains("o")) {
                int size = v.length();
                boolean result = size == 11;
                assert result;
            }
        }
    }
}
