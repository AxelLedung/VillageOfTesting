package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class VillageInputTest {
    DatabaseConnection dbMock;
    @BeforeEach
    public void Setup() {
        dbMock = mock(DatabaseConnection.class);
    }
    @Test
    public void Save_Test() {
        InputStream backup = System.in;

        

        Village village = new Village();
        boolean hej = dbMock.SaveVillage(village, "Save1");
        assertEquals(true, hej );

        Village village = new Village();

        String myInput = "Save1";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);

        VillageInput villageInput = new VillageInput(village, dbMock);
        villageInput.scanner = new Scanner(System.in);

        villageInput.Save();
        //Assertions.assertEquals(1, 1);
    }

}