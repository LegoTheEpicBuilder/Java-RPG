import java.util.ArrayList;

public class Entiteit{
    protected String name = "Unknown";
    protected int maxAmountOfLives;
    protected int amountOfLives;
    protected boolean isDead = false;
    
    protected ArrayList <Effect> damageOverTimeEffects = new ArrayList <Effect>();
    
    public Entiteit(String name){   //voor Speler
        this.name = name;
        amountOfLives = 100;
        maxAmountOfLives = 100;
    }
    public Entiteit(int amountOfLives){ //voor Vijand
        this.amountOfLives = amountOfLives;
        maxAmountOfLives = amountOfLives;
    }
    
    public boolean isDead(){
        if (amountOfLives <= 0){
            isDead = true;
        }
        return isDead;
    }
    
    public void doDamageOverTime(){
        for (Effect effect: damageOverTimeEffects){
            if (effect.getValue2() > 0){
                amountOfLives -= effect.getValue1();
                effect.setValue2(Math.round(effect.getValue2() - 1));
                
                if (amountOfLives < 0){
                    amountOfLives = 0;
                }
                
                printDamageOverTime();
            }
            if (effect.getValue2() == 0){
                damageOverTimeEffects.remove(effect);
            }
        }
    }
    public void printDamageOverTime(){
        if (amountOfLives != 0){
            System.out.println(name + " got damaged by a DoT effect for 5 damage. (" + amountOfLives + "hp)");
        }
        else{
            System.out.println(name + " got damaged by a DoT effect for 5 damage. (dead)");
        }
    }
    
    //getters and setters
    public void setAmountOfLives(int amountOfLives){
        this.amountOfLives = amountOfLives;
    }
    public int getAmountOfLives(){
        return amountOfLives;
    }
    public String getName(){
        return name;
    }public void setDamageOverTime(Effect effect){
        damageOverTimeEffects.add(effect);
    }
    public ArrayList <Effect> getDamageOverTimeEffects(){
        return damageOverTimeEffects;
    }
    public int getMaxAmountOfLives(){
        return maxAmountOfLives;
    }
    public void setMaxAmountOfLives(int maxAmountOfLives){
        this.maxAmountOfLives = maxAmountOfLives;
    }
    public void setIsDead(boolean isDead){
        this.isDead = isDead;
    }
    public boolean getIsDead(){
        return isDead;
    }
}