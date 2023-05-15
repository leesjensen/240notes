package com.lee;

import java.util.*;
import java.util.concurrent.*;

public class Editor {

    public static Image invert(Image sourceImage) {
        Image newImage = new Image(sourceImage);
        for (ArrayList<Pixel> row : newImage.pixels) {
            for (Pixel pixel : row) {
                pixel.red = 255 - pixel.red;
                pixel.green = 255 - pixel.green;
                pixel.blue = 255 - pixel.blue;
            }
        }

        return newImage;
    }

    public static Image grayscale(Image sourceImage) {
        Image newImage = new Image(sourceImage);
        for (ArrayList<Pixel> row : newImage.pixels) {
            for (Pixel pixel : row) {
                int averageColor = (pixel.red + pixel.green + pixel.blue) / 3;
                pixel.red = averageColor;
                pixel.green = averageColor;
                pixel.blue = averageColor;
            }
        }

        return newImage;
    }

    public static Image emboss(Image sourceImage) {
        Image newImage = new Image(sourceImage);

        for (int rowPos = 0; rowPos < sourceImage.height; rowPos++) {
            for (int colPos = 0; colPos < sourceImage.width; colPos++) {
                int value = 0;
                Pixel sourcePixel = sourceImage.pixels.get(rowPos).get(colPos);
                Pixel newPixel = newImage.pixels.get(rowPos).get(colPos);

                if (colPos != 0 && rowPos != 0) {
                    Pixel relatedPixel = sourceImage.pixels.get(rowPos - 1).get(colPos - 1);
                    int redDiff = sourcePixel.red - relatedPixel.red;
                    int greenDiff = sourcePixel.green - relatedPixel.green;
                    int blueDiff = sourcePixel.blue - relatedPixel.blue;

                    if (Math.abs(redDiff) >= Math.abs(greenDiff) && Math.abs(redDiff) >= Math.abs(blueDiff)) {
                        value = redDiff;
                    }
                    else if (Math.abs(greenDiff) >= Math.abs(redDiff) && Math.abs(greenDiff) >= Math.abs(blueDiff)) {
                        value = greenDiff;
                    }
                    else if (Math.abs(blueDiff) >= Math.abs(greenDiff) && Math.abs(blueDiff) >= Math.abs(redDiff)) {
                        value = blueDiff;
                    }
                }

                value = 128 + Math.max(Math.min(value, 255), 0);

                newPixel.red = value;
                newPixel.green = value;
                newPixel.blue = value;
            }
        }

        return newImage;
    }

    public static Image blur(Image sourceImage, int blurLength) {
        Image newImage = new Image(sourceImage);

        for (int rowPos = 0; rowPos < sourceImage.height; rowPos++) {
            for (int colPos = 0; colPos < sourceImage.width; colPos++) {
                int blurMax = Math.min(blurLength, sourceImage.width - colPos - 1);

                Pixel newPixel = newImage.pixels.get(rowPos).get(colPos);

                for (int blurPos = 0; blurPos < blurMax; blurPos++) {
                    Pixel sourcePixel = sourceImage.pixels.get(rowPos).get(colPos + blurPos + 1);

                    newPixel.red += sourcePixel.red;
                    newPixel.green += sourcePixel.green;
                    newPixel.blue += sourcePixel.blue;
                }

                if (blurMax > 0) {
                    blurMax++;
                    newPixel.red /= blurMax;
                    newPixel.green /= blurMax;
                    newPixel.blue /= blurMax;
                }
            }
        }

        return newImage;
    }
}