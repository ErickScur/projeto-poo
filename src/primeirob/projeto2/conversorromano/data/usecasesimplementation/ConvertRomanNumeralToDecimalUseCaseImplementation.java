package primeirob.projeto2.conversorromano.data.usecasesimplementation;

import primeirob.projeto2.conversorromano.data.repositories.LoadAvailableRomanNumeralsRepository;
import primeirob.projeto2.conversorromano.domain.entities.RomanNumeral;
import primeirob.projeto2.conversorromano.domain.usecases.ConvertRomanNumeralToDecimalUseCase;

import java.util.List;

public class ConvertRomanNumeralToDecimalUseCaseImplementation implements ConvertRomanNumeralToDecimalUseCase {
    private final LoadAvailableRomanNumeralsRepository loadAvailableRomanNumeralsRepository;

    public ConvertRomanNumeralToDecimalUseCaseImplementation(LoadAvailableRomanNumeralsRepository loadAvailableRomanNumeralsRepository) {
        this.loadAvailableRomanNumeralsRepository = loadAvailableRomanNumeralsRepository;
    }

    @Override
    public String convert(int number) {
        List<RomanNumeral> romanNumerals = loadAvailableRomanNumeralsRepository.loadAvailableRomanNumerals();
        validateRomanNumeral(number);

        StringBuilder convertedNumeral = new StringBuilder();

        while (number > 0) {
            for (RomanNumeral roman : romanNumerals) {
                if (number - roman.decimalValue() >= 0) {
                    convertedNumeral.append(roman.romanNumeral());
                    number -= roman.decimalValue();
                    break;
                }
            }
        }

        return convertedNumeral.toString();
    }

    private void validateRomanNumeral(int romanNumeral) {
        if (romanNumeral < 1 || romanNumeral > 3999) {
            throw new NumberFormatException("Roman numeral must be between 1 and 3999");
        }
    }
}
