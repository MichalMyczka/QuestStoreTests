package org.example.services;

public class DecoderURL {

    public static String polishDecoder(String string){
        return string.replace("%20", " ")
                .replace("%C5%82", "ł")
                .replace("%C4%87","ć")
                .replace("%C4%86", "Ć")
                .replace("%C5%81", "Ł")
                .replace("%C5%84", "ń")
                .replace("%C5%84", "Ń")
                .replace("%C4%99","ę")
                .replace("%C4%85", "ą")
                .replace("%C3%B3","ó")
                .replace("%C5%BB", "Ż")
                .replace("%C5%BC", "ż")
                .replace("%C5%B9", "Ź")
                .replace("%C5%BA", "ź")
                .replace("%C5%9B", "ś");
    }
}
