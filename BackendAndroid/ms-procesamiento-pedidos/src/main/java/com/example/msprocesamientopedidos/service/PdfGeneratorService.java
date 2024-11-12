package com.example.msprocesamientopedidos.service;

import com.example.msprocesamientopedidos.entity.Pedido;
import com.example.msprocesamientopedidos.entity.PedidoDetalle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

@Service
public class PdfGeneratorService {

    public void generatePdf(Pedido pedido, String dest) {
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Pedido ID: " + pedido.getId()));
            document.add(new Paragraph("Cliente ID: " + pedido.getClienteId()));
            if (pedido.getCliente() != null) {
                document.add(new Paragraph("Nombre del Cliente: " + pedido.getCliente().getNombre()));
            }
            document.add(new Paragraph("Precio Total: " + pedido.getTotalAmount()));

            document.add(new Paragraph("Detalle:"));
            Table table = new Table(new float[]{1, 2, 2, 2, 2});
            table.addCell(new Cell().add(new Paragraph("Producto ID")));
            table.addCell(new Cell().add(new Paragraph("Cantidad")));
            table.addCell(new Cell().add(new Paragraph("Subtotal")));
            table.addCell(new Cell().add(new Paragraph("Nombre del Producto")));
            table.addCell(new Cell().add(new Paragraph("Categoría del Producto")));

            for (PedidoDetalle detalle : pedido.getDetalle()) {
                table.addCell(new Cell().add(new Paragraph(detalle.getProductoId().toString())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getCantidad()))));
                table.addCell(new Cell().add(new Paragraph(detalle.getSubtotal().toString())));
                if (detalle.getProducto() != null) {
                    table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getNombre())));
                    if (detalle.getProducto().getCategoria() != null) {
                        table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getCategoria().getTitulo())));
                    } else {
                        table.addCell(new Cell().add(new Paragraph("N/A")));
                    }
                } else {
                    table.addCell(new Cell().add(new Paragraph("N/A")));
                    table.addCell(new Cell().add(new Paragraph("N/A")));
                }
            }

            document.add(table);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
