import java.util.Random;
import java.util.Scanner;
public class Rugzak
{
    private static int size = 10;
    private static int coinPouchSize = 250;
    private static int coins = 0;
    
    private static Wapen[] rugzak = new Wapen[size];
    
    private static Random rnd = new Random();
    private static Scanner sc = new Scanner(System.in);
    
    public static void dropLoot(){
        Wapen chosenLootDrop;
        boolean isToegevoegd = false;
        
        for (Vijand vijand: Spel.getVijanden()){
            chosenLootDrop = Wapen.getRandomWeaponNoFists();

            int i = 0;
            int chosenCoinDrop = vijand.calculateCoinDrop(); //checkt de hoeveelheid munten dat een vijand laat vallen na zijn dood.
            String printBagFull = "";
            
            if (coinPouchSize > coins + chosenCoinDrop){
                coins += chosenCoinDrop;
            }
            else{
                coins = coinPouchSize;
                printBagFull = " (full!)";
            }
            
            System.out.println(vijand.getName() + " dropped " + chosenCoinDrop + " coins.");
            System.out.println("Your coins: " + coins + " / " + coinPouchSize + printBagFull);
            
            if (i < rugzak.length){
                while (i < rugzak.length){
                    if (rugzak[i] == null){
                        rugzak[i] = chosenLootDrop;
                        System.out.println("The " + chosenLootDrop.getWeaponName() + " that " + vijand.getName() + " dropped has been succesfully added to your inventory.");
                        i++;
                        break;
                    }
                    else{
                        i++;
                    }
                }
            }
            else{
                        //replacing weapon if backpack is full
                System.out.println("Your backpack is full. Do you want to replace an item and store " + chosenLootDrop + "?" + "\n" + "Yes / No");
                String askIfReplaceItem = sc.nextLine();
                int j = 0;
                
                if (askIfReplaceItem.equalsIgnoreCase("Yes")){
                    System.out.println("\n" + "__________" + "\n" + "Your backpack: ");
                    
                    for (Wapen wapenRugzak: rugzak){
                        System.out.println((j + 1) + ": " + wapenRugzak);
                        j++;
                    }
                    System.out.println("__________");
                    System.out.println("What item do you want to replace? ");
                    
                    int ReplaceItemIndex = sc.nextInt() - 1;
                    
                    if (ReplaceItemIndex >= rugzak.length){
                        System.out.println("Invalid index given. Try again.");
                        ReplaceItemIndex = sc.nextInt() - 1;
                    }
                    else{
                        Wapen replacedItem = rugzak[ReplaceItemIndex];
                        rugzak[ReplaceItemIndex] = chosenLootDrop;
                        System.out.println("The " + replacedItem + " has been succesfully replaced by " + chosenLootDrop + ".");
                    }
                }
            }
        }
    }
    public static void replaceWeapon(Wapen wapen){
        //replacing weapon if backpack is full
        System.out.println("Your backpack is full. Do you want to replace an item and store " + wapen + "?" + "\n" + "Yes / No");
        String askIfReplaceItem = sc.nextLine();
        int j = 0;
        
        if (askIfReplaceItem.equalsIgnoreCase("Yes")){
            System.out.println("\n" + "__________" + "\n" + "Your backpack: ");
            
            for (Wapen wapenRugzak: rugzak){
                System.out.println((j + 1) + ": " + wapenRugzak);
                j++;
            }
            System.out.println("__________");
            System.out.println("What item do you want to replace? ");
            
            int ReplaceItemIndex = sc.nextInt() - 1;
            
            if (ReplaceItemIndex >= rugzak.length){
                System.out.println("Invalid index given. Try again.");
                ReplaceItemIndex = sc.nextInt() - 1;
            }
            else{
                Wapen replacedItem = rugzak[ReplaceItemIndex];
                rugzak[ReplaceItemIndex] = wapen;
                System.out.println("The " + replacedItem + " has been succesfully replaced by " + wapen + ".");
            }
        }
    }
    public static Wapen[] getRugzak(){
        return rugzak;
    }
    public static Wapen setRugzakItem(int index, Wapen wapen){
        return rugzak[index] = wapen;
    }
    public void setSizeRugzak(int size){
        this.size = size;
        
        Wapen[] newRugzak = new Wapen[size];
        for (int i = 0; i < rugzak.length; i++) {
            if (rugzak[i] != null){
                newRugzak[i] = rugzak[i];
            }
        }
        
        rugzak = newRugzak;
    }
    public static void printRugzak(){
        System.out.println("\n" + "__________" + "\n" + "Your backpack: ");
        int j = 0;            
        for (Wapen wapenRugzak: rugzak){
            if (wapenRugzak != null){
                System.out.println((j + 1) + ": " + wapenRugzak.getWeaponName());
            }
            else{
                System.out.println((j + 1) + ": Nothing");
            }
            j++;
        }
    }
    public static void printCoins(){
        System.out.println("Your coins: " + coins + " / " + coinPouchSize);
    }
    public static int getCoinPouchSize(){
        return coinPouchSize;
    }
    public static void setCoinPouchSize(int value){
        coinPouchSize = value;
    }
    public static int getCoins(){
        return coins;
    }
    public static void setCoins(int coin){
        if (coins + coin <= coinPouchSize){
            coins = coin;
        }
        else{
            coins = coinPouchSize;
        }
    }
    public static void removeCoins(int coin){
        if (coins - coin < 0){
            coins = 0;
        }
        else{
            coins -= coin;
        }
    }
}
