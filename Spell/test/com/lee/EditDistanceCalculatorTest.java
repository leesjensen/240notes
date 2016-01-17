package com.lee;

import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Created by lee on 1/9/16.
 */
public class EditDistanceCalculatorTest {

    private EditDistanceCalculator calc = new EditDistanceCalculator();

    @Test
    public void equals() throws Exception {
        assertEquals(calc.distance("cow", "cow"), 0);
        assertNotEquals(calc.distance("cow", "cw"), 0);
        assertNotEquals(calc.distance("cow", "cowe"), 0);
    }

    @Test
    public void delete1() throws Exception {
        assertEquals(calc.distance("cow", "cw"), 1);
        assertEquals(calc.distance("cow", "ow"), 1);
        assertEquals(calc.distance("cow", "co"), 1);
    }

    @Test
    public void delete2() throws Exception {
        assertEquals(calc.distance("cow", "c"), 2);
        assertEquals(calc.distance("cow", "o"), 2);
        assertEquals(calc.distance("cow", "w"), 2);
    }

    @Test
    public void deleteNot() throws Exception {
        assertEquals(calc.distance("cow", ""), 3);
    }

    @Test
    public void insert1() throws Exception {
        assertEquals(calc.distance("cow", "coow"), 1);
        assertEquals(calc.distance("cow", "ccow"), 1);
        assertEquals(calc.distance("cow", "coww"), 1);
    }

    @Test
    public void insert2() throws Exception {
        assertEquals(calc.distance("cow", "xcxow"), 2);
        assertEquals(calc.distance("cow", "xcowx"), 2);
        assertEquals(calc.distance("cow", "cxoxw"), 2);
    }

    @Test
    public void insert3() throws Exception {
        assertEquals(calc.distance("cow", "xcxowx"), 3);
        assertEquals(calc.distance("cow", "xxcowx"), 3);
        assertEquals(calc.distance("cow", "cxoxxw"), 3);
    }

    @Test
    public void alter1() throws Exception {
        assertEquals(calc.distance("cow", "cxw"), 1);
        assertEquals(calc.distance("cow", "xow"), 1);
        assertEquals(calc.distance("cow", "cox"), 1);
    }

    @Test
    public void alter2() throws Exception {
        assertEquals(calc.distance("cow", "cxx"), 2);
        assertEquals(calc.distance("cow", "xxw"), 2);
        assertEquals(calc.distance("cow", "xox"), 2);
    }

    @Test
    public void alter3() throws Exception {
        assertEquals(calc.distance("cow", "xxx"), 3);
    }

    @Test
    public void transpose1() throws Exception {
        assertEquals(calc.distance("cow", "cwo"), 1);
        assertEquals(calc.distance("cow", "ocw"), 1);
        assertEquals(calc.distance("cow", "cwo"), 1);
    }

    @Test
    public void transpose2() throws Exception {
        assertEquals(calc.distance("cow", "owc"), 2);
        assertEquals(calc.distance("cow", "wco"), 2);
    }

    @Test
    public void transpose3() throws Exception {
        assertEquals(calc.distance("abcdef", "badcfe"), 3);
    }

    @Test
    public void assortedWords() throws Exception {
        assertEquals(calc.distance("kitten", "sitting"), 3);
        assertEquals(calc.distance("lee", "love"), 2);
        assertEquals(calc.distance("love", "pain"), 4);
        assertEquals(calc.distance("lee", "lisa"), 3);
        assertEquals(calc.distance("right", "wrong"), 5);
    }
}