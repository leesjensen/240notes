package com.lee;

import java.io.*;

public class ImageEditor {
    public static void main(String[] args) {
        try {
            Command command = new Command(args);

            Image image = Image.loadImage(command.inputFile);
            if (command.operation != null) {
                Image outImage = null;

                if (command.operation.equals("invert")) {
                    outImage = Editor.invert(image);
                }
                else if (command.operation.equals("grayscale")) {
                    outImage = Editor.grayscale(image);
                }
                else if (command.operation.equals("emboss")) {
                    outImage = Editor.emboss(image);
                }
                else if (command.operation.equals("motionblur")) {
                    outImage = Editor.blur(image, command.blur);
                }

                if (outImage != null) {
                    System.out.println("Creating file " + command.outputFile);

                    try (FileWriter writer = new FileWriter(command.outputFile)) {
                        writer.write(outImage.toString());
                    }
                }

            } else {
                System.out.println("ImageEditor inputFileName outputFileName {grayscale|invert|emboss|motionblur blurLength}");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
        }
    }

}