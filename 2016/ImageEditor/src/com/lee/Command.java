package com.lee;

/**
 * Created by lee on 1/1/16.
 */
public class Command {
    String inputFile;
    String outputFile;
    String operation = null;
    int blur = 0;

    public Command(String[] args) {

        if (args.length >= 3 && args.length <= 4) {
            inputFile = args[0];
            outputFile = args[1];
            operation = args[2].toLowerCase();

            if (operation.equals("motionblur") || operation.equals("grayscale") || operation.equals("emboss") || operation.equals("invert")) {
                if (args.length == 4) {
                    if (operation.equals("motionblur")) {
                        blur = Integer.parseInt(args[3]);
                    } else {
                        operation = null;
                    }
                }
            } else {
                operation = null;
            }
        }
    }

    public String toString() {
        if (operation != null) {
            if (operation.equals("motionblur")) {
                return operation + " " + blur;
            } else {
                return operation;
            }
        }

        return "";
    }
}
