package net.spectrum.api.service.reports.controller;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.service.reports.service.ReportsService;
import net.spectrum.api.util.ExceptionHandlerUtil;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



@RestController
@RequestMapping("/v1/report")
@Validated
@Slf4j
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping(path = "/application-step-one")
    public void getApplicationStepOnePdf(HttpServletRequest request, HttpServletResponse response,
                                         @RequestHeader(name = "Authorization") String token,
                                         @RequestHeader(name = "Request-Id") @NotEmpty String requestId,
                                         @RequestHeader(name = "Request-Timeout-In-Seconds") @NotEmpty String requestTimeoutInSeconds,
                                         @RequestHeader(name = "Request-Time") @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                         @AuthenticationPrincipal Principal principal,
                                         @RequestParam(name = "oid", required = true, defaultValue = "") String oid
    ) throws Exception {
        try {
            log.info("Request send to system user list pdf by {}", principal.getName());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=rmsApplicantList.pdf"));
            reportsService.generatePdfFileForApplicationStepOne(oid, response.getOutputStream());
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/application-step-two")
    public void getApplicationStepTwoPdf(HttpServletRequest request, HttpServletResponse response,
                                         @RequestHeader(name = "Authorization") String token,
                                         @RequestHeader(name = "Request-Id") @NotEmpty String requestId,
                                         @RequestHeader(name = "Request-Timeout-In-Seconds") @NotEmpty String requestTimeoutInSeconds,
                                         @RequestHeader(name = "Request-Time") @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                         @AuthenticationPrincipal Principal principal,
                                         @RequestParam(name = "oid", required = true, defaultValue = "") String oid
    ) throws Exception {
        try {
            log.info("Request send to system user list pdf by {}", principal.getName());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=rmsApplicantList.pdf"));
            reportsService.generatePdfFileForApplicationStepTwo(oid, response.getOutputStream());
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/application-step-three")
    public void getApplicationStepThreePdf(HttpServletRequest request, HttpServletResponse response,
                                           @RequestHeader(name = "Authorization") String token,
                                           @RequestHeader(name = "Request-Id") @NotEmpty String requestId,
                                           @RequestHeader(name = "Request-Timeout-In-Seconds") @NotEmpty String requestTimeoutInSeconds,
                                           @RequestHeader(name = "Request-Time") @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                           @AuthenticationPrincipal Principal principal,
                                           @RequestParam(name = "oid", required = true, defaultValue = "") String oid
    ) throws Exception {
        try {
            log.info("Request send to system user list pdf by {}", principal.getName());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=rmsApplicantList.pdf"));
            reportsService.generatePdfFileForApplicationStepThree(oid, response.getOutputStream());
//            reportsService.generatePdfFileForApplicationAdminStepThree(oid, response.getOutputStream());
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @GetMapping(path = "/customs-application-step-four")
    public void getCustomsApplicationStepFourPdf(HttpServletRequest request, HttpServletResponse response,
                                           @RequestHeader(name = "Authorization") String token,
                                           @RequestHeader(name = "Request-Id") @NotEmpty String requestId,
                                           @RequestHeader(name = "Request-Timeout-In-Seconds") @NotEmpty String requestTimeoutInSeconds,
                                           @RequestHeader(name = "Request-Time") @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                           @AuthenticationPrincipal Principal principal,
                                           @RequestParam(name = "oid", required = true, defaultValue = "") String oid
    ) throws Exception {
        try {
            log.info("Request send to system user list pdf by {}", principal.getName());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=signed_pdf_letter.pdf"));
            reportsService.generatePdfFileForCustomsApplicationStepFour(oid, response.getOutputStream());
//            reportsService.generatePdfFileForApplicationAdminStepThree(oid, response.getOutputStream());
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }


//    public ResponseEntity<byte[]> getCustomsApplicationStepFourPdf(
//            @RequestHeader(name = "Authorization") String token,
//            @RequestHeader(name = "Request-Id") @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds") @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time") @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @AuthenticationPrincipal Principal principal,
//            @RequestParam(name = "oid", required = true, defaultValue = "") String oid
//    ) {
//
//        try {
//            // Specify the file path of the existing DOCX document
//            String docxFilePath = "src/main/resources/docs-template/Doc2.docx";
//
//            // Load the existing DOCX document
//            File docxFile = new File(docxFilePath);
//            FileInputStream fis = new FileInputStream(docxFile);
//            XWPFDocument docxDocument = new XWPFDocument(fis);
//
//            // Create a new PDF document
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            PdfWriter writer = new PdfWriter(outputStream);
//            PdfDocument pdfDocument = new PdfDocument(writer);
//            Document document = new Document(pdfDocument);
//
////            // Load the custom font
////            String fontPath = "src/main/resources/static/fonts/solaimanlipi-1/SolaimanLipi_22-02-2012.ttf";
////            PdfFont font = PdfFontFactory.createFont(fontPath, true);
//
//            // Load the custom font
//            String fontPath = "src/main/resources/static/fonts/solaimanlipi-1/SolaimanLipi_22-02-2012.ttf";
////            PdfFont font = PdfFontFactory.createFont(fontPath, "Identity-H");
//            BaseFont font = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//
//            // Iterate over the paragraphs of the DOCX document and add them to the PDF document
//            for (XWPFParagraph paragraph : docxDocument.getParagraphs()) {
//                String paragraphText = paragraph.getText();
//                Paragraph pdfParagraph = new Paragraph();
//
////                // Add custom text to the PDF document
////                Text customText = new Text("Custom Text");
////                customText.setFont(font).setFontSize(12).setFontColor(ColorConstants.RED);
////                pdfParagraph.add(customText);
//
//                // Add the existing paragraph text to the PDF document
//                pdfParagraph.add(paragraphText);
//                document.add(pdfParagraph);
//            }
//
//            // Close the PDF document
//            document.close();
//
//            // Get the PDF document as a byte array
//            byte[] documentBytes = outputStream.toByteArray();
//
//            // Set the headers and return the PDF document as the response body
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf");
//            headers.setContentType(MediaType.APPLICATION_PDF);
//
//            return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);
//        } catch (IOException | DocumentException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }

    }


    @GetMapping(path = "/application-step-three-accepted")
    public void getApplicationStepThreeAcceptedPdf(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestHeader(name = "Authorization") String token,
                                                 @RequestHeader(name = "Request-Id") @NotEmpty String requestId,
                                                 @RequestHeader(name = "Request-Timeout-In-Seconds") @NotEmpty String requestTimeoutInSeconds,
                                                 @RequestHeader(name = "Request-Time") @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                                 @AuthenticationPrincipal Principal principal,
                                                 @RequestParam(name = "oid", required = true, defaultValue = "") String oid
    ) throws Exception {
        try {
            log.info("Request send to system user list pdf by {}", principal.getName());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=signed_pdf_letter.pdf"));
            reportsService.generatePdfFileForApplicationStepThreeAccepted(oid, response.getOutputStream());
//            reportsService.generatePdfFileForApplicationAdminStepThree(oid, response.getOutputStream());
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }



}
