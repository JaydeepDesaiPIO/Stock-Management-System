package com.spring.stockmanagement.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.spring.stockmanagement.entities.OrderItem;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class ExportPdf {
        public static ByteArrayInputStream exportOrderPdf(List<OrderItem> orderItemList) {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {

                PdfWriter.getInstance(document, out);
                document.open();

                // Add Content to PDF file ->
                Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
                Paragraph para = new Paragraph("Order History", fontHeader);
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                document.add(Chunk.NEWLINE);

                PdfPTable table = new PdfPTable(6);
                // Add PDF Table Header ->
                Stream.of("Order ID", "Date", "Product", "Company", "Quantity", "Price").forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
//                    header.setBackgroundColor(Color.CYAN);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPadding(8);
                    header.setPhrase(new Phrase(headerTitle, headFont));
                    table.addCell(header);
                });

                for (OrderItem ot : orderItemList) {
                    PdfPCell orderId = new PdfPCell(new Phrase(String.valueOf(ot.getOrders().getId())));
                    orderId.setPaddingLeft(4);
                    orderId.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    orderId.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(orderId);

                    PdfPCell date = new PdfPCell(new Phrase(String.valueOf(ot.getOrders().getOrderDate())));
                    date.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    date.setHorizontalAlignment(Element.ALIGN_CENTER);
                    date.setPaddingRight(4);
                    table.addCell(date);

                    PdfPCell product = new PdfPCell(new Phrase(String.valueOf(ot.getProduct())));
                    product.setPaddingLeft(4);
                    product.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    product.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(product);

                    PdfPCell company = new PdfPCell(new Phrase(String.valueOf(ot.getCompany())));
                    company.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    company.setHorizontalAlignment(Element.ALIGN_CENTER);
                    company.setPaddingRight(4);
                    table.addCell(company);

                    PdfPCell quantity = new PdfPCell(new Phrase(String.valueOf(ot.getQuantity())));
                    quantity.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
                    quantity.setPaddingRight(4);
                    table.addCell(quantity);

                    PdfPCell price = new PdfPCell(new Phrase(String.valueOf((float) ot.getPrice())));
                    price.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    price.setHorizontalAlignment(Element.ALIGN_CENTER);
                    price.setPaddingRight(4);
                    table.addCell(price);
                }
                document.add(table);

                HeaderFooter footer=new HeaderFooter(true, new Phrase(" SMS"));
                footer.setAlignment(Element.ALIGN_CENTER);
                footer.setBorderWidthBottom(0);
                document.setFooter(footer);

                document.close();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            return new ByteArrayInputStream(out.toByteArray());
        }
    }

