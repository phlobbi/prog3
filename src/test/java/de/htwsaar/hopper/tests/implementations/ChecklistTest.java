package de.htwsaar.hopper.tests.implementations;

import de.htwsaar.hopper.logic.implementations.Checklist;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChecklistTest {
    private final static String CHECKLIST_TOSTRING = "Checklist (ID: %d):\n"
            + "hasFullTank: %b\n"
            + "isUndamaged: %b\n"
            + "isClean: %b\n"
            + "hasKey: %b";

    private Checklist checklist;

    @Before
    public void setUp() {
        checklist = new Checklist(true, true, true, true);
    }

    public String getExpectedToString(boolean fueledUp, boolean undamaged, boolean clean, boolean keyDroppedOff) {
        return String.format(CHECKLIST_TOSTRING,
                checklist.getChecklistId(), fueledUp, undamaged, clean, keyDroppedOff);
    }

    @Test
    public void getProblemCountWithNoProblems() {
        assertEquals(0, checklist.getProblemCount());
    }

    @Test
    public void getProblemCountWithOneProblem() {
        checklist.setFueledUp(false);
        assertEquals(1, checklist.getProblemCount());
    }

    @Test
    public void getProblemCountWithTwoProblems() {
        checklist.setFueledUp(false);
        checklist.setUndamaged(false);
        assertEquals(2, checklist.getProblemCount());
    }

    @Test
    public void getProblemCountWithThreeProblems() {
        checklist.setFueledUp(false);
        checklist.setUndamaged(false);
        checklist.setClean(false);
        assertEquals(3, checklist.getProblemCount());
    }

    @Test
    public void getProblemCountWithFourProblems() {
        checklist.setFueledUp(false);
        checklist.setUndamaged(false);
        checklist.setClean(false);
        checklist.setKeyDroppedOff(false);
        assertEquals(4, checklist.getProblemCount());
    }

    @Test
    public void toStringWithNoProblems() {
        String expected = getExpectedToString(true, true, true, true);
        assertEquals(expected, checklist.toString());
    }

    @Test
    public void toStringWithOneProblem() {
        checklist.setFueledUp(false);
        String expected = getExpectedToString(false, true, true, true);
        assertEquals(expected, checklist.toString());
    }

    @Test
    public void toStringWithTwoProblems() {
        checklist.setFueledUp(false);
        checklist.setUndamaged(false);
        String expected = getExpectedToString(false, false, true, true);
        assertEquals(expected, checklist.toString());
    }

    @Test
    public void toStringWithThreeProblems() {
        checklist.setFueledUp(false);
        checklist.setUndamaged(false);
        checklist.setClean(false);
        String expected = getExpectedToString(false, false, false, true);
        assertEquals(expected, checklist.toString());
    }

    @Test
    public void toStringWithFourProblems() {
        checklist.setFueledUp(false);
        checklist.setUndamaged(false);
        checklist.setClean(false);
        checklist.setKeyDroppedOff(false);
        String expected = getExpectedToString(false, false, false, false);
        assertEquals(expected, checklist.toString());
    }

    @Test
    public void toStringWithUndamagedProblem() {
        checklist.setUndamaged(false);
        String expected = getExpectedToString(true, false, true, true);
        assertEquals(expected, checklist.toString());
    }

    @Test
    public void toStringWithCleanProblem() {
        checklist.setClean(false);
        String expected = getExpectedToString(true, true, false, true);
        assertEquals(expected, checklist.toString());
    }

    @Test
    public void toStringWithKeyDroppedOffProblem() {
        checklist.setKeyDroppedOff(false);
        String expected = getExpectedToString(true, true, true, false);
        assertEquals(expected, checklist.toString());
    }
}
