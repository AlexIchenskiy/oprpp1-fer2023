package hr.fer.oprpp1.hw07.gui.prim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrimListModelTest {

    PrimListModel primListModel;

    @BeforeEach
    public void beforeEach() {
        primListModel = new PrimListModel();
    }

    @Test
    public void testAfterCreation() {
        assertEquals(1, this.primListModel.getElementAt(0));
    }

    @Test
    public void testGenerateOneElement() {
        this.primListModel.next();
        assertEquals(1, this.primListModel.getElementAt(0));
        assertEquals(2, this.primListModel.getElementAt(1));
    }

    @Test
    public void testGenerateFewElements() {
        this.primListModel.next();
        this.primListModel.next();
        this.primListModel.next();
        assertEquals(1, this.primListModel.getElementAt(0));
        assertEquals(2, this.primListModel.getElementAt(1));
        assertEquals(3, this.primListModel.getElementAt(2));
        assertEquals(5, this.primListModel.getElementAt(3));
    }

}
