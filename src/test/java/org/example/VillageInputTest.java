package org.example;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.*;
import java.util.ArrayList;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VillageInputTest {
    DatabaseConnection dbMock;
    InputStream backup = System.in;

    @BeforeEach
    public void Setup() {
        dbMock = mock(DatabaseConnection.class);
    }
    @AfterEach
    public void Reset() {
        System.setIn(backup);
    }
    @Test
    public void Save_SavingWithUniqueName_Success() {
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

        when(villageInput.databaseConnection.SaveVillage(village, input)).thenReturn(true);

        villageInput.Save();

        verify(villageInput.databaseConnection).SaveVillage(village, input);
    }
    @Test
    public void Save_SavingWithUniqueName_Fail() {
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

        when(villageInput.databaseConnection.SaveVillage(village, input)).thenReturn(false);

        villageInput.Save();

        verify(villageInput.databaseConnection).SaveVillage(village, input);
    }
    @Test
    public void Save_SavingWithUsedName_Overwrite() {
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

        String choice = "placeholders";
        String input = choice + "\ny";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Village village = new Village();
        VillageInput villageInput = new VillageInput(village, dbMock);

        when(dbMock.SaveVillage(village, choice)).thenReturn(true);

        villageInput.Save();

        verify(dbMock).SaveVillage(village, choice);
    }
    @Test
    public void Save_SavingWithUsedName_NotOverwrite() {
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

        String choice = "placeholders";
        String input = choice + "\nn";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Village village = new Village();
        VillageInput villageInput = new VillageInput(village, dbMock);

        when(dbMock.SaveVillage(village, choice)).thenReturn(true);

        villageInput.Save();

        verify(dbMock, never()).SaveVillage(village, choice);
    }
    @Test
    public void Load_WithExistingVillage() {
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

        String choice = "These";
        ByteArrayInputStream in = new ByteArrayInputStream(choice.getBytes());
        System.setIn(in);

        Village village = new Village();
        VillageInput villageInput = new VillageInput(village, dbMock);
        when(dbMock.LoadVillage(choice)).thenReturn(village);

        villageInput.Load();

        verify(dbMock).LoadVillage(choice);
        assertNotEquals(null, dbMock.LoadVillage(choice));
    }
    @Test
    public void Load_WithoutExistingVillage() {
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

        String choice = "Save1";
        ByteArrayInputStream in = new ByteArrayInputStream(choice.getBytes());
        System.setIn(in);

        Village village = new Village();
        VillageInput villageInput = new VillageInput(village, dbMock);
        when(dbMock.LoadVillage(choice)).thenReturn(village);

        villageInput.Load();

        verify(dbMock, never()).LoadVillage(choice);
    }
    @Test
    public void Load_NullVillage() {
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

        String choice = "These";
        ByteArrayInputStream in = new ByteArrayInputStream(choice.getBytes());
        System.setIn(in);

        Village village = null;
        VillageInput villageInput = new VillageInput(village, dbMock);
        when(dbMock.LoadVillage(choice)).thenReturn(village);

        villageInput.Load();

        verify(dbMock).LoadVillage(choice);
        assertNull(dbMock.LoadVillage(choice));
    }
}