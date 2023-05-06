import java.util.Scanner;

public class Kamp{
    
    static Scanner sc = new Scanner(System.in);
    
    public static void makeChoice(){
        String choice = "";
        int optionIndex = 1;
        boolean isNotValidInput = true;
        String[] options = {"Equip weapon", "Unequip weapon","Check backpack", "Check info", "Go somewhere else"};
        
        //player makes choice between options
        //checking for valid input
        while (isNotValidInput){    
            
            System.out.println("__________" + "\n" + "What do you wanna do now? " + "\n" + "Possible choices: " + "\n");
            //prints the options
            optionIndex = 1;
            for (String option: options){
                System.out.println(optionIndex + ": " + option);
                optionIndex++;
            }
            choice = sc.nextLine();
            
            optionIndex = 1;
            for (String option: options){
                if (choice.equalsIgnoreCase(option) || choice.equals(Integer.toString(optionIndex))){
                    isNotValidInput = false;
                    break;
                }
                else{
                    isNotValidInput = true;
                }
                if (optionIndex == options.length){
                    System.out.println("Invalid input. Please try again.");
                }
                optionIndex++;
            }
        }
        if (choice.equalsIgnoreCase(options[0]) || choice.equals("1")){
            System.out.println("You chose option 1");
            equipWeapon();
            makeChoice();
        }
        else if(choice.equalsIgnoreCase(options[1]) || choice.equals("2")){
            System.out.println("You chose option 2");
            unequipWeapon();
            makeChoice();
        }
        else if(choice.equalsIgnoreCase(options[2]) || choice.equals("3")){
            System.out.println("You chose option 3");
            Rugzak.printRugzak();
            makeChoice();
        }
        else if(choice.equalsIgnoreCase(options[3]) || choice.equals("4")){
            System.out.println("You chose option 4");
            chooseInfoOption();
            sc.nextLine();
            makeChoice();
        }
        else if(choice.equalsIgnoreCase(options[4]) || choice.equals("5")){
            System.out.println("You chose option 5");
            printLeaving();
            Spel.makeChoice();
        }
        else{
            System.out.println("Invalid input.");
        }
    }
    public static void equipWeapon(){
        Speler chosenPlayer = null;
        Wapen chosenWeapon = null;
        Wapen oldWeapon = null;
        int index = 0;
        
        
        //het kiezen van de speler
        Spel.printPlayerWeapons();
        System.out.println("Which player do you wanna let equip something? (number)");
        String input = sc.nextLine();
        
        //dit systeem fixt het meeste, maar zal nog steeds een error geven wanneer er iets anders dan een integer wordt ingegeven
        //werkt met String en index
        
        for (Speler speler: Spel.getSpelers()){
            if (!(Integer.parseInt(input) > Spel.getSpelers().length) && Spel.getSpelers()[Integer.parseInt(input) - 1] == Spel.getSpelers()[index]){
                chosenPlayer = Spel.getSpelers()[index];
                break;
            }
            index++;
        }
        if (index == Spel.getSpelers().length){
            System.out.println("Invalid input. Equipping weapon failed.");
            return;
        }
        
        index = 0;
        
        //het kiezen van het wapen
        Rugzak.printRugzak();
        System.out.println("What weapon does " + chosenPlayer.getName() + " need to equip? (number)");
        input = sc.nextLine();
        
        //werkt alleen met index
        
        for (Wapen wapen: Rugzak.getRugzak()){
            if (Rugzak.getRugzak()[Integer.parseInt(input) - 1] == Rugzak.getRugzak()[index] && Rugzak.getRugzak()[Integer.parseInt(input) - 1] != null){
                chosenWeapon = Rugzak.getRugzak()[index];
                break;
            }
            index++;
            if (Rugzak.getRugzak()[Integer.parseInt(input) - 1] == null){
                oldWeapon = chosenPlayer.getWeapon();
                
                if (!(chosenPlayer.getWeapon().getWeaponName().equals("Fists"))){
                    Rugzak.setRugzakItem(Integer.parseInt(input) - 1, oldWeapon);
                    chosenPlayer.setWeapon(Wapen.getAllWeapons()[0]);
                    System.out.println(chosenPlayer.getName() + " unequipped his/her weapon.");
                }
                else{
                    System.out.println("What did you even try to do?");
                }
                return;
            }
            if (index == Rugzak.getRugzak().length){
                System.out.println("Invalid input. Equipping weapon failed.");
                return;
            }
        }
        
        //het geven van het wapen aan de speler als het gekozen wapen niet vuisten is
        
        oldWeapon = chosenPlayer.getWeapon();
        chosenPlayer.setWeapon(chosenWeapon);
        System.out.println("Succesfully equipped " + chosenWeapon.getWeaponName() + ".");
        
        //ik heb hier code verwijderd
        
        //zet het oude wapen als het niet handen is op dezelfde plek van waar de speler zijn wapen koos 
        if (oldWeapon.getWeaponName().equals("Fists")){
            Rugzak.setRugzakItem(index, null);
        }
        else{
            Rugzak.setRugzakItem(index, oldWeapon);
            System.out.println("The old weapon from " + chosenPlayer.getName() + " has been added to your inventory.");
        }
        System.out.println("Done!");
    }
    public static void unequipWeapon(){
        Speler chosenPlayer = null;
        Wapen unequippedWeapon = null;
        int index = 0;
        
        //het kiezen van de speler
        Spel.printPlayerWeapons();
        System.out.println("Which player do you wanna let unequip his weapon? (number)");
        String input = sc.nextLine();
        
        //werkt alleen met index
        while (chosenPlayer == null){
            for (Speler speler: Spel.getSpelers()){
                if (!(Integer.parseInt(input) > Spel.getSpelers().length) && Spel.getSpelers()[Integer.parseInt(input) - 1] == Spel.getSpelers()[index]){ //0 & -1
                    chosenPlayer = Spel.getSpelers()[index];
                    unequippedWeapon = speler.getWeapon();
                    break;
                }
                index++;
            }
            if (index == Spel.getSpelers().length){
                System.out.println("Invalid input. Please try again.");
                input = sc.nextLine();
                index = 0;
            }
        }
        index = 0;
        
        if (!(unequippedWeapon.getWeaponName().equals("Fists"))){
            for (Wapen wapen: Rugzak.getRugzak()){
                if (wapen == null){
                    Rugzak.setRugzakItem(index, unequippedWeapon);
                    break;
                }
                index++;
                if (index == Rugzak.getRugzak().length){
                    Rugzak.replaceWeapon(unequippedWeapon);
                }
            }
            chosenPlayer.setWeapon(Wapen.getAllWeapons()[0]);
            System.out.println("Succesfully unequipped " + unequippedWeapon.getWeaponName());
        }
        else{
            System.out.println(chosenPlayer.getName() + " does not have any weapon equipped!");
        }
    }
    public static void chooseInfoOption(){
        int choice;
        String[] options = {"Weapon list", "All obtainable attacks list", "All enemy attacks list", "Nothing"};
        
        System.out.println("__________" + "\n" + "What do you wanna check out?" + "\n" + "Possible choices:" + "\n");
        
        for (int i = 0; i < options.length; i++){
            System.out.println(i + 1 + ": " + options[i]);
        }
        
        try{
            choice = sc.nextInt() - 1;
            
            switch(choice){
                case 0:
                    Wapen.printAllWeapons();
                    break;
                case 1:
                    Aanval.printAllPlayerAttacks();
                    break;
                case 2:
                    Aanval.printAllEnemyAttacks();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
        catch(Exception e){
            System.out.println("Invalid input.");
        }
    }
    public static void printGreeting(){
        System.out.println("You and your team safely get back to camp.");
    }
    public static void printLeaving(){
        System.out.println("You and your team leave the camp.");
    }
    public static void fullCamp(){
        printGreeting();
        makeChoice();
    }
}