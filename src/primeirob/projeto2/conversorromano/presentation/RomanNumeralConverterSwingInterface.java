package primeirob.projeto2.conversorromano.presentation;

import primeirob.projeto2.conversorromano.domain.usecases.ConvertRomanNumeralToDecimalUseCase;

import javax.swing.JOptionPane;

public class RomanNumeralConverterSwingInterface {
    private final ConvertRomanNumeralToDecimalUseCase convertRomanNumeralToDecimalUseCase;

    public RomanNumeralConverterSwingInterface(ConvertRomanNumeralToDecimalUseCase convertRomanNumeralToDecimalUseCase) {
        this.convertRomanNumeralToDecimalUseCase = convertRomanNumeralToDecimalUseCase;
    }

    public void start() {
        while (true) {
            String[] options = {"Sair", "Converter Decimal para Romano"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Escolha uma opção:",
                    "Conversor Decimal para Romano",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            if (choice == 1) {
                String input = JOptionPane.showInputDialog("Digite um número decimal:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int decimal = Integer.parseInt(input);
                        String roman = convertRomanNumeralToDecimalUseCase.convert(decimal);
                        JOptionPane.showMessageDialog(null, "O número romano é: " + roman);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número válido.");
                    }
                }
            } else if (choice == 0) {
                JOptionPane.showMessageDialog(null, "Saindo...");
                break;
            }
        }
    }
}
