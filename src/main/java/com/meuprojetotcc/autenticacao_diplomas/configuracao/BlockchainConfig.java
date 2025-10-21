/*package com.meuprojetotcc.autenticacao_diplomas.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class BlockchainConfig {

    @Bean
    public Web3j web3j() {
        // Conectando ao Ganache local
        String ganacheUrl = "http://127.0.0.1:7545";
        Web3j web3j = Web3j.build(new HttpService(ganacheUrl));

        System.out.println("✅ Conectado à blockchain local (Ganache): " + ganacheUrl);
        return web3j;
    }
}*/