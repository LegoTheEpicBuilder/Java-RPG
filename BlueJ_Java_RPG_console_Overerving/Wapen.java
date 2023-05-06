import java.util.Random;
import java.util.Arrays;

public class Wapen
{
    private String weaponName; //name of weapon
    private Aanval chosenAttack;
    private int baseDamage;
    private int damageThisRound;
    private double critChance;
    
    private int averageSellPrice;
    private int sellPrice;
    
    private String[] categoriën;
    private Aanval[] aanvallen = new Aanval[4];
    
    private static String[] weaponNames = {"Fists", "Wooden Club", "Sword", "Knife", "Pistol", "Bow"}; //fists always has to be the first value in this array!!
    private static Wapen[] allWeapons = new Wapen[]{
    new Wapen("Fists", 10, 0, new String[]{"Melee"}, new Aanval[]{Aanval.getAllPlayerAttacks()[0], Aanval.getAllPlayerAttacks()[1]}),
    new Wapen("Wooden Club", 20, 120, new String[]{"Melee"}, new Aanval[]{Aanval.getAllPlayerAttacks()[2], Aanval.getAllPlayerAttacks()[3]}),
    new Wapen("Sword", 12, 140, new String[]{"Melee"}, new Aanval[]{Aanval.getAllPlayerAttacks()[4], Aanval.getAllPlayerAttacks()[5]}),
    new Wapen("Knife", 8, 100, new String[]{"Melee"}, new Aanval[]{Aanval.getAllPlayerAttacks()[6], Aanval.getAllPlayerAttacks()[7]}),
    new Wapen("Pistol", 15, 150, new String[]{"Ranged"}, new Aanval[]{Aanval.getAllPlayerAttacks()[8], Aanval.getAllPlayerAttacks()[9]}),
    new Wapen("Bow", 20, 175, new String[]{"Ranged"}, new Aanval[]{Aanval.getAllPlayerAttacks()[10], Aanval.getAllPlayerAttacks()[11]})
    };
    
    static Random rnd = new Random();
    
    public Wapen(String weaponName, double critChance, int averageSellPrice, String[] categoriën, Aanval[] aanvallen){
        this.weaponName = weaponName;
        this.critChance = critChance;
        this.averageSellPrice = averageSellPrice;
        this.categoriën = categoriën;
        this.aanvallen = aanvallen;
    }
    public static Wapen getRandomWeapon(){
        Wapen chosenWeapon = allWeapons[rnd.nextInt(allWeapons.length)];
        return new Wapen(chosenWeapon.getWeaponName(), chosenWeapon.getCritChance(), chosenWeapon.getAverageSellPrice(), chosenWeapon.getCategoriën(), chosenWeapon.getAanvallen());
    }
    public static Wapen getRandomWeaponNoFists(){
        Wapen chosenWeapon = allWeapons[rnd.nextInt(allWeapons.length - 1) + 1];
        return new Wapen(chosenWeapon.getWeaponName(), chosenWeapon.getCritChance(), chosenWeapon.getAverageSellPrice(), chosenWeapon.getCategoriën(), chosenWeapon.getAanvallen());
    }
    public int calculateDamageOld(){
        //normal damage
        int maxDmg = baseDamage + 5;
        int minDmg = baseDamage - 5;
        int getal = rnd.nextInt(11);
        
        damageThisRound = 0;
        
        //crit damage
        int getal2 = rnd.nextInt(100) + 1;
        double damageMultiplier = 1;
        
        if (critChance >= getal2){
            damageMultiplier = 1.5;
            System.out.println("Crit!");
        }
        
        if (getal < 5){
            damageThisRound = (int) Math.round((baseDamage - (getal + 1)) * damageMultiplier);
        }
        else if (getal > 5){
            damageThisRound = (int) Math.round((baseDamage + getal - 5) * damageMultiplier);
        }
        else if (getal == 5){
            damageThisRound = (int) Math.round(baseDamage * damageMultiplier);
        }
        
        return damageThisRound;
    }
    public void calculateDamage(){
        double damageMultiplier = 1;
        int getal2 = rnd.nextInt(100) + 1;
        
        if (critChance >= getal2){
            damageMultiplier = 1.5;
            System.out.println("Crit!");
        }
        
        damageThisRound = (int) Math.round((0.8 + Math.random() * (1.2 - 0.8)) * chosenAttack.getWaarde() * damageMultiplier); //damage differenciates 20%
    }
    public int calculateValue(int value){
        return (int) Math.round((0.8 + Math.random() * (1.2 - 0.8)) * value); //value differenciates 20%
    }
    public String getWeaponName(){
        return weaponName;
    }
    public static String[] getWeaponNames(){
        return weaponNames;
    }
    public void setDamageThisRound(int damageThisRound){
        this.damageThisRound = damageThisRound;
    }
    public int getDamageThisRound(){
        return damageThisRound;
    }
    public int getAverageSellPrice(){
        return averageSellPrice;
    }
    public int getSellPrice(){
        return sellPrice;
    }
    public void setSellPrice(int sellPrice){
        this.sellPrice = sellPrice;
    }
    public Aanval[] getAanvallen(){
        return aanvallen;
    }
    public void setBaseAanvallen(int index){
        aanvallen[0] = Aanval.getAllPlayerAttacks()[index];
        aanvallen[1] = Aanval.getAllPlayerAttacks()[index + 1];
    }
    
    //deze methode is niet af
    public void setAanval(int index, int indexArray){ //eerste parameter voor waar in de array te plaatsen en tweede voor kiezen welke aanval
        aanvallen[index] = Aanval.getAllPlayerAttacks()[indexArray];
    }
    public void printAttacksFromWeapon(Aanval[] aanvallen){
        int i = 1;
        for (Aanval aanval: aanvallen){
            if (aanval != null){
                System.out.println(i + ": " + aanval.getName());
            }
            else{
                System.out.println(i + ": /");
            }
            i++;
        }
    }
    public void setChosenAttack(Aanval chosenAttack){
        this.chosenAttack = chosenAttack;
    }
    public Aanval getChosenAttack(){
        return chosenAttack;
    }
    public static Wapen[] getAllWeapons(){
        return allWeapons;
    }
    public double getCritChance(){
        return critChance;
    }
    public String[] getCategoriën(){
        return categoriën;
    }
    public static void printAllWeapons(){
        int i = 1;
        System.out.println("\n" + "__________" + "\n" + "Weapons: " + "\n");
        
        for (Wapen wapen: Wapen.getAllWeapons()){
            System.out.println(i + ": " + wapen.getWeaponName() + " | Crit chance: " + wapen.getCritChance());
            
            System.out.println("Attacks: ");
            for (Aanval aanval: wapen.getAanvallen()){
                System.out.print(aanval.getName() + "     ");
            }
            System.out.println(); //rare bug fixen
            System.out.println();
            
            System.out.println("Categories: ");
            for (String categorie: wapen.getCategoriën()){
                System.out.print(categorie + "     ");
            }
            System.out.println(); //rare bug fixen
            System.out.println("_____");
            i++;
        }
    }
}
