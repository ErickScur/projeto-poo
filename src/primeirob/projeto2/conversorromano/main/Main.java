package primeirob.projeto2.conversorromano.main;

import primeirob.projeto2.conversorromano.main.factories.RomanNumeralConverterSwingInterfaceFactory;
import primeirob.projeto2.conversorromano.presentation.RomanNumeralConverterSwingInterface;

public class Main {
    public static void main(String[] args) {
        RomanNumeralConverterSwingInterface romanNumeralConverterSwingInterface = RomanNumeralConverterSwingInterfaceFactory.makeRomanNumeralConverterSwingInterface();
        romanNumeralConverterSwingInterface.start();
    }
}
