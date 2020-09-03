package edu.eci.arep.sparkD2.examples;

import edu.eci.arep.sparkD2.sparkD;

/**
 * The type Sparkd example.
 */
public class SparkDExample {
    /**
     * An entry point of application for example purposes.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        sparkD.get("/hello",(req,res)->"hello World");
    }
}
