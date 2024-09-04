package primeirob.projeto2.conversorromano.main.factories;

import primeirob.projeto2.conversorromano.data.repositories.LoadAvailableRomanNumeralsRepository;
import primeirob.projeto2.conversorromano.data.usecasesimplementation.ConvertRomanNumeralToDecimalUseCaseImplementation;
import primeirob.projeto2.conversorromano.domain.usecases.ConvertRomanNumeralToDecimalUseCase;
import primeirob.projeto2.conversorromano.infra.MockLoadAvailableRomanNumeralsRepository;
import primeirob.projeto2.conversorromano.presentation.RomanNumeralConverterSwingInterface;

public class RomanNumeralConverterSwingInterfaceFactory {
    public static RomanNumeralConverterSwingInterface makeRomanNumeralConverterSwingInterface() {
        LoadAvailableRomanNumeralsRepository loadAvailableRomanNumeralsRepository = new MockLoadAvailableRomanNumeralsRepository();
        ConvertRomanNumeralToDecimalUseCase convertRomanNumeralToDecimalUseCase = new ConvertRomanNumeralToDecimalUseCaseImplementation(loadAvailableRomanNumeralsRepository);
        return new RomanNumeralConverterSwingInterface(convertRomanNumeralToDecimalUseCase);
    }
}
