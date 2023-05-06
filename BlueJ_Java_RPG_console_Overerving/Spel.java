import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Spel{
    private static Random rnd = new Random();
    private static Scanner sc = new Scanner(System.in);
    
    private static Speler[] spelers;
    private static Vijand[] vijanden;
    
    private static String[] locations = {"Camp", "Forest", "Shop"};
    private static String playerLocation;
    
    Vijand enemyTarget;
    Speler playerTarget;
    
    static int amountOfPlayers = 0;
    static String input;
    private static int overflowCheck;
    
    public static void main(String[] args){
        playGame();
    }

    public static void determinePlayers(){
        System.out.println("With how many players do you wanna play?");
        
        int amountPlayers = sc.nextInt();
        sc.nextLine(); // --> onzichtbaar teken bug tegenhouden
        amountOfPlayers = amountPlayers;
        
        spelers = new Speler[amountOfPlayers];
        
        System.out.println("Type the names of the players in the console.");
        for (int i = 0; i < amountOfPlayers; i++){
            String naam = sc.nextLine();
            spelers[i] = new Speler(naam); // 0 --> 100 for testing
        }
        System.out.println("Done!");
    }
    public static void determineEnemies(){
        //generating amount of enemies
        int amountOfEnemies = rnd.nextInt(3) + 1;
        vijanden = new Vijand[amountOfEnemies];
        
        //generating enemies themselves
        for (int i = 0; i < amountOfEnemies; i++){
            Vijand chosenVijand = Vijand.getAllEnemies()[rnd.nextInt(Vijand.getAllEnemies().length)];
            vijanden[i] = new Vijand(chosenVijand.getEnemyKind(), chosenVijand.getAmountOfLives(), chosenVijand.getBaseCoinDrop(), chosenVijand.getAanvallen());
        }
    }
    public static void playGame(){
        //basic info
        System.out.println("Hello, I welcome you to this simple RPG game." + "\n" +
        "You will need to fight off a diverse amount of enemies, it's not gonna be easy." + "\n");
        
        determinePlayers();
        
        if (spelers.length > 1){
            System.out.println("Looks like you chose to play with " + spelers.length + " players.");
        }
        else{
            System.out.println("Looks like you chose to play alone.");
        }
        
        //starting game
        System.out.println("Let's begin with a simple battle.");
        System.out.println("Write 'Start' in the console to begin your adventure.");
        
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("start")){
            System.out.println("You started the game.");
        }
        else{
            while (!(input.equalsIgnoreCase("Start"))){
                input = sc.nextLine();
            }
        }
        
        battle();
    }
    public static void battle(){
        //setting location
        playerLocation = "Forest";
        
        //creating enemies
        determineEnemies();
        printEnemyStats();
        
        //game itself
        while (true){ //loops the game as long as not a whole team died
            //loop for players
            System.out.println("__________" + "\n" + "Your turn!" + "\n");
            for (int i = 0; i < spelers.length; i++){
                if (!spelers[i].isDead()){
                    spelers[i].chooseAttack();
                    
                    //het niet moeten kiezen van een vijand als aanval (alleen) levens genereert
                    if (!(spelers[i].getWeapon().getChosenAttack().getAanvalSoort().contains("Heal"))){
                        chooseEnemy(spelers[i]);
                    }
                    
                    spelers[i].attack();
                    spelers[i].doDamageOverTime();
                    for (int j = 0; j < vijanden.length; j++){
                        checkIfEnemyIsDead(vijanden[j]);
                    }
                    if (checkIfAllEnemiesDead()){
                        printYouWon();
                        Rugzak.dropLoot();
                        makeChoice();
                        return;
                    }
                }
                else{
                    System.out.println(spelers[i].getName() + " (dead)");
                    /* int chooseLine = rnd.nextInt(3);
                    
                    switch(chooseLine){
                        case 0: System.out.println(spelers[i].getName() + " is dead. " + spelers[i].getName() + " cannot attack anymore in this battle."); break;
                        case 1: System.out.println(spelers[i].getName() + " died. That means that " + spelers[i].getName() + " cannot attack anymore in this battle."); break;
                        case 2: System.out.println(spelers[i].getName() + " will not wake up anymore. " + spelers[i].getName() + " will not be able to attack anymore in this battle."); break;
                    } */
                }
            }
            
            //loop for enemies
            System.out.println("__________" + "\n" + "Enemy's turn!" + "\n");
            for (int i = 0; i < vijanden.length; i++){
                if (!vijanden[i].isDead()){
                    choosePlayer(vijanden[i]);
                    vijanden[i].attack();
                    vijanden[i].doDamageOverTime(); //does DoT damage
                    for (int j = 0; j < spelers.length; j++){
                        printDeath(spelers[j]);
                    }
                    if (checkIfAllPlayersDead()){
                        printYouLost();
                        makeChoice();
                        return;
                    }
                }
                else{
                    System.out.println(vijanden[i].getName() + " (dead)");
                    /* int chooseLine = rnd.nextInt(3);
                    
                    switch(chooseLine){
                        case 0: System.out.println(vijanden[i].getEnemyKind() + "" + vijanden[i].getName() + " is dead. " + vijanden[i].getName() + " cannot attack anymore in this battle."); break;
                        case 1: System.out.println(vijanden[i].getEnemyKind() + "" + vijanden[i].getName() + " died. That means that " + vijanden[i].getName() + " cannot attack anymore in this battle."); break;
                        case 2: System.out.println(vijanden[i].getEnemyKind() + "" + vijanden[i].getName() + " will not wake up anymore. " + vijanden[i].getName() + " will not be able to attack anymore in this battle."); break;
                    } */
                }
            }
            printPlayerStats();
        }
    }
    public static void makeChoice(){
        int choice;
        int option = 1;
        String returnValue;
        boolean isValidAnswer = false;
        
        //prints the locations
        System.out.println("__________" + "\n" + "Where do you wanna go now? " + "\n" + "Possible choices: " + "\n");
        for (String location: locations){
            if (playerLocation.equals(location)){
                System.out.println(option + ": " + location + " (You're here!)");
            }
            else{
                System.out.println(option + ": " + location);
            }
            
            option++;
        }
        
        //player making choice
        while (isValidAnswer == false){
            choice = sc.nextInt();
            
            if (!(choice == 2) && playerLocation.equals(locations[choice - 1])){
                System.out.println("You cannot go to the " + (locations[choice - 1]).toLowerCase() + " back to back. Please try again.");
            }
            else if (choice == 1){
                isValidAnswer = true;
                System.out.println("Traveling to " + "camp...");
                Spel.playerLocation = locations[0];
                sc.nextLine();
                Kamp.fullCamp();
            }
            else if(choice == 2){
                isValidAnswer = true;
                System.out.println("Traveling to " + "forest...");
                Spel.playerLocation = locations[1];
                sc.nextLine();
                battle();
            }
            else if(choice == 3){
                isValidAnswer = true;
                System.out.println("Traveling to " + "shop...");
                Spel.playerLocation = locations[2];
                sc.nextLine();
                Winkel.fullShop();
            }
            else{
                System.out.println("Invalid input. Please try again.");
            }
        }
    }   
    public static void chooseEnemy(Speler speler){
        boolean isValidInput = false;
        
        while(!isValidInput){
            System.out.println("Which enemy does " + speler.getName() + " need to attack?");
            
            String enemyName = sc.nextLine();
            for (int i = 0; i < vijanden.length; i++){
                if (vijanden[i].getName().equalsIgnoreCase(enemyName)){
                    speler.chooseEnemy(vijanden[i]);
                    isValidInput = true;
                    break;
                }
                else if(i == vijanden.length - 1){
                    System.out.println("No enemy found with that name. Try again.");
                    printEnemyStats();
                }
            }
        }
    }
    public static void choosePlayer(Vijand vijand){
        for (int i = 0; i < spelers.length; i++){
            int getal = rnd.nextInt(spelers.length);
            vijand.choosePlayer(spelers[getal]);
            
        }
    }
    public static int getOverflowCheck(){
        return overflowCheck;
    }
    public static void checkIfEnemyIsDead(Vijand vijand){
        if (vijand.isDead() && vijand.getAmountOfLives() == 0){
            System.out.println(vijand.getName() + " died.");
            //vijand.setAmountOfLives(1);
        }
    }
    public static void printDeath(Speler speler){
        if (!speler.isDead() && speler.getAmountOfLives() == 0){
            System.out.println(speler.getName() + " died.");
            //speler.setAmountOfLives(1);
        }
    }
    public static boolean checkIfAllPlayersDead(){
        boolean allPlayersDead = false;
        int checkIfAllDead = 0;
        for (int i = 0; i < spelers.length; i++){
            if (spelers[i].isDead()){
                checkIfAllDead++;
            }
        }
        if (checkIfAllDead == spelers.length){
            allPlayersDead = true;
        }
        return allPlayersDead;
    }
    public static boolean checkIfAllEnemiesDead(){
        boolean allEnemiesDead = false;
        int checkIfAllDead = 0;
        for (int i = 0; i < vijanden.length; i++){
            if (vijanden[i].isDead()){
                checkIfAllDead++;
            }
        }
        if (checkIfAllDead == vijanden.length){
            allEnemiesDead = true;
        }
        return allEnemiesDead;
    }
    public static void setOverflowCheck(int overflowChecken){
        overflowCheck = overflowChecken;
    }
    public static void printEnemyStats(){
        System.out.println("__________" + "\n" + "Enemies:");
        for (int i = 0 ; i < vijanden.length && vijanden[i] != null; i++){
            if (vijanden[i].isDead()){
                System.out.println(vijanden[i].getName() + ", " + vijanden[i].getEnemyKind() + " (dead)");
            }
            else{
                System.out.println(vijanden[i].getName() + ", " + vijanden[i].getEnemyKind() + " (" + vijanden[i].getAmountOfLives() + " hp)");
            }
        }
        System.out.println("__________");
    }
    public static void printYouWon(){
        int chooseDeathNote = rnd.nextInt(3);
        switch(chooseDeathNote){
            case 0: System.out.println("\n" + "You won! Your team managed to kill all enemies succesfully!"); break;
            case 1: System.out.println("\n" + "You won! Your team was just to strong for these unlucky enemies."); break;
            case 2: System.out.println("\n" + "You won! I just knew your team could do it!"); break;
        }
    }
    public static void printYouLost(){
        int chooseDeathNote = rnd.nextInt(3);
        switch(chooseDeathNote){
            case 0: System.out.println("\n" + "You lost. The enemies managed to kill your team."); break;
            case 1: System.out.println("\n" + "You Lost. You got overwhelmed by the enemies."); break;
            case 2: System.out.println("\n" + "You lost. The enemies were just to strong for your team."); break;
        }
    }
    public static Vijand[] getVijanden(){
        return vijanden;
    }
    public static Speler[] getSpelers(){
        return spelers;
    }
    public static String getPlayerLocation(){
        return playerLocation;
    }
    public static void setPlayerLocation(String location){
        playerLocation = location;
    }
    public static void printSpelers(){
        System.out.println("\n" + "__________" + "\n" + "Your team: ");
        int j = 0;
        for (Speler speler: spelers){
            System.out.println((j + 1) + ": " + speler.getName());
            j++;
        }
    }
    public static void printPlayerStats(){
        System.out.println("\n" + "__________" + "\n" + "Your team: ");
        int i = 0;
        for (Speler speler: spelers){
            if (spelers[i].isDead()){
                System.out.println((i + 1) + ": " + spelers[i].getName() + ", " + " (dead)");
            }
            else{
                System.out.println((i + 1) + ": " + spelers[i].getName() + ", " + " (" + spelers[i].getAmountOfLives() + " hp)");
            }
            i++;
        }
    }
    public static void printPlayerWeapons(){
        System.out.println("\n" + "__________" + "\n" + "Your team's weapons equipped: ");
        int i = 0;
        for (Speler speler: spelers){
            System.out.println((i + 1) + ": " + spelers[i].getName() + ", " + " (" + speler.getWeapon().getWeaponName() + ")");
            i++;
        }
    }
}