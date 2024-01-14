package uz.pdp.util;

import java.util.Scanner;

public interface Input {
    Scanner scannerDub = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);

    static Integer inputNum(String msg) {
    Scanner SCANNER_INT = new Scanner(System.in);
        System.out.print(msg + ":");
        if(SCANNER_INT.hasNextInt()){
            return SCANNER_INT.nextInt();
        }else {

        return inputNum(msg);
        }
    }

    static String inputStr(String msg) {
        System.out.print(msg + ":");
        return scannerStr.nextLine();
    }

    static double inputDob(String msg) {
        System.out.print(msg + ":");
        return scannerDub.nextDouble();
    }



}


