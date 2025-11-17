package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.contracts.src.main.java.com.meuprojetotcc.autenticacao_diplomas.contracts.DiplomaRegistry;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.gas.ContractGasProvider;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class BlockchainService {

    private static final Logger logger = LoggerFactory.getLogger(BlockchainService.class);

    private final Web3j web3j;
    private final Credentials emissorCredentials;
    private final ContractGasProvider gasProvider;
    private final DiplomaRegistry contrato;

    public BlockchainService(Web3j web3j,
                             Credentials emissorCredentials,
                             ContractGasProvider gasProvider,
                             DiplomaRegistry contrato) {
        this.web3j = web3j;
        this.emissorCredentials = emissorCredentials;
        this.gasProvider = gasProvider;
        this.contrato = contrato;

        logger.info("Conexão com contrato DiplomaRegistry no endereço {} estabelecida.", contrato.getContractAddress());
    }

    // Resto do código permanece igual...
    public String registrarDiploma(Diploma diploma) {
        try {
            byte[] numeroDiplomaBytes = stringToBytes(diploma.getNumeroDiploma(), "numeroDiploma");
            byte[] hashDocumentoBytes = stringToBytes(diploma.getHashBlockchain(), "hashDocumento");
            byte[] carimboBytes = base64ToBytes(diploma.getCarimboInstituicao(), "carimboInstituicao");
            byte[] assinaturaBytes = base64ToBytes(diploma.getAssinaturaInstituicao(), "assinaturaInstituicao");

            logger.info("Enviando transação para registrar diploma: {}", diploma.getNumeroDiploma());

            TransactionReceipt receipt = contrato.registrarDiploma(
                    numeroDiplomaBytes,
                    hashDocumentoBytes,
                    carimboBytes,
                    assinaturaBytes
            ).send();

            logger.info("Transação concluída. Hash: {}, Status: {}",
                    receipt.getTransactionHash(), receipt.getStatus());

            return receipt.getTransactionHash();
        } catch (TransactionException e) {
            logger.error("Erro ao enviar transação para o blockchain: {}", e.getMessage(), e);
            throw new RuntimeException("Falha na transação blockchain", e);
        } catch (Exception e) {
            logger.error("Erro inesperado ao registrar diploma: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao registrar diploma", e);
        }
    }

    private byte[] stringToBytes(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " não pode ser nulo");
        }
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > 32) {
            logger.warn("O campo {} excede 32 bytes. Certifique-se que o contrato aceita bytes dinâmico.", fieldName);
        }
        return bytes;
    }

    private byte[] base64ToBytes(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            return new byte[0];
        }
        try {
            return Base64.getDecoder().decode(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Falha ao decodificar Base64 para " + fieldName, e);
        }
    }
}
