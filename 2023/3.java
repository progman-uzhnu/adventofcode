package org.example;

import java.awt.*;
import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static char[][] elements;

    public static void main(String[] args) {
        Path filePath = Paths.get("inp.txt");
        String text = null;
        try {
            text = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] ll = text.trim().split("\n");
        elements = new char[ll.length][ll[0].length()];

        int sum = 0;

        List<Element> els = new ArrayList<>();

        int counter = 0;
        int num = 0;

        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[0].length; j++) {
                elements[i][j] = ll[i].charAt(j);
            }
        }

        int[] tempCords = null;

        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[0].length; j++) {
                if (isdigit(elements[i][j])) {
                    if (counter != 1) {
                        tempCords = checkIfValid(j, i);
                        if (tempCords != null) {
                            counter = 1;
                        }
                    }

                    num = num * 10 + Integer.parseInt(String.valueOf(elements[i][j]));
                } else {
                    if (counter == 1) {
                        sum += num;
                        els.add(new Element(num, tempCords[0], tempCords[1]));
                    }

                    tempCords = null;
                    num = 0;
                    counter = 0;
                }
            }
        }

        int result = 0;

        for (int i = 0; i < els.size(); i++) {
            for (int j = i + 1; j < els.size(); j++) {
                Element e1 = els.get(i);
                Element e2 = els.get(j);

                if (e1.x == e2.x && e1.y == e2.y && !e1.equals(e2)) {
                    result += e1.val * e2.val;
                }
            }
        }

        System.out.println(result);
    }

    public static int[] checkIfValid(int x, int y) {
        int[][] moves = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};

        for (int[] move : moves) {
            int mX = move[0] + x;
            int mY = move[1] + y;

            if (moveCheck(mX, mY)) {
                char curr = elements[mY][mX];
                if (curr == '*') {
                    return new int[] {mX, mY};
                }
            }
        }

        return null;
    }

    public static boolean moveCheck(int x, int y) {
        return x >= 0 && x < elements.length && y >= 0 && y < elements[0].length;
    }

    public static boolean isdigit(char c) {
        return c >= '0' && c <= '9';
    }
}

class Element {
    int val;
    int x;
    int y;

    public Element(int val, int x, int y) {
        this.val = val;
        this.x = x;
        this.y = y;
    }
}

