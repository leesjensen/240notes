package com.lee;

        import org.testng.Assert;
        import org.testng.annotations.Test;

        import java.io.StringReader;

/**
 * Created by lee on 1/1/16.
 */
public class ImageTest {


    @Test
    public void testLoadVariedSpacingAndNewlines() throws Exception {
        StringReader reader = new StringReader("P3\n# comment\n3 2\n255\n 1 2 3  4  5\t6\t7 8 9\n10 11 12\t13 14 15\t16 17 18\n");
        Image image = new Image(reader);

        Assert.assertEquals(image.height, 2);
        Assert.assertEquals(image.width, 3);
        Assert.assertEquals(image.maxColorValue, 255);
        Assert.assertEquals(image.pixels.get(0).get(0), new Pixel(1,2,3));
        Assert.assertEquals(image.pixels.get(0).get(1), new Pixel(4,5,6));
        Assert.assertEquals(image.pixels.get(0).get(2), new Pixel(7,8,9));

        String imageText = image.toString();
        Assert.assertEquals(imageText, "P3\n3 2\n255\n1 2 3\t4 5 6\t7 8 9\n10 11 12\t13 14 15\t16 17 18\n");
    }

    @Test
    public void testLoadOnlyNewline() throws Exception {
        StringReader reader = new StringReader("P3\n# comment\n3 2\n255\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\t13\n14\n15\n16\n17\n18\n");
        Image image = new Image(reader);

        Assert.assertEquals(image.height, 2);
        Assert.assertEquals(image.width, 3);
        Assert.assertEquals(image.maxColorValue, 255);
        Assert.assertEquals(image.pixels.get(0).get(0), new Pixel(1,2,3));
        Assert.assertEquals(image.pixels.get(0).get(1), new Pixel(4,5,6));
        Assert.assertEquals(image.pixels.get(0).get(2), new Pixel(7,8,9));

        String imageText = image.toString();
        Assert.assertEquals(imageText, "P3\n3 2\n255\n1 2 3\t4 5 6\t7 8 9\n10 11 12\t13 14 15\t16 17 18\n");
    }

    @Test
    public void testInvert() throws Exception {
        Image image = new Image(new StringReader("P3\n2 1\n255\n255 0 127\t240 128 15\n"));

        String imageText = Editor.invert(image).toString();
        Assert.assertEquals(imageText, "P3\n2 1\n255\n0 255 128\t15 127 240\n");
    }

    @Test
    public void testGrayscale() throws Exception {
        Image image = new Image(new StringReader("P3\n1 1\n255\n25 230 122\n"));

        String imageText = Editor.grayscale(image).toString();
        Assert.assertEquals(imageText, "P3\n1 1\n255\n125 125 125\n");
    }

    @Test
    public void testEmboss() throws Exception {
        Image image = new Image(new StringReader("P3\n3 3\n255\n1 1 1\t1 1 1\t1 1 1\n2 2 2\t2 2 2\t2 2 2\n3 3 3\t3 3 3\t3 3 3\n"));

        String imageText = Editor.emboss(image).toString();
        Assert.assertEquals(imageText, "P3\n3 3\n255\n128 128 128\t128 128 128\t128 128 128\n128 128 128\t129 129 129\t129 129 129\n128 128 128\t129 129 129\t129 129 129\n");
    }

    @Test
    public void testEmbossNegative() throws Exception {
        Image image = new Image(new StringReader("P3\n3 3\n255\n" +
                "3 1 2\t255 255 255\t1   1   1\n" +
                "2 2 2\t2   2   2  \t255 254 0\n" +
                "3 3 3\t3   3   3  \t3   3   3\n"));

        String imageText = Editor.emboss(image).toString();
        Assert.assertEquals(imageText, "P3\n3 3\n255\n" +
                "128 128 128\t128 128 128\t128 128 128\n" +
                "128 128 128\t128 128 128\t128 128 128\n" +
                "128 128 128\t129 129 129\t129 129 129\n");
    }

    @Test
    public void testBlur() throws Exception {
        Image image = new Image(new StringReader("P3\n3 1\n255\n10 10 10\t0 0 0\t20 20 20\n"));

        String imageText = Editor.blur(image, 2).toString();
        Assert.assertEquals(imageText, "P3\n3 1\n255\n10 10 10\t10 10 10\t20 20 20\n");
    }
}