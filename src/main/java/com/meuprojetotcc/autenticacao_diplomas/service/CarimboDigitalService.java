package com.meuprojetotcc.autenticacao_diplomas.service;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.imageio.ImageIO;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import org.springframework.stereotype.Service;

@Service
public class CarimboDigitalService {

    public byte[] gerarCarimboComQRCode(Diploma diploma) throws Exception {
        int largura = 450;
        int altura = 220;

        BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imagem.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fundo e borda
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, largura, altura);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(1, 1, largura - 2, altura - 2);

        // Texto
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(diploma.getInstituicao().getNome(), 20, 35);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Diploma Nº: " + diploma.getNumeroDiploma(), 20, 65);
        g.drawString("Emitido em: " + diploma.getDataEmissao().toLocalDate(), 20, 85);
        g.drawString("Emitido por: " + diploma.getCriadoPor().getNome(), 20, 105);

        // Hash digital
        String hash = gerarHash(diploma);
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.drawString("Carimbo Digital:", 20, 135);
        g.drawString(hash.substring(0, 32) + "...", 20, 150);

        // QR Code
        String urlValidacao = "https://validar.ies.br/diploma/" + diploma.getNumeroDiploma() + "?hash=" + hash;
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = qrWriter.encode(urlValidacao, BarcodeFormat.QR_CODE, 100, 100);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
        g.drawImage(qrImage, largura - 120, altura - 120, null);

        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem, "png", baos);

        return baos.toByteArray();
    }

    public String gerarHash(Diploma diploma) throws Exception {
        String dados = diploma.getNumeroDiploma() +
                diploma.getInstituicao().getNome() +
                diploma.getDataEmissao() +
                diploma.getCriadoPor().getNome();

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(dados.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}