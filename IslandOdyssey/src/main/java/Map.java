import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Map implements Serializable {

    private ArrayList<Room> rooms;

    //Joseph and Bao
    public Map(String roomFile, String itemFile, String monsterFile, String puzzleFile) {
        this.rooms = new ArrayList<>();
        readRoom(roomFile);
        readItems(itemFile);
        readMonster(monsterFile);
        readPuzzles(puzzleFile);

    }
    
    //Bao and Joseph
    public void readRoom(String roomFile){

        try{
            File readRoomFile = new File(roomFile);
            Scanner roomScanner = new Scanner(readRoomFile);

            while(roomScanner.hasNext()) {
                String data = roomScanner.nextLine();
                String[] reading = data.split("~");


                int roomNumber = Integer.parseInt(reading[0]);
                String name = reading[1];
                String description = reading[2];
                int northExit = Integer.parseInt(reading[3]);
                int eastExit = Integer.parseInt(reading[4]);
                int southExit = Integer.parseInt(reading[5]);
                int westExit = Integer.parseInt(reading[6]);
                Room roomies = new Room(roomNumber,name, description,northExit,eastExit,southExit,westExit);
                rooms.add(roomies);

            }
            roomScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Joseph and Bao
    //Method to read the monster text file
    public void readMonster(String monsterFile){

        try {
            File readMonsterFile = new File(monsterFile);
            Scanner read = new Scanner(readMonsterFile);

            while(read.hasNext()){
                String memory = read.nextLine();
                String[] data = memory.split("~");
                try{
                    rooms.get(Integer.parseInt(data[6])).addMonster(new Monster(data[0],data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]),data[5]));
                }catch(NumberFormatException ex){
                    System.out.println(ex.getMessage() + "\u001B[31m" + "monster file problem" + "\u001B[0m");
                }
            }
            read.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Edwin and Bao
    public void readItems(String itemFile) {
        try {
            File readItemFile = new File(itemFile);
            Scanner sc = new Scanner(readItemFile);


        while (sc.hasNext()) {
            String data = sc.nextLine();
            String[] tokens = data.split("~");

            String itemName = tokens[1];
            String itemDesc = tokens[2];
            int roomNumber = Integer.parseInt(tokens[3]);
            switch (tokens[0].toLowerCase()) {
                case "consumable":
                    int healthPoints = Integer.parseInt(tokens[4]);
                    rooms.get(roomNumber).addItem(new Consumable(itemName, itemDesc, healthPoints));
                    break;
                case "combat":
                    rooms.get(roomNumber).addItem(new CombatItem(itemName, itemDesc, roomNumber));
                    break;
                case "collectible":
                    rooms.get(roomNumber).addItem(new Collectible(itemName, itemDesc, roomNumber));
                    break;
                case "interactable":
                    rooms.get(roomNumber).addItem(new Interactable(itemName, itemDesc, roomNumber));
                    break;
                }
            }
                sc.close();
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                System.out.println("\u001B[31m" + "Item file problem" + "\u001B[0m");
            } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



    }
    //Joseph
    public void readPuzzles(String puzzleFile){


        try{
            File readPuzzleFile = new File(puzzleFile);
            puzzleReader = new Scanner(readPuzzleFile);
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
            System.out.println("\u001B[31m" +"Puzzle file problem" + "\u001B[0m");
        }
        while(puzzleReader.hasNext()){
            String puzzleData = puzzleReader.nextLine();
            String[] data = puzzleData.split("~");
            try{
                switch (data[0].toLowerCase()){
                    case "0":
                        rooms.get(Integer.parseInt(data[4])).addPuzzle(new Switches(data[1],data[2],Integer.parseInt(data[3])));
                        break;
                    case "1":
                        rooms.get(Integer.parseInt(data[4])).addPuzzle(new Keypad(data[1],data[2],Integer.parseInt(data[3])));
                        break;

                }
            }catch (NumberFormatException ex){
                System.out.println(ex.getMessage());
                System.out.println("\u001B[31m" + "Puzzle file problem" + "\u001B[0m");
            }
        }
        puzzleReader.close();
    }
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }

}
