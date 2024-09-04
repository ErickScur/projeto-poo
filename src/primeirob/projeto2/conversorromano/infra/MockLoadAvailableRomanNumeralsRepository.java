package primeirob.projeto2.conversorromano.infra;

import primeirob.projeto2.conversorromano.data.repositories.LoadAvailableRomanNumeralsRepository;
import primeirob.projeto2.conversorromano.domain.entities.RomanNumeral;

import java.util.List;

public class MockLoadAvailableRomanNumeralsRepository implements LoadAvailableRomanNumeralsRepository {
    private static final List<RomanNumeral> availableRomanNumerals = List.of(
            new RomanNumeral("M", 1000),
            new RomanNumeral("CM", 900),
            new RomanNumeral("D", 500),
            new RomanNumeral("CD", 400),
            new RomanNumeral("C", 100),
            new RomanNumeral("XC", 90),
            new RomanNumeral("L", 50),
            new RomanNumeral("XL", 40),
            new RomanNumeral("X", 10),
            new RomanNumeral("IX", 9),
            new RomanNumeral("V", 5),
            new RomanNumeral("IV", 4),
            new RomanNumeral("I", 1)
    );

    @Override
    public List<RomanNumeral> loadAvailableRomanNumerals() {
        return availableRomanNumerals;
    }
}
