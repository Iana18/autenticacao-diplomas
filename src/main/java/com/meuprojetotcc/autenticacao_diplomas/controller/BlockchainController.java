package com.meuprojetotcc.autenticacao_diplomas.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

@RestController
@RequestMapping("/api/blockchain")
@CrossOrigin(origins = "*")
public class BlockchainController {

    private final Web3j web3j;

    public BlockchainController() {
        // Conecta diretamente ao Ganache local
        this.web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));
    }

    @GetMapping("/status")
    public String status() {
        try {
            Web3ClientVersion clientVersion = web3j.web3ClientVersion().send();
            return "🟢 Conectado à blockchain local: " + clientVersion.getWeb3ClientVersion();
        } catch (Exception e) {
            return "🔴 Erro ao conectar à blockchain: " + e.getMessage();
        }
    }
}
