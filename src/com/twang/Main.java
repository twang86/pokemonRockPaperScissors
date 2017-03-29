package com.twang;

import java.io.*;
import java.util.*;

public class Main {
    public static String historyFile = "history.txt";
    public static String pokemonFile = "pokemon.txt";
    public static int grassLevel = 0;
    public static int fireLevel = 0;
    public static int waterLevel = 0;
    public static String userChoice = "";
    public static String aiChoice = "";
    public static String [] bulbasaurLine = {"Bulbasaur", "Ivysaur", "Venusaur"};
    public static String [] charmanderine = {"Charmander", "Charmeleon", "Charizard"};
    public static String [] squirtleLine = {"Squirtle", "Wartortle", "Blastoise"};
    public static List<String> battleLog = new ArrayList<>();
    public static List<String> pokemonLevel = new ArrayList<>();
    public static String grass = bulbasaurLine[grassLevel];
    public static String fire = charmanderine[fireLevel];
    public static String water = squirtleLine[waterLevel];
    public static String electric = "Pikachu";
    public static String mewtwo = "Mewtwo";

    public static void main(String[] args) {
        init();
        mainMenu();
    }

    public static void init(){
        File history = new File(historyFile);
        File pokemon = new File(pokemonFile);
        if (history.exists()) {
            readFile(battleLog, history);
        }
        if (pokemon.exists()){
            readFile(pokemonLevel, pokemon);
            grassLevel = Integer.parseInt(pokemonLevel.get(pokemonLevel.size()-3));
            fireLevel = Integer.parseInt(pokemonLevel.get(pokemonLevel.size()-2));
            waterLevel = Integer.parseInt(pokemonLevel.get(pokemonLevel.size()-1));
        }
        //System.out.println(pokemonLevel);
        //System.out.println(Integer.parseInt(pokemonLevel.get(0)));
        grass = bulbasaurLine[grassLevel];
        fire = charmanderine[fireLevel];
        water = squirtleLine[waterLevel];
    }

    public static void mainMenu(){
        drawText("logo.txt", 100);
        switch (userSays()){
            case "1":
            case "Play":
            case "play":
                chooseHand();
                break;
            case "2":
            case "History":
            case "history":
                showHistory();
                break;
            default:
                pokemonLevel.add(Integer.toString(grassLevel));
                pokemonLevel.add(Integer.toString(fireLevel));
                pokemonLevel.add(Integer.toString(waterLevel));
                System.out.println("Thanks for playing!!");
                saveFile(pokemonLevel, pokemonFile);
                saveFile(battleLog, historyFile);
                System.exit(0);
        }
    }

    public static void chooseHand() {


        System.out.println("choose your pokemon");
        System.out.println("1. " + grass);
        System.out.println("2. " + fire);
        System.out.println("3. " + water);
        userChoice = userSays();
        aiChoice = compChoose();
        if (userChoice.equals("1") || userChoice.equalsIgnoreCase(grass)) {
            userChoice = grass;
        } else if (userChoice.equals("2") || userChoice.equalsIgnoreCase(fire)) {
            userChoice = fire;
        } else if (userChoice.equals("3") || userChoice.equalsIgnoreCase(water)){
            userChoice = water;
        } else if (userChoice.equalsIgnoreCase(mewtwo)){
            userChoice = mewtwo;
        } else if (userChoice.equalsIgnoreCase(electric)){
            userChoice = electric;
        } else {
            System.out.println("This pokemon does not exist, try again!");
            chooseHand();
        }
        startBattle();
    }

    public static void startBattle() {
        String logMe;
        drawText(userChoice + ".txt", 50);
        logMe = "You chose " + userChoice;
        System.out.println(logMe);
        battleLog.add(logMe);
        System.out.println("Press any button to see what the computer chose.");
        userSays();
        drawText(aiChoice + ".txt", 50);
        logMe = "the computer chose " + aiChoice;
        System.out.println(logMe);
        battleLog.add(logMe);
        System.out.println("Press any key");
        userSays();
        logMe = "You " + compareHands(userChoice, aiChoice) + "!!";
        //System.out.println(logMe);
        battleLog.add(logMe);
        drawText(compareHands(userChoice, aiChoice) + ".txt", 50);
        System.out.println("Press any key to continue");
        userSays();
        if (compareHands(userChoice, aiChoice).equals("win")) {
            evolvePokemon(userChoice);
        }

        endScreen();
    }

    public static void endScreen(){

        System.out.println("Play Again?");
        System.out.println("1: Yes");
        System.out.println("2: No");

        switch (userSays()){
            case "1":
            case "Yes":
            case "yes":
            case "y":
                chooseHand();
                break;
            case "2":
            case "No":
            case "no":
            case "n":
                mainMenu();
                break;
            default:
                mainMenu();
                break;

        }


    }

    public static void evolvePokemon(String pokemon){
        String logMe;
        if (pokemon.equals(grass) && grassLevel < 2){
            logMe = "Huh... Your pokemon is evolving!";
            System.out.println(logMe);
            System.out.println("Press any key to continue.");
            userSays();
            grassLevel++;
            grass = bulbasaurLine[grassLevel];
            logMe = "Your " + pokemon + " evolved into a " + grass + "!";
            drawText(pokemon + ".txt", 50);
            drawText(grass + ".txt", 50);
        } else if (pokemon.equals(fire) && fireLevel <2){
            logMe = "Huh... Your pokemon is evolving!";
            System.out.println(logMe);
            System.out.println("Press any key to continue.");
            userSays();
            fireLevel++;
            fire = charmanderine[fireLevel];
            logMe = "Your " + pokemon + " evolved into a " + fire + "!";
            drawText(pokemon + ".txt", 50);
            drawText(fire + ".txt", 50);
        } else if (pokemon.equals(water) && waterLevel <2){
            logMe = "Huh... Your pokemon is evolving!";
            System.out.println(logMe);
            System.out.println("Press any key to continue.");
            userSays();
            waterLevel++;
            water = squirtleLine[waterLevel];
            logMe = "Your " + pokemon + " evolved into a " + water + "!";
            drawText(pokemon + ".txt", 50);
            drawText(water + ".txt", 50);
        } else {
            return;
        }

        System.out.println(logMe);
        battleLog.add(logMe);
        return;

    }

    public static String compareHands(String user, String comp){
        String result;
        if (user.equals(comp)){
            result = "tied";
        } else if (user.equals(grass)) {
            result = comp.equals(water) ? "win" : "lose";
        } else if (user.equals(fire)) {
            result = comp.equals(grass) ? "win" : "lose";
        } else if (user.equals(water)) {
            result = comp.equals(fire) ? "win" : "lose";
        } else if (user.equals(electric)){
            result = comp.equals(water) ? "win" : "tied";
        }
        else if (user.equals(mewtwo)){
            result = "win";
        } else {
            result = "messed up somehow...";
        }
        return result;

    }


    public static String compChoose(){
        int choice = (int)Math.round(Math.random()*2);
        switch(choice){
            case 0:
                return grass;
            case 1:
                return fire;
            case 2:
            case 3:
                return water;
            default:
                return grass;

        }
    }

    public static void showHistory(){
        System.out.println("Battle history");
        for (int i = 0; i < battleLog.size(); i++) {
            System.out.println(battleLog.get(i));
        }
        System.out.println("Press any button to exit!");
        userSays();
        mainMenu();
    }

    public static String userSays(){
        Scanner input = new Scanner(System.in);
        String userTypes = input.nextLine();
        return userTypes;
    }

    public static void saveFile(List<String>writeFrom, String writeTo){
        BufferedWriter bw = null;
        try {
            //Specify the file name and path here
            File file = new File(writeTo);

	 /* This logic will make sure that the file
	  * gets created if it is not present at the
	  * specified location*/
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < writeFrom.size(); i++) {

                bw.write(writeFrom.get(i) + System.getProperty("line.separator"));
            }

            //System.out.println("File written Successfully");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally
        {
            try{
                if(bw!=null)
                    bw.close();
            }catch(Exception ex){
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }
    }

    public static void pause(long howLong){
        try {
            Thread.sleep(howLong);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void readFile(List<String> readTo, File readFrom){
        try(BufferedReader br = new BufferedReader(new FileReader(readFrom))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                readTo.add(line);
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void drawText (String fileName, long speed){
        File imageFile = new File(fileName);
        List<String> drawList = new ArrayList<>();
        if (imageFile.exists()){
            readFile(drawList, imageFile);
            for (int i = 0; i < drawList.size(); i++) {
                System.out.println(drawList.get(i));
                pause(speed);
            }
        }
    }

}
