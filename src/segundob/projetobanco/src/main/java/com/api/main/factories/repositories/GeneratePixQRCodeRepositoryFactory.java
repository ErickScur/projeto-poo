package com.api.main.factories.repositories;

import com.api.data.protocols.pix.IGeneratePixQRCodeRepository;
import com.api.infra.api.CellcoinAPIRepository;

public class GeneratePixQRCodeRepositoryFactory {
    public static IGeneratePixQRCodeRepository makeGeneratePixQRCodeRepository() {
        return new CellcoinAPIRepository();
    }
}
