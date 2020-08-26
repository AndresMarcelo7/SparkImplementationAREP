package edu.eci.arep.sparkD2.examples;

import edu.eci.arep.sparkD2.sparkD;

public class SparkDExample {
    public static void main(String[] args) {
        sparkD.get("/hello",(req,res)->"hello World");
    }
}
