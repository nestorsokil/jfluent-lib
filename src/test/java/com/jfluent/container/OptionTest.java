package com.jfluent.container;

import com.jfluent.container.Option;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Created by nestorsokil on 17.03.2017.
 */
public class OptionTest {
    private static class DTO {
        private int anInt;
        private String aString;
    }

    @Test
    public void testGeneral() throws Exception {
        final PrintStream logger = System.out;
        DTO dto = new DTO();
        dto.anInt = 42;
        dto.aString = "Some string";

        List<String> singleString =
                Option.of(dto)
                        .filter(d -> d.anInt < 50)
                        .ifEmpty(()  -> logger.println("[ERROR] Value's too big"))
                        .ifPresent(d -> logger.println("[INFO] Working with " + d.toString()))
                        .map(d -> d.aString)
                        .filter(s -> s.length() > 20)
                        .or(() -> {
                            DTO other = new DTO();
                            other.anInt = 100;
                            other.aString = "ANOTHER STRING";
                            return other;
                        })
                        .map(d -> d.aString)
                        .stream()
                        .collect(Collectors.toList());
        Assert.assertEquals(1, singleString.size());
        Assert.assertEquals("ANOTHER STRING", singleString.get(0));
    }

    @Test
    public void testLaziness() throws Exception {
        DTO value = new DTO();
        value.anInt = 100;
        Option.of(null).map(o -> value.anInt = 200).orNull();
        Assert.assertTrue(value.anInt == 100);

        Option.of(null).or(() -> value.anInt = 300).orNull();
        Assert.assertTrue(value.anInt == 300);

        Option.of("Some getValue").or(() -> value.anInt = 400).map(i -> value.anInt = 500).orNull();
        Assert.assertTrue(value.anInt == 300);
    }

    @Test(expected = RuntimeException.class)
    public void testException() throws Exception {
        String str = Option.of("str").map(s -> (String) null).orElseThrow(RuntimeException::new);
    }

    @Test(expected = NoSuchElementException.class)
    public void testNone() throws Exception {
        Option.of(null).get();
    }

    @Test
    public void matchesTest() {
        Assert.assertTrue(Option.of(LocalDateTime.now()).matches(d -> d.getYear() > 2000));
    }
}