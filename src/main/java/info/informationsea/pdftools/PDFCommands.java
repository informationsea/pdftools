package info.informationsea.pdftools;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

/**
 * pdftools
 * Copyright (C) 2015 Yasunobu OKAMURA
 * Created at 2015/11/22.
 */
public class PDFCommands {
    @Argument(handler = SubCommandHandler.class, usage = "command to run")
    @SubCommands({
            @SubCommand(name = "validate", impl = PDFValidationCommand.class)
    })
    private Command command = null;

    @Option(name = "-h", aliases = {"--help"}, usage = "Show help")
    private boolean showHelp = false;


    public static void main(String ... args) throws Exception {
        PDFCommands pdfCommands = new PDFCommands();
        CmdLineParser cmdLineParser = new CmdLineParser(pdfCommands);
        cmdLineParser.parseArgument(args);

        if (pdfCommands.showHelp || pdfCommands.command == null) {
            System.out.print("pdftools");
            cmdLineParser.printSingleLineUsage(System.out);
            System.out.println();
            System.out.println();
            cmdLineParser.printUsage(System.out);
            return;
        }

        pdfCommands.command.run();
    }
}
