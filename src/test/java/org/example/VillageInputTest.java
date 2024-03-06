package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.ArrayList;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VillageInputTest {
    DatabaseConnection dbMock;
    @BeforeEach
    public void Setup() {
        dbMock = mock(DatabaseConnection.class);
    }
    @Test
    public void Save_CorrectSaving() {
        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");
        when(dbMock.GetTownNames()).thenReturn(list);
        InputStream backup = System.in;
        String input = "Save1";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Village village = new Village();
        VillageInput villageInput = new VillageInput(village, dbMock);

        when(dbMock.SaveVillage(village, input)).thenReturn(true);

        //Create backup before we redirect the outStream
        PrintStream outBackup = System.out;
        //Redirect the System.out.println to our variable: baos.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        //Run villageInput.Save() which prints out the expected value into our baos variable.
        villageInput.Save();

        //Remove the redirection to the original backup.
        System.setOut(outBackup);

        //Trim all the outputs to just our expected output.
        //A lot of problem with line seperators which led to us using \r instead of \n
        //More info about line seperators in: https://stackoverflow.com/questions/26926888/how-can-i-disable-diff-in-line-separators-in-intellij-idea
        int trimLength = 30 + input.length();
        String actual = baos.toString().substring(baos.toString().length() - trimLength, baos.toString().length() - 1);
        String expected = "village " + input + " successfully saved.\r";

        //Compare the trimmed output to the expected output.
        assertEquals(expected.toLowerCase(), actual.toLowerCase());

        System.setIn(backup);
    }

}