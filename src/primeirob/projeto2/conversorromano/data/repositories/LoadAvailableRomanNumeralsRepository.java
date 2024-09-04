package primeirob.projeto2.conversorromano.data.repositories;

import primeirob.projeto2.conversorromano.domain.entities.RomanNumeral;

import java.util.List;

public interface LoadAvailableRomanNumeralsRepository {
    List<RomanNumeral> loadAvailableRomanNumerals();
}
