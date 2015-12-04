package info.informationsea.pdftools;

import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.kohsuke.args4j.Argument;

import javax.activation.FileDataSource;
import java.io.File;

/**
 * pdftools
 * Copyright (C) 2015 Yasunobu OKAMURA
 * Created at 2015/11/22.
 */
public class PDFValidationCommand implements Command {
    @Argument(index = 0, required = true)
    private File validationFile;

    @Override
    public void run() throws Exception {
        FileDataSource fileDataSource = new FileDataSource(validationFile);
        PreflightParser parser = new PreflightParser(fileDataSource);
        ValidationResult validationResult;

        try {
            parser.parse();
            PreflightDocument document = parser.getPreflightDocument();
            document.validate();
            validationResult = document.getResult();
            document.close();
        } catch (SyntaxValidationException e) {
            validationResult = e.getResult();
        }

        if (validationResult.isValid()) {
            System.out.println("Valid PDF/A-b document");
        } else {
            for (ValidationResult.ValidationError error : validationResult.getErrorsList()) {
                System.out.println(error.getErrorCode() + " : " + error.getDetails());
            }
        }
    }
}
