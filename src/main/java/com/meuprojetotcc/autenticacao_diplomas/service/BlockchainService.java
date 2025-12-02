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
import java.security.MessageDigest;
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

            byte[] numeroDiplomaBytes = toBytes32(diploma.getNumeroDiploma());

            // CONVERTE hash do documento (hex SHA-256) para bytes32
            byte[] hashDocumentoBytes = hexToBytes32(diploma.getHashBlockchain());


            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashCarimbo = digest.digest(diploma.getCarimboInstituicao());


            byte[] assinaturaBytes = diploma.getAssinaturaInstituicao();

            logger.info("Enviando transação para registrar diploma: {}", diploma.getNumeroDiploma());

            TransactionReceipt receipt = contrato.registrarDiploma(
                    numeroDiplomaBytes,
                    hashDocumentoBytes,
                    hashCarimbo,
                    assinaturaBytes
            ).send();

            logger.info("Transação concluída. Hash: {}, Status: {}",
                    receipt.getTransactionHash(), receipt.getStatus());

            return receipt.getTransactionHash();

        } catch (Exception e) {
            logger.error("Erro inesperado ao registrar diploma: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao registrar diploma", e);
        }
    }


    private byte[] toBytes32(String value) {
        byte[] result = new byte[32];
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        if (bytes.length > 32) {
            throw new RuntimeException("Valor '" + value + "' excede 32 bytes.");
        }

        System.arraycopy(bytes, 0, result, 0, bytes.length);
        return result;
    }

    private byte[] hexToBytes32(String hex) {
        if (hex == null)
            throw new IllegalArgumentException("hashDocumento não pode ser null");

        if (hex.startsWith("0x"))
            hex = hex.substring(2);

        if (hex.length() != 64)
            throw new IllegalArgumentException("hashDocumento deve ter 64 caracteres hexadecimais (32 bytes)");

        byte[] result = new byte[32];
        for (int i = 0; i < 32; i++) {
            result[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return result;
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
