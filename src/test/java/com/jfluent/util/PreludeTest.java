package com.jfluent.util;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
import static com.jfluent.util.Prelude.*;

/**
 * Created by nestorsokil on 18.04.2017.
 */
public class PreludeTest {

    @Test
    public void test() {
        maybe(mapOf(
                pair(1, "One"),
                pair(2, "Two"),
                pair(3, "Three")))
                .ifPresent(System.out::print)
                .or(HashMap::new);
    }

}