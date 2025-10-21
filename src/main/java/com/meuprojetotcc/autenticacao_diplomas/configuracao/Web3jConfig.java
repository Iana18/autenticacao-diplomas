package com.meuprojetotcc.autenticacao_diplomas.configuracao;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

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

    // ✅ Forma correta de testar a conexão
    @Bean
    public CommandLineRunner testBlockchainConnection(Web3j web3j) {
        return args -> {
            Web3ClientVersion clientVersion = web3j.web3ClientVersion().send();
            System.out.println("🚀 Blockchain client version: " + clientVersion.getWeb3ClientVersion());
        };
    }
}
