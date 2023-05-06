import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class Vijand extends Entiteit
{
    static String[] enemyKinds = {"Wolf","Wizard","Goblin","Zombie"};
    static String[] allAttacks = {"Claws", "Roar", "Spell", "Healing Drink", "Punch", "Gold Steal", "Bite", "Grab"};
    static String[] possibleNames = {"James", "Robert", "John", "Michael", "David", "William", 
                                "Mary", "Patricia", "Jennifer", "Linda", "Lisa"};
    
    private static Vijand[] allEnemies = new Vijand[]{
    new Vijand("Wolf", 80, 25, new Aanval[]{Aanval.getAllEnemyAttacks()[0], Aanval.getAllEnemyAttacks()[1]}),
    new Vijand("Wizard", 140, 40, new Aanval[]{Aanval.getAllEnemyAttacks()[2], Aanval.getAllEnemyAttacks()[3]}),
    new Vijand("Goblin", 60, 50, new Aanval[]{Aanval.getAllEnemyAttacks()[4], Aanval.getAllEnemyAttacks()[5]}),
    new Vijand("Zombie", 100, 30, new Aanval[]{Aanval.getAllEnemyAttacks()[6], Aanval.getAllEnemyAttacks()[7]})
    };
    
    private Aanval[] aanvallen = new Aanval[2];
    private Aanval chosenAttack;
    private int damageThisRound;
    
    //TBA
    private int baseCoinDrop;
    private int coinDropThisRound;
    
    private int damageOverTime = 0;
    
    private Speler speler;
    private String enemyKind;
    private Speler huidigeSpeler;
    
    Random rnd = new Random();
    
    public Vijand(String enemyKind, int amountOfLives, int baseCoinDrop, Aanval[] aanvallen){
        super(amountOfLives);
        this.enemyKind = enemyKind;
        this.amountOfLives = amountOfLives;
        this.baseCoinDrop = baseCoinDrop;
        this.aanvallen = aanvallen;
        
        maxAmountOfLives = amountOfLives;
        
        determineName();
    }
    public void choosePlayer(Speler speler){
        this.speler = speler;
        huidigeSpeler = speler;
        if (Spel.getOverflowCheck() == 0){
            Spel.setOverflowCheck(1);
            speler.chooseEnemy(this);
        }
        else{
            Spel.setOverflowCheck(0);
        }
    }
    public void chooseAttack(){
        chosenAttack = aanvallen[rnd.nextInt(aanvallen.length)];
    }
    public void attack(){
        int j = 0;
        
        chooseAttack();
        
        //damage
        if (chosenAttack.getAanvalSoort().contains("Damage")){
            calculateDamage();
            checkDamage();
            //checkDamageOne();
            //checkDamageAll();
        }
        //heal
        else if (chosenAttack.getAanvalSoort().contains("Heal")){
            calculateDamage();
            checkHeal();
        }
        //buff
        else if (chosenAttack.getAanvalSoort().contains("Buff")){
            
        }
        else{
            System.out.println("kind of attack not found.");
        }
    }
    public void checkDamage(){
        try{
            String effectKind = getChosenAttack().getEffect().getEffectKind();
        
            if (effectKind.equals("Normal")){
                Effect.normal(this);
            }
            else if (effectKind.equals("Damage Over Time")){
                
            }
            else if (effectKind.equals("Multi Hit")){
                Effect.multiHit(this);
            }
            else if (effectKind.equals("Final Hit")){
                Effect.finalHit(this);
            }
            else if (effectKind.equals("Remove Gold")){
                Effect.removeGold(this);
            }
        }
        catch (Exception e){
            Effect.normal(this);
        }
    }
    public void checkDamageAll(){
        if (chosenAttack.getAanvalSoort().equals("Damage All")){
            for (Speler spelertje: Spel.getSpelers()){
                if (chosenAttack.getAanvalSoort().equals("Normal")){
                    spelertje.setAmountOfLives(spelertje.getAmountOfLives() - getDamageThisRound());
                }
                else if (chosenAttack.getAanvalSoort().equals("Damage Over Time")){
                    spelertje.setAmountOfLives(spelertje.getAmountOfLives() - getDamageThisRound());
                    spelertje.setDamageOverTime(chosenAttack.getEffect()); //spelertje.getDamageOverTime() + 2
                }
                else if (chosenAttack.getAanvalSoort().equals("Final Hit")){
                    if (spelertje.getAmountOfLives() <= 50){
                        setDamageThisRound(chosenAttack.getWaarde() * 2);
                        spelertje.setAmountOfLives(spelertje.getAmountOfLives() - getDamageThisRound());
                    }
                    else{
                        spelertje.setAmountOfLives(spelertje.getAmountOfLives() - getDamageThisRound());
                    }
                }
                else if (chosenAttack.getAanvalSoort().equals("Multi Hit")){
                    for (int i = 0; i < 2; i++){
                        spelertje.setAmountOfLives(spelertje.getAmountOfLives() - getDamageThisRound());
                    }
                }
                else{
                    System.out.println("Damage effect not found.");
                }
                
                if (spelertje.getAmountOfLives() < 0){
                    spelertje.setAmountOfLives(0);
                }
            }
        }
    }
    public void checkDamageOne(){
        if (chosenAttack.getAanvalSoort().equals("Damage One")){
            if (chosenAttack.getAanvalSoort().equals("Normal")){
                    speler.setAmountOfLives(speler.getAmountOfLives() - getDamageThisRound());
            }
            else if (chosenAttack.getAanvalSoort().equals("Damage Over Time")){
                speler.setAmountOfLives(speler.getAmountOfLives() - getDamageThisRound());
                speler.setDamageOverTime(chosenAttack.getEffect()); //speler.getDamageOverTime() + 2
            }
            else if (chosenAttack.getAanvalSoort().equals("Final Hit")){
                if (speler.getAmountOfLives() <= 50){
                    setDamageThisRound(chosenAttack.getWaarde() * 2);
                    speler.setAmountOfLives(speler.getAmountOfLives() - getDamageThisRound());
                }
                else{
                    speler.setAmountOfLives(speler.getAmountOfLives() - getDamageThisRound());
                }
            }
            else if (chosenAttack.getAanvalSoort().equals("Multi Hit")){
                for (int i = 0; i < 2; i++){
                    speler.setAmountOfLives(speler.getAmountOfLives() - getDamageThisRound());
                }
            }
            else{
                System.out.println("Damage effect not found.");
            }
            
            if (speler.getAmountOfLives() < 0){
                speler.setAmountOfLives(0);
            }
        }
    }
    public void checkHeal(){
        if (chosenAttack.getAanvalSoort().equals("Heal Self")){
            setAmountOfLives(amountOfLives + getDamageThisRound());
        }
        else if (chosenAttack.getAanvalSoort().equals("Heal All")){
            for (Vijand vijand: Spel.getVijanden()){
                if (!vijand.isDead()){
                    vijand.setAmountOfLives(vijand.getAmountOfLives() + getDamageThisRound());
                }
            }
        }
        else{
            System.out.println("Heal effect not found.");
        }
    }
    public void calculateDamage(){
        damageThisRound = (int) Math.round((0.8 + Math.random() * (1.2 - 0.8)) * chosenAttack.getWaarde());
    }
    public Speler huidigeSpeler(){
        return huidigeSpeler;
    }
    public String getEnemyKind(){
        return enemyKind;
    }
    public int calculateCoinDrop(){
        double randomFactor = rnd.nextDouble() * 0.4 + 0.8; // genereert een getal tussen 0.8 en 1.2
        coinDropThisRound = (int) Math.round(baseCoinDrop * randomFactor);
        
        return coinDropThisRound;
    }
    public String determineName(){
        // "James", "Robert", "John", "Michael", "David", "William" | "Mary", "Patricia", "Jennifer", "Linda", "Lisa"
        // bro this method took me so long to write and it finally works
        List<String> possibleNamesArrayList = new ArrayList<>(Arrays.asList(possibleNames));
        
        int choosingNameIndex = rnd.nextInt(possibleNamesArrayList.size());
        name = possibleNames[choosingNameIndex];
        
        possibleNamesArrayList.subList(choosingNameIndex, choosingNameIndex + 1).clear();
        possibleNames = possibleNamesArrayList.toArray(new String[possibleNamesArrayList.size()]);
        
        return name;
    }
    public void printDamageThisRound(){
        String playersHit = speler.getName();
        if (chosenAttack.getAanvalSoort().contains("All")){
            playersHit = " everyone";
        }
        
        if (chosenAttack.getAanvalSoort().contains("Damage")){
            System.out.println(enemyKind + " " + name + " (" + getAmountOfLives() + " hp)" + " did " + damageThisRound + " damage with his attack " + chosenAttack.getName() + " to " + playersHit + ". ");
        }
        else if (chosenAttack.getAanvalSoort().contains("Heal")){
            if (playersHit.equals(speler.getName())){
                playersHit = " himself";
            }
            else{
                playersHit = " everyone from his team";
            }
            System.out.println(enemyKind + " " + name + " (" + getAmountOfLives() + " hp) healed" + playersHit + " for " + damageThisRound + ". ");
        }
        else{
            System.out.println(enemyKind + " " + name + " (" + getAmountOfLives() + " hp) did something.");
        }
    }
    public void printBaseCoinDrop(){
        System.out.println(baseCoinDrop);
    }
    public static Vijand[] getAllEnemies(){
        return allEnemies;
    }
    public int getBaseCoinDrop(){
        return baseCoinDrop;
    }
    public Aanval[] getAanvallen(){
        return aanvallen;
    }
    public int getDamageThisRound(){
        return damageThisRound;
    }
    public void setDamageThisRound(int damageThisRound){
        this.damageThisRound = damageThisRound;
    }
    public Speler getHuidigeSpeler(){
        return huidigeSpeler;
    }
    public void setHuidigeSpeler(Speler huidigeSpeler){
        this.huidigeSpeler = huidigeSpeler;
    }
    public Aanval getChosenAttack(){
        return chosenAttack;
    }
    public void setChosenAttack(Aanval chosenAttack){
        this.chosenAttack = chosenAttack;
    }
}