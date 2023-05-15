package com.lee;

import java.io.*;
import java.util.*;

/**
 * PPM_File Header MagicNumber Separator Comment Width
 * Height MaxColorValue Pixels
 * Pixel RedColorValue GreenColorValue BlueColorValue Number
 * ::= Header Pixels Separator*
 * ::= MagicNumber Separator Width Separator Height Separator MaxColorValue \s ::= P3
 * ::= \s+ Comment? \s* | Comment \s+
 * ::= #[^\n]*\n
 * ::= \d+
 * ::= \d+
 * ::= 255
 * ::= (Pixel (Separator Pixel)* )?
 * ::= RedColorValue Separator GreenColorValue Separator BlueColorValue
 * ::= Number
 * ::= Number
 * ::= Number
 * ::= [01](\d\d?)? | 2[0-4]\d | 25[0-5] | [3-9]\d?
 */
public class Image {
    int width;
    int height;
    int maxColorValue;
    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();

    private BufferedReader reader;

    public Image(Image copy) {
        this.width = copy.width;
        this.height = copy.height;
        this.maxColorValue = copy.maxColorValue;

        for (ArrayList<Pixel> copyRow : copy.pixels) {
            ArrayList<Pixel> row = new ArrayList<>();
            pixels.add(row);
            for (Pixel copyPixel : copyRow) {
                Pixel pixel = new Pixel(copyPixel);
                row.add(pixel);
            }
        }
    }


    public Image(Reader sourceReader) throws Exception {
        reader = new BufferedReader(sourceReader);

        ReadHeader();
        ReadWidthAndHeight();
        ReadMaxColorValue();
        ReadPixels();
    }

    public static Image loadImage(String filename) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return new Image(reader);
        }
    }

    private void ReadHeader() throws Exception {
        String line = readLine(true);
        if (!line.equalsIgnoreCase(("p3"))) {
            throw new Exception("Invalid header");
        }
    }

    private void ReadWidthAndHeight() throws Exception {
        String line = readLine(true);
        String[] tokens = line.split("\\s");
        if (tokens.length != 2) throw new Exception("Invalid width and height");

        width = Integer.parseInt(tokens[0]);
        height = Integer.parseInt(tokens[1]);
    }

    private void ReadMaxColorValue() throws Exception {
        String line = readLine(true);

        maxColorValue = Integer.parseInt(line);
        if (maxColorValue != 255) throw new Exception("Invalid max color value. Must be 255");
    }

    private void ReadPixels() throws Exception {
        int currentRow = 0;

        while (currentRow < height) {
            int currentColumn = 0;

            ArrayList<Pixel> row = new ArrayList<>();
            pixels.add(row);

            while (currentColumn < width) {
                row.add(new Pixel(readNextInt(), readNextInt(), readNextInt()));

                currentColumn++;
            }

            currentRow++;
        }
    }

    private int readNextInt() throws Exception {
        int byteValue = 32;

        while (byteValue != -1 && Character.isWhitespace(byteValue)) {
            byteValue = reader.read();
        }

        if (byteValue == -1) throw new Exception("Integer not found when expected");

        StringBuilder buffer = new StringBuilder();

        while (byteValue != -1 && !Character.isWhitespace(byteValue)) {
            buffer.append((char)byteValue);

            byteValue = reader.read();
        }

        return Integer.parseInt(buffer.toString());
    }


    private String readLine(boolean throwIfEmpty) throws Exception {
        String line = null;
        if (reader != null) {
            line = reader.readLine();
            while (line != null && (line.length() == 0 || line.trim().startsWith(("#")))) {
                line = reader.readLine();
            }
        }

        if (throwIfEmpty && line == null) throw new Exception("Missing expected field");

        return line;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("P3\n");
        buffer.append(String.format("%d %d\n", width, height));
        buffer.append("255\n");

        for (ArrayList<Pixel> row : pixels) {
            for (Pixel pixel : row) {
                buffer.append(String.format("%d %d %d\t", pixel.red, pixel.green, pixel.blue));
            }

            buffer.deleteCharAt(buffer.length() - 1);
            buffer.append("\n");
        }

        return buffer.toString();
    }

}