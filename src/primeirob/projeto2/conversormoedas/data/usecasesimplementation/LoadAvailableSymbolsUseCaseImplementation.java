package primeirob.projeto2.conversormoedas.data.usecasesimplementation;

import primeirob.projeto2.conversormoedas.data.protocols.LoadAvailableSymbolsRepository;
import primeirob.projeto2.conversormoedas.domain.usecases.LoadAvailableSymbolsUseCase;

import java.util.List;

public class LoadAvailableSymbolsUseCaseImplementation implements LoadAvailableSymbolsUseCase {
    private final LoadAvailableSymbolsRepository repository;

    public LoadAvailableSymbolsUseCaseImplementation(LoadAvailableSymbolsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> loadAvailableSymbols() {
        return this.repository.loadAvailableSymbols();
    }
}
