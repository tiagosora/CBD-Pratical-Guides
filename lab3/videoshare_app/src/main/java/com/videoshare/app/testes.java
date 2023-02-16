package com.videoshare.app;
public class testes {
    public static void main(String[] args) {
        String tagString = "{'";
        String[] a = {"a", "b", "papawaraanebete"};
        String b = String.join("'','", a);
        tagString = tagString + b + "'}";
        System.out.println(tagString);
    }
}
