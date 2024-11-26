package com.api.data.usecasesimplementation.pix;

import com.api.data.protocols.pix.IGeneratePixQRCodeRepository;
import com.api.domain.usecases.pix.IGeneratePixQRCodeUseCase;

public class GeneratePixQRCodeUseCaseImplementation implements IGeneratePixQRCodeUseCase {
    private final IGeneratePixQRCodeRepository generatePixQRCodeRepository;

    public GeneratePixQRCodeUseCaseImplementation(IGeneratePixQRCodeRepository generatePixQRCodeRepository) {
        this.generatePixQRCodeRepository = generatePixQRCodeRepository;
    }

    @Override
    public String generatePixQRCode(double amount) {
        return this.generatePixQRCodeRepository.generatePixQRCode(amount);
    }
}
