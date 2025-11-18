package com.meuprojetotcc.autenticacao_diplomas.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class DiplomaPdfService {

    public byte[] gerarPdf(Diploma diploma) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // --- Título ---
        Paragraph titulo = new Paragraph("🎓 Diploma de Conclusão")
                .setBold()
                .setFontSize(22)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(titulo);

        document.add(new Paragraph("\n")); // espaçamento

        // --- Frase formal ---
        String frase = String.format(
                "O Reitor da Universidade %s, no uso de suas atribuições, confere o presente diploma a %s, pela conclusão do curso de %s em %s.",
                diploma.getInstituicao().getNome(),
                diploma.getEstudante().getNomeCompleto(),
                diploma.getCurso().getNome(),
                diploma.getDataConclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
        document.add(new Paragraph(frase)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(15));

        document.add(new Paragraph("\n")); // espaçamento

        // --- Informações adicionais ---
        Paragraph info = new Paragraph()
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER);

        info.add("Grau Acadêmico: " + diploma.getGrauAcademico() + "\n");
        info.add("Nota Final: " + diploma.getNotaFinal() + "\n");
        info.add("Carga Horária: " + diploma.getCargaHoraria() + "h\n");
        info.add("Número do Diploma: " + diploma.getNumeroDiploma() + "\n");
        info.add("Registro no Ministério: " +
                (diploma.getRegistroMinisterio() != null ? diploma.getRegistroMinisterio() : "Não informado") + "\n");
        info.add("Data de Emissão: " + diploma.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
        info.add("Hash Blockchain: " + diploma.getHashBlockchain() + "\n");

        document.add(info);

        document.add(new Paragraph("\n\n")); // espaço antes das assinaturas

        /*--- Carimbo ---
        if ((Byte) diploma.getCarimboInstituicao() != null) {
            byte[] carimboBytes = Base64.getDecoder().decode( diploma.getCarimboInstituicao());
            ImageData carimboData = ImageDataFactory.create(carimboBytes);
            Image carimboImg = new Image(carimboData)
                    .scaleToFit(120, 60)
                    .setFixedPosition(50, 100);
            document.add(carimboImg);
        }

        // --- Assinatura ---
        if (diploma.getAssinaturaInstituicao() != null) {
            byte[] assinaturaBytes = Base64.getDecoder().decode(diploma.getAssinaturaInstituicao());
            ImageData assinaturaData = ImageDataFactory.create(assinaturaBytes);
            Image assinaturaImg = new Image(assinaturaData)
                    .scaleToFit(120, 60)
                    .setFixedPosition(400, 100);
            document.add(assinaturaImg);
        }

         */

        document.close();
        return baos.toByteArray();
    }
}
