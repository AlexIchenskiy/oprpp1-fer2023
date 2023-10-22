package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class for testing a smart script parser.
 */
public class SmartScriptTester {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("A filepath must be provided!");
        }
        String docBody = Files.readString(Paths.get(args[0]));

        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody);
            DocumentNode document = parser.getDocumentNode();
            String originalDocumentBody = document.toString();
            SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
            DocumentNode document2 = parser2.getDocumentNode();
            boolean same = document.equals(document2);
            System.out.println(same);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }
    }

}
