package main;

import controller.BuchungenPDF;

public class PDFTest {

    public static void main(String[] args) {
        BuchungenPDF buchungenPDF = new BuchungenPDF();
        buchungenPDF.TextData();
        System.out.println(buchungenPDF);
    }
}