package com.meuprojetotcc.autenticacao_diplomas.service;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class DiplomaPdfService {

    public byte[] gerarPdf(Diploma diploma) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Adiciona uma imagem de fundo (opcional)
        // ImageData data = ImageDataFactory.create("src/main/resources/static/diploma_fundo.png");
        // Image img = new Image(data);
        // document.add(img.setFixedPosition(0,0).scaleToFit(600,800));

        // Título
        document.add(new Paragraph("🎓 Diploma de Conclusão")
                .setBold().setFontSize(18));

        // Frase formal
        String frase = String.format(
                "O Reitor da Universidade %s, no uso de suas atribuições, confere o presente diploma a %s, pela conclusão do curso de %s em %s.",
                diploma.getInstituicao().getNome(),
                diploma.getEstudante().getNomeCompleto(),
                diploma.getCurso().getNome(),
                diploma.getDataConclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
        document.add(new Paragraph(frase).setFontSize(14).setMarginTop(20));

        // Informações adicionais (opcional)
        document.add(new Paragraph("Grau Acadêmico: " + diploma.getGrauAcademico()));
        document.add(new Paragraph("Nota Final: " + diploma.getNotaFinal()));
        document.add(new Paragraph("Carga Horária: " + diploma.getCargaHoraria() + "h"));
        document.add(new Paragraph("Número do Diploma: " + diploma.getNumeroDiploma()));
        document.add(new Paragraph("Registro no Ministério: " + diploma.getRegistroMinisterio()));

        document.close();
        return baos.toByteArray();
    }
}
