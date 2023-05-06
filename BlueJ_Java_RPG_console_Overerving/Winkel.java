import java.util.Random;
import java.util.Scanner;

public class Winkel{
    private static int amountOfItems;
    private static Wapen[] sellingWeapons;
    
    private static Random rnd = new Random();
    private static Scanner sc = new Scanner(System.in);
    
    public Winkel(int amountOfItems){
        this.amountOfItems = amountOfItems;
        sellingWeapons = new Wapen[amountOfItems];
    }
    public static void fillingShop(){
        int i = 0;
        for (Wapen wapen: sellingWeapons){
            wapen = Wapen.getRandomWeaponNoFists();
            calculateSellPrice(wapen);
            sellingWeapons[i] = wapen;
            i++;
        }
    }
    public static void printGreeting(){
        System.out.println("Hello! Welcome to my shop!" + "\n" + "Let me show you what I got in store for you this time.");
    }
    public static void printLeaving(){
        System.out.println("Thanks for checking out my store! Hope to see you back sometime.");
    }
    public static void printShop(){
        int i = 0;
        System.out.println("__________" + "\n" + "Shop:");
        
        for (Wapen wapen: sellingWeapons){
            if (wapen != null){
                System.out.println((i + 1) + ": " + wapen.getWeaponName() + " (" + wapen.getSellPrice() + " coins)");
            }
            else{
                System.out.println((i + 1) + ": SOLD!");
            }
            i++;
        }
    }
    public static void buyWeapon(){
        int index = 0;
        boolean buyAnotherItem = false;
        boolean firstTime = true;
        
        printShop();
        Rugzak.printCoins();
        System.out.println("Do you wanna buy anything? (Y / N)");
        String answer = sc.nextLine();
        
        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("Yes")){
            while (firstTime == true || buyAnotherItem == true){
                firstTime = false;
                buyAnotherItem = false;
                
                printShop();
                Rugzak.printCoins();
                System.out.println("What item do you wanna buy? (number)");
                int answer2 = sc.nextInt();
                
                while (amountOfItems < answer2 || answer2 < 0 || sellingWeapons[answer2 - 1] == null){
                    System.out.println("Invalid location. Please try again.");
                    
                    printShop();
                    System.out.println("What item do you wanna buy? (number)");
                    answer2 = sc.nextInt();
                }
                
                while (Rugzak.getCoins() < sellingWeapons[answer2 - 1].getSellPrice()){
                    System.out.println("You cannot afford this item. Do you wanna cancel buying an item? (Y / N)");
                    
                    sc.nextLine();
                    String answer3 = sc.nextLine();
                    if (answer3.equalsIgnoreCase("Y") || answer3.equalsIgnoreCase("Yes")){
                        System.out.println("You canceled purchasing an item. Hope to see you back here sometime.");
                        Spel.makeChoice();
                        return;
                    }
                    else if (answer3.equalsIgnoreCase("N") || answer3.equalsIgnoreCase("No")){
                        buyAnotherItem = true;
                        break;
                    }
                }
                
                if(Rugzak.getCoins() >= sellingWeapons[answer2 - 1].getSellPrice()){ //if your coins are greater or the same as the price of the item, buy it and store it in your inventory.
                    Rugzak.removeCoins(sellingWeapons[answer2 - 1].getSellPrice());
                    
                    for (Wapen wapen: Rugzak.getRugzak()){
                        if (wapen == null){
                            Rugzak.setRugzakItem(index, sellingWeapons[answer2 - 1]); //.getWeaponName();
                            
                            System.out.println("You bought " + sellingWeapons[answer2 - 1].getWeaponName() + ".");
                            
                            sellingWeapons[answer2 - 1] = null;
                            
                            System.out.println("Do you wanna buy anything else? (Y / N)");
                            sc.nextLine();
                            String answer4 = sc.nextLine();
                            
                            if(answer4.equalsIgnoreCase("Y") || answer4.equalsIgnoreCase("Yes")){
                                buyAnotherItem = true;
                            }
                            
                            break;
                        }
                        index++;
                    }
                }
            }
        }
    }
    public static void calculateSellPrice(Wapen wapen){
        double randomFactor = rnd.nextDouble() * 0.4 + 0.8; // genereert een getal tussen 0.8 en 1.2
        wapen.setSellPrice((int) Math.round(wapen.getAverageSellPrice() * randomFactor));
    }
    public static void fullShop(){
        Spel.setPlayerLocation("Shop");
        Winkel winkel = new Winkel(rnd.nextInt(5) + 4); //makes a shop with 4 - 8 weapons selling in it.
        fillingShop();
        printGreeting();
        buyWeapon();
        printLeaving();
        Spel.makeChoice();
    }
}