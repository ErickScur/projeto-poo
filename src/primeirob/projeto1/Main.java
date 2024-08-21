package primeirob.projeto1;

import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] separadoresValidos = {",", ";", "|"};

    public static void main(String[] args) {
        String nomeArquivo = lerNomeArquivo();
        String separador = lerSeparador();

        ArrayList<String> colunas = lerColunas();
        ArrayList<ArrayList<String>> linhas = lerLinhas(colunas);

        String dados = formatarDados(colunas, linhas, separador);
        gravarPlanilha(nomeArquivo, dados);
    }

    private static String lerNomeArquivo() {
        System.out.println("Digite o nome do arquivo:");

        do {
            String nomeArquivo = scanner.nextLine();

            if (nomeArquivo.matches("^[a-zA-Z0-9]*$")) {
                return nomeArquivo;
            }

            System.out.println("Nome de arquivo inválido! Digite um nome de arquivo válido, sem extensões, caracteres especiais ou espaços:");
        } while (true);
    }

    private static String lerSeparador() {
        System.out.println("Digite o separador desejado:");
        do {
            String separador = scanner.nextLine();

            for (String separadorValido : separadoresValidos) {
                if (separador.equals(separadorValido)) {
                    return separador;
                }
            }

            System.out.println("Separador inválido! Digite um separador válido: ',', ';', '|'");
        } while (true);
    }

    private static ArrayList<String> lerColunas() {
        char opcao = 's';
        int contador = 0;
        ArrayList<String> colunas = new ArrayList<>();

        while (opcao != 'n') {
            System.out.println("Digite o nome da coluna:");
            String coluna = scanner.nextLine();
            colunas.add(coluna);

            System.out.println("Coluna adicionada com sucesso!");
            contador++;

            if (contador >= 3) {
                System.out.println("Deseja adicionar mais uma coluna? [s/n]");
                opcao = scanner.next().charAt(0);
                scanner.nextLine();
            }
        }

        return colunas;
    }

    private static ArrayList<ArrayList<String>> lerLinhas(ArrayList<String> colunas) {
        char opcao = 's';
        ArrayList<ArrayList<String>> linhas = new ArrayList<>();

        while (opcao != 'n') {
            ArrayList<String> linha = new ArrayList<>();

            for (String coluna : colunas) {
                System.out.println("Digite o valor da coluna " + coluna + ":");
                String valor = scanner.nextLine();
                linha.add(valor);
            }

            linhas.add(linha);

            System.out.println("Deseja adicionar mais uma linha? [s/n]");
            opcao = scanner.next().charAt(0);
            scanner.nextLine();
        }

        return linhas;
    }

    private static String formatarDados(ArrayList<String> colunas, ArrayList<ArrayList<String>> linhas, String separador) {
        StringBuilder dados = new StringBuilder();

        for (int i = 0; i < colunas.size(); i++) {
            dados.append(colunas.get(i));
            if (i < colunas.size() - 1) {
                dados.append(separador);
            }
        }

        dados.append("\n");

        for (ArrayList<String> linha : linhas) {
            for (int i = 0; i < linha.size(); i++) {
                dados.append(linha.get(i));
                if (i < linha.size() - 1) {
                    dados.append(separador);
                }
            }
            dados.append("\n");
        }

        return dados.toString();
    }

    private static void gravarPlanilha(String nomeArquivo, String dados) {
        Path path = Paths.get(nomeArquivo + ".csv");

        if (Files.exists(path)) {
            System.out.println("Arquivo já existe! Deseja sobrescrever? [s/n]");
            char opcao = scanner.next().charAt(0);
            scanner.nextLine();

            if (opcao == 'n') {
                System.out.println("Arquivo não foi criado!");
                return;
            }
        }

        try {
            Files.write(path, dados.getBytes(), StandardOpenOption.CREATE);
            System.out.println("Planilha criada com sucesso no diretório: " + path.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Erro ao criar planilha: " + e.getMessage());
        }
    }
}
