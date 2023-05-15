package com.lee;

/**
 * Created by lee on 1/2/16.
 */
public class Pixel {
    int red;
    int green;
    int blue;

    public Pixel(Pixel copy) {
        this.red = copy.red;
        this.green = copy.green;
        this.blue = copy.blue;
    }

    public Pixel(String redText, String greenText, String blueText) {
        red = Integer.parseInt(redText);
        green = Integer.parseInt(greenText);
        blue = Integer.parseInt(blueText);
    }

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Pixel pixel = (Pixel) o;
        return ((red == pixel.red) && (green == pixel.green) && (blue == pixel.blue));
    }

    @Override
    public int hashCode() {
        return red + green + blue;
    }

    @Override
    public String toString() {
        return red + "," + green + "," + blue;
    }
}
