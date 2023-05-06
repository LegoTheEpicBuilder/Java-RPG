import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Speler extends Entiteit
{
    private Vijand vijand;
    private boolean isDead = false;
    private Vijand huidigeVijand;
    private Wapen weapon;
    
    private int damageOverTime = 0; //moet weg
    private ArrayList <Effect> damageOverTimeEffects = new ArrayList <Effect>(); //moet nog gecodeerd worden
    
    private Random rnd = new Random();
    private static Scanner sc = new Scanner(System.in);
    
    public Speler(String name){
        super(name);
        weapon = Wapen.getAllWeapons()[0];
        Aanval.determineBaseAttacks(weapon);
    }
    public void chooseEnemy(Vijand vijand){
        this.vijand = vijand;
        huidigeVijand = vijand;
        
        if (Spel.getOverflowCheck() == 0){
            Spel.setOverflowCheck(1);
            vijand.choosePlayer(this);
        }
        else{
            Spel.setOverflowCheck(0);
        }
    }
    public Vijand getHuidigeVijand(){
        return huidigeVijand;
    }
    
    public void attack(){
        int j = 0;
        
        //damage
        if (weapon.getChosenAttack().getAanvalSoort().contains("Damage")){
            weapon.calculateDamage();
            checkDamage();
            
            //checkDamageOne();
            //checkDamageAll();
            //printDamageThisRound();
        }
        //heal
        else if (weapon.getChosenAttack().getAanvalSoort().contains("Heal")){
            weapon.calculateDamage();
            checkHeal();
        }
        //buff
        else if (weapon.getChosenAttack().getAanvalSoort().contains("Buff")){
            
        }
    }
    public void checkDamage(){
        try{
            String effectKind = weapon.getChosenAttack().getEffect().getEffectKind();
        
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
        if (weapon.getChosenAttack().getAanvalSoort().equals("Damage All")){
            for (Vijand vijandje: Spel.getVijanden()){
                if (weapon.getChosenAttack().getAanvalSoort().equals("Normal")){
                    vijandje.setAmountOfLives(vijandje.getAmountOfLives() - weapon.getDamageThisRound());
                }
                else if (weapon.getChosenAttack().getAanvalSoort().equals("Damage Over Time")){
                    vijandje.setAmountOfLives(vijandje.getAmountOfLives() - weapon.getDamageThisRound());
                    vijandje.setDamageOverTime(weapon.getChosenAttack().getEffect()); //vijandje.getDamageOverTime() + 2
                }
                else if (weapon.getChosenAttack().getAanvalSoort().equals("Final Hit")){
                    if (vijandje.getAmountOfLives() <= 50){
                        weapon.setDamageThisRound(weapon.getChosenAttack().getWaarde() * 2);
                        vijandje.setAmountOfLives(vijandje.getAmountOfLives() - weapon.getDamageThisRound());
                    }
                    else{
                        vijandje.setAmountOfLives(vijandje.getAmountOfLives() - weapon.getDamageThisRound());
                    }
                }
                else if (weapon.getChosenAttack().getAanvalSoort().equals("Multi Hit")){
                    for (int i = 0; i < 2; i++){
                        vijandje.setAmountOfLives(vijandje.getAmountOfLives() - weapon.getDamageThisRound());
                    }
                }
                else{
                    System.out.println("Damage effect not found.");
                }
                
                if (vijand.getAmountOfLives() < 0){
                    vijand.setAmountOfLives(0);
                }
            }
        }
    }
    public void checkDamageOne(){
        if (weapon.getChosenAttack().getAanvalSoort().equals("Damage One")){
            if (weapon.getChosenAttack().getAanvalSoort().equals("Normal")){
                    vijand.setAmountOfLives(vijand.getAmountOfLives() - weapon.getDamageThisRound());
            }
            else if (weapon.getChosenAttack().getAanvalSoort().equals("Damage Over Time")){
                vijand.setAmountOfLives(vijand.getAmountOfLives() - weapon.getDamageThisRound());
                vijand.setDamageOverTime(weapon.getChosenAttack().getEffect()); //vijand.getDamageOverTime() + 2
            }
            else if (weapon.getChosenAttack().getAanvalSoort().equals("Final Hit")){
                if (vijand.getAmountOfLives() <= 50){
                    weapon.setDamageThisRound(weapon.getChosenAttack().getWaarde() * 2);
                    vijand.setAmountOfLives(vijand.getAmountOfLives() - weapon.getDamageThisRound());
                }
                else{
                    vijand.setAmountOfLives(vijand.getAmountOfLives() - weapon.getDamageThisRound());
                }
            }
            else if (weapon.getChosenAttack().getAanvalSoort().equals("Multi Hit")){
                for (int i = 0; i < 2; i++){
                    vijand.setAmountOfLives(vijand.getAmountOfLives() - weapon.getDamageThisRound());
                }
            }
            else{
                System.out.println("Damage effect not found.");
            }
            
            if (vijand.getAmountOfLives() < 0){
                vijand.setAmountOfLives(0);
            }
        }
    }
    public void checkHeal(){
        if (weapon.getChosenAttack().getAanvalSoort().equals("Heal Self")){
            setAmountOfLives(weapon.getDamageThisRound());
        }
        else if (weapon.getChosenAttack().getAanvalSoort().equals("Heal All")){
            for (Speler speler: Spel.getSpelers()){
                if (!speler.isDead()){
                    speler.setAmountOfLives(weapon.getDamageThisRound());
                }
            }
        }
        else{
            System.out.println("Heal effect not found.");
        }
    }
    public void chooseAttack(){
        boolean isValidInput = false;
        
        while (!isValidInput){
            System.out.println("What attack does " + name + " need to use?");
            weapon.printAttacksFromWeapon(weapon.getAanvallen());
            
            try{
                int chosenAttackIndex = sc.nextInt() - 1;
                
                if (weapon.getAanvallen()[chosenAttackIndex] != null){
                    isValidInput = true;
                    weapon.setChosenAttack(weapon.getAanvallen()[chosenAttackIndex]);
                }
            }
            catch(Exception e){
                System.out.println("Attack not found. Try again.");
                sc.nextLine();
            }
        }
    }
    
    public void printDamageThisRound(){
        String multiHitText = "";
        
        try{
            if(weapon.getChosenAttack().getEffect().getEffectKind().equals("Multi Hit")){
                multiHitText = " (x" + weapon.getChosenAttack().getEffect().getValue1() + ")";
            }
        }
        catch (Exception e){
            
        }
        if (weapon.getChosenAttack().getAanvalSoort().equals("Damage One")){
            System.out.println(name + " did " + weapon.getDamageThisRound() + " damage to " + vijand.getName() + " this round." + multiHitText);
        }
        else if (weapon.getChosenAttack().getAanvalSoort().equals("Damage All")){
            System.out.println(name + " did " + weapon.getDamageThisRound() + " damage to everyone this round." + multiHitText);
        }
    }
    
    public Wapen getWeapon(){
        return weapon;
    }
    public void setWeapon(Wapen weapon){
        this.weapon = weapon;
    }
    public int getDamageOverTime(){
        return damageOverTime;
    }
}