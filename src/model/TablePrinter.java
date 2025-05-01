package model;

import java.util.ArrayList;

public class TablePrinter {
    public static void printTable(String[] headers, ArrayList<Object[]> rows) {
        int[] colWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
        }
        for (Object[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null) {
                    int len = String.valueOf(row[i]).length();
                    if (len > colWidths[i])
                        colWidths[i] = len;
                }
            }
        }
        printDivider(colWidths);
        System.out.print("|");
        for (int i = 0; i < headers.length; i++) {
            System.out.printf(" %-" + colWidths[i] + "s |", headers[i]);
        }
        System.out.println();
        printDivider(colWidths);
        for (Object[] row : rows) {
            System.out.print("|");
            for (int i = 0; i < row.length; i++) {
                String cell;
                if (row[i] != null) {
                    cell = String.valueOf(row[i]);
                } else {
                    cell = "";
                }
                System.out.printf(" %-" + colWidths[i] + "s |", cell);
            }
            System.out.println();
        }
        printDivider(colWidths);
    }

    private static void printDivider(int[] colWidths) {
        System.out.print("+");
        for (int width : colWidths) {
            System.out.print("-".repeat(width + 2));
            System.out.print("+");
        }
        System.out.println();
    }
}
