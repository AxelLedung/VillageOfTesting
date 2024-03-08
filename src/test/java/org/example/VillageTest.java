package org.example;

import org.example.objects.Worker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VillageTest {
    @Test
    public void AddWorker_CanAddOneWorker_WithoutOccupation() {
        Village village = new Village();
        String workerName = "Adam";
        String workerOccupation = "";
        village.AddWorker(workerName, workerOccupation);
        ArrayList<Worker> workerArrayList = village.getWorkers();

        assertEquals(0, workerArrayList.size());
    }
    @Test
    public void AddWorker_CanAddOneWorker_WithCorrectOccupation() {
        Village village = new Village();
        String workerName = "Adam";
        String workerOccupation = "farmer";
        village.AddWorker(workerName, workerOccupation);
        ArrayList<Worker> workerArrayList = village.getWorkers();

        assertEquals(workerName, workerArrayList.get(0).getName());
        assertEquals(workerOccupation, workerArrayList.get(0).getOccupation());
        assertEquals(1, workerArrayList.size());
    }
    @Test
    public void AddWorker_CanAddTwoWorker_WithCorrectOccupation() {
        Village village = new Village();
        String worker1Name = "Adam";
        String worker1Occupation = "farmer";
        village.AddWorker(worker1Name, worker1Occupation);
        String worker2Name = "Bertil";
        String worker2Occupation = "miner";
        village.AddWorker(worker2Name, worker2Occupation);
        ArrayList<Worker> workerArrayList = village.getWorkers();

        assertEquals(2, workerArrayList.size());
    }
    @Test
    public void AddWorker_CanAddOneWorker_WithWrongOccupation() {
        Village village = new Village();
        String workerName = "Adam";
        String workerOccupation = "Trainer";
        village.AddWorker(workerName, workerOccupation);
        ArrayList<Worker> workerArrayList = village.getWorkers();

        assertEquals(0, workerArrayList.size());
    }
    @Test
    public void AddWorker_Day_MinerDoesCorrectWork() {
        Village village = new Village();
        String workerName = "Adam";
        String workerOccupation = "miner";
        village.AddWorker(workerName, workerOccupation);
        village.setMetal(5);

        village.Day();

        assertEquals(6, village.getMetal());
    }
    @Test
    public void AddWorker_Day_FarmerDoesCorrectWork() {
        Village village = new Village();
        String workerName = "Adam";
        String workerOccupation = "farmer";
        village.AddWorker(workerName, workerOccupation);
        village.setFood(5);

        village.Day();

        assertEquals(9, village.getFood());
    }
    @Test
    public void IsFull_ReturnsTrueOnFullWorkerList() {
        Village village = new Village();
        village.AddWorker("Adam", "farmer");
        village.setMaxWorkers(village.getWorkers().size());
        assertTrue(village.isFull());
    }
    @Test
    public void Day_ContinuesToNextDay_WhenNoWorkerCreated() {
        Village village = new Village();
        village.Day();

        assertEquals(1, village.getDaysGone());
    }
    @Test
    public void Day_ContinuesToNextDay_WithWorkerAndFood() {
        Village village = new Village();
        village.AddWorker("Adam", "builder");
        village.Day();

        assertEquals(false, village.isGameOver());
    }
    @Test
    public void Day_ContinuesToNextDay_WithWorkerAndNoFood() {
        Village village = new Village();
        village.AddWorker("Adam", "builder");
        village.setFood(0);
        village.Day();

        assertEquals(false, village.isGameOver());
    }
    @Test
    public void Day_ContinuesToNextDay_WithWorkerAndNoFoodFor3Days() {
        Village village = new Village();
        village.AddWorker("Adam", "lumberjack");
        village.setFood(0);
        village.Day();
        village.Day();
        village.Day();
        village.Day();

        assertEquals(3, village.getWorkers().get(0).getDaysHungry());
    }
    @Test
    public void Day_InitiateGameOver_WhenOneWorkerWithNoFoodFor5Days() {
        Village village = new Village();
        village.AddWorker("Adam", "lumberjack");
        village.setFood(0);
        village.Day();
        village.Day();
        village.Day();
        village.Day();
        village.Day();
        village.Day();

        assertEquals(true, village.isGameOver());
    }
    @Test
    public void Day_GameContinue_WhenTwoWorkersWith1WithoutFood() {
        Village village = new Village();
        village.AddWorker("Adam", "lumberjack");
        village.AddWorker("Bertil", "lumberjack");
        village.setFood(1);
        village.Day();
        village.setFood(1);
        village.Day();
        village.setFood(1);
        village.Day();
        village.setFood(1);
        village.Day();
        village.setFood(1);
        village.Day();
        village.setFood(1);
        village.Day();
        village.setFood(1);

        assertEquals(false, village.isGameOver());
    }
    @Test
    public void AddProject_WithoutWorker() {
        Village village = new Village();
        village.setWood(1000);
        village.setMetal(1000);
        village.AddProject("House");
        assertEquals("House", village.getProjects().get(0).getName());
    }
    @Test
    public void AddProject_WithoutEnoughResources() {
        Village village = new Village();
        village.setWood(0);
        village.setMetal(0);
        village.AddProject("House");
        assertEquals(0, village.getProjects().size());
    }
    @Test
    public void AddProject_CheckResourcesDecreases() {
        Village village = new Village();
        village.setFood(100);
        village.AddWorker("Adam", "builder");
        village.AddWorker("Bertil", "builder");
        village.AddWorker("Ceasar", "builder");
        village.setWood(100);
        village.setMetal(10);
        village.AddProject("House");
        village.Day();
        village.Day();
        village.Day();
        assertEquals(95, village.getWood());
        assertEquals(10, village.getMetal());
    }
}