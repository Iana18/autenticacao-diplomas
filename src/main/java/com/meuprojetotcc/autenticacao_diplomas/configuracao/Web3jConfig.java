package com.meuprojetotcc.autenticacao_diplomas.configuracao;

import com.meuprojetotcc.autenticacao_diplomas.contracts.src.main.java.com.meuprojetotcc.autenticacao_diplomas.contracts.DiplomaRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class Web3jConfig {

    @Value("${ganache.url}")
    private String ganacheUrl;

    @Value("${ganache.admin.private-key}")
    private String adminPrivateKey;

    @Value("${ganache.emissor.private-key}")
    private String emissorPrivateKey;

    @Value("${ganache.ministerio.private-key}")
    private String ministerioPrivateKey;

    @Value("${blockchain.contractAddress}")
    private String contratoEndereco; // endereço do contrato no application.properties

    // ================= Beans principais =================
    @Bean
    public Web3j web3j() {
        Web3j web3j = Web3j.build(new HttpService(ganacheUrl));
        System.out.println("✅ Conectado à blockchain local (Ganache): " + ganacheUrl);
        return web3j;
    }

    @Bean
    public Credentials adminCredentials() {
        return Credentials.create(adminPrivateKey);
    }

    @Bean
    public Credentials emissorCredentials() {
        return Credentials.create(emissorPrivateKey);
    }

    @Bean
    public Credentials ministerioCredentials() {
        return Credentials.create(ministerioPrivateKey);
    }

    @Bean
    public ContractGasProvider contractGasProvider() {
        BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(100_000_000L);    // 100 milhões
        return new StaticGasProvider(gasPrice, gasLimit);
    }

    // ================= Bean do contrato =================
    @Bean
    public DiplomaRegistry diplomaRegistry(Web3j web3j, Credentials emissorCredentials, ContractGasProvider gasProvider) throws Exception {
        DiplomaRegistry contrato = DiplomaRegistry.load(
                contratoEndereco, // usa a string do application.properties
                web3j,
                emissorCredentials,
                gasProvider
        );
        System.out.println("✅ DiplomaRegistry conectado no endereço: " + contrato.getContractAddress());
        return contrato;
    }



    // ================= Getter para o endereço do contrato =================
    public String getContratoEndereco() {
        return contratoEndereco;
    }
}
