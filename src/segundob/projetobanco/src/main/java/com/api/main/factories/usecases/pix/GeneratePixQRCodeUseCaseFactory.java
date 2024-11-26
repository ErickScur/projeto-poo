package com.api.main.factories.usecases.pix;

import com.api.data.usecasesimplementation.pix.GeneratePixQRCodeUseCaseImplementation;
import com.api.domain.usecases.pix.IGeneratePixQRCodeUseCase;
import com.api.main.factories.repositories.GeneratePixQRCodeRepositoryFactory;

public class GeneratePixQRCodeUseCaseFactory {
    public static IGeneratePixQRCodeUseCase makeGeneratePixQRCodeUseCase() {
        return new GeneratePixQRCodeUseCaseImplementation(GeneratePixQRCodeRepositoryFactory.makeGeneratePixQRCodeRepository());
    }
}
