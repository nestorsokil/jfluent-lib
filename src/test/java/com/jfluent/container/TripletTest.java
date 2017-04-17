package com.jfluent.container;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by nestorsokil on 17.04.2017.
 */
public class TripletTest {
    @Test
    public void test() {
        Triplet.of("John", "Doe", 42)
                .flatMap((first, last, age) ->
                        Triplet.of("Johnny", "Doe", age + 1))
                .apply((e1,e2,e3) -> {
                    assertThat(e1, is(equalTo("Johnny")));
                    assertThat(e2, is(equalTo("Doe")));
                    assertThat(e3, is(equalTo(43)));
                });
    }
}