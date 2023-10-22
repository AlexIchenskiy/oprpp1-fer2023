package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SmartScriptParserTest {

    @Test
    public void testSingleTextNode() {
        SmartScriptParser parser = new SmartScriptParser(loadExample(1));

        assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
        assertThrows(Exception.class, () -> parser.getDocumentNode().getChild(1));
    }

    @Test
    public void testSingleTextNodeWithEscape() {
        SmartScriptParser parser = new SmartScriptParser(loadExample(2));

        assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
        assertThrows(Exception.class, () -> parser.getDocumentNode().getChild(1));
    }

    @Test
    public void testSingleTextNodeWithMultipleEscape() {
        SmartScriptParser parser = new SmartScriptParser(loadExample(3));

        assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
        assertThrows(IndexOutOfBoundsException.class, () -> parser.getDocumentNode().getChild(1));
    }

    @Test
    public void testIllegalEscape() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(loadExample(4)));
    }

    @Test
    public void testIllegalEscapeInsideString() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(loadExample(5)));
    }

    @Test
    public void testMultilineWithEchoTag() {
        SmartScriptParser parser = new SmartScriptParser(loadExample(6));
        DocumentNode node = parser.getDocumentNode();

        assertTrue(node.getChild(0) instanceof TextNode);
        assertTrue(node.getChild(1) instanceof EchoNode);
        assertTrue(node.getChild(2) instanceof TextNode);
        assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(3));
    }

    @Test
    public void testMultilineWithEchoTagAndNewLineChar() {
        SmartScriptParser parser = new SmartScriptParser(loadExample(7));
        DocumentNode node = parser.getDocumentNode();

        assertTrue(node.getChild(0) instanceof TextNode);
        assertTrue(node.getChild(1) instanceof EchoNode);
        assertTrue(node.getChild(2) instanceof TextNode);
        assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(3));
    }

    @Test
    public void testMultilineWithInvalidOpenTag() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(loadExample(8)));
    }

    @Test
    public void testOneLineWithInvalidEscape() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(loadExample(9)));
    }

    private String loadExample(int n) {
        return loader("extra/primjer" + n + ".txt");
    }

    private String loader(String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
            byte[] buffer = new byte[1024];

            while (true) {
                assert is != null;
                int read = is.read(buffer);
                if (read < 1) {
                    break;
                }

                bos.write(buffer, 0, read);
            }

            return bos.toString(StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return null;
        }
    }

    @Test
    public void testNotNullEmptyDocument() {
        String documentBody = "";
        SmartScriptParser parser = new SmartScriptParser(documentBody);
        assertNotNull(parser.getDocumentNode());
    }

    @Test
    public void testPlainText() {
        String documentBody = "This is plain text.";
        SmartScriptParser parser = new SmartScriptParser(documentBody);
        DocumentNode node = parser.getDocumentNode();

        assertTrue(node.getChild(0) instanceof TextNode);

        TextNode textNode = (TextNode) node.getChild(0);
        assertEquals(documentBody, textNode.getText());

        assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(1));
    }

    @Test
    public void testForLoop() {
        String documentBody = "{$ FOR i 1 10 1 $} {$= i $} {$ END $}";
        SmartScriptParser parser = new SmartScriptParser(documentBody);
        DocumentNode node = parser.getDocumentNode();

        assertTrue(node.getChild(0) instanceof ForLoopNode);
        assertTrue(node.getChild(0).getChild(0) instanceof TextNode);
        assertTrue(node.getChild(0).getChild(1) instanceof EchoNode);
        assertTrue(node.getChild(0).getChild(2) instanceof TextNode);

        assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(1));
        assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(0).getChild(3));
    }

    @Test
    public void testEchoTag() {
        String documentBody = "{$= var $}";
        SmartScriptParser parser = new SmartScriptParser(documentBody);
        DocumentNode node = parser.getDocumentNode();

        assertTrue(node.getChild(0) instanceof EchoNode);
        assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(1));

    }

    @Test
    public void testMixedContent() {
        String documentBody = "Text and a {$= var $} for loop: {$ FOR i 1 10 1 $} {$= i $} {$ END $}";
        SmartScriptParser parser = new SmartScriptParser(documentBody);
        DocumentNode node = parser.getDocumentNode();

        assertEquals(4, node.numberOfChildren());
        assertTrue(node.getChild(0) instanceof TextNode);
        assertTrue(node.getChild(1) instanceof EchoNode);
        assertTrue(node.getChild(2) instanceof TextNode);
        assertTrue(node.getChild(3) instanceof ForLoopNode);
        assertThrows(IndexOutOfBoundsException.class, () -> node.getChild(4));
    }

}
