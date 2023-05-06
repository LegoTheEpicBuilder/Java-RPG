import java.util.Random;

public class Effect{
    private String effectKind;
    private String name;
    private int value1;
    private double value2;
    
    private static String[] allEffectKinds = {"Normal", "Damage Over Time", "Final Hit", "Multi Hit", "Remove Gold"};
    
    //special effects will vary alot so not all of them are in this array       example: Damage Over Time
    private static Effect[] allEffects = {
    new Effect("Normal", "/", 0, 0),
    new Effect("Damage Over Time", "/", 5, 3), //value1 = damage, value2 = amount of rounds
    // --> In Vijand en Speler moet een array van damage over time effecten opgeslagen worden en moet voor elk damage over time effect schade oplopen.
    //     Als er een effect dezelfde naam heeft dan een andere, wordt deze vervangen door de nieuwste.
    
    new Effect("Final Hit", "/", 30, 1.5), //value1 = percent health needed to activate, value2 = damage multiplier --> max health is not a thing yet WIP
    new Effect("Multi Hit", "/", 2, 0), //value1 = amount of hits
    new Effect("Remove Gold", "/", 20, 0) //value1 = average amount of gold stolen
    };
    
    //name of the special effect, value1 = mostly damage, value2 = amount of rounds / damage multiplier(optional)
    // --> use 0 as standard value
    public Effect(String effectKind, String name, int value1, double value2){
        this.effectKind = effectKind;
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
    }
    public static void normalOld(Speler speler){
        //Neemt de effectSoort van uit de gekozen aanval.
        //Doet schade naargelang effectSoort: 'all' = elke vijand aanvallen (die nog niet dood is), 'one' = de gekozen vijand aanvallen
        //Neemt daarachter de gegenreerde damageThisRound and doet deze schade naar de vijand(en).
        String aanvalSoort = speler.getWeapon().getChosenAttack().getAanvalSoort();
        
        if (aanvalSoort.equals("Damage One")){
            Vijand vijand = speler.getHuidigeVijand();
            vijand.setAmountOfLives(vijand.getAmountOfLives() - speler.getWeapon().getDamageThisRound());
            
            vijand.isDead();
            speler.printDamageThisRound();
        }
        else if (aanvalSoort.equals("Damage All")){
            for (Vijand vijand: Spel.getVijanden()){
                if (!vijand.getIsDead()){
                    vijand.setAmountOfLives(vijand.getAmountOfLives() - speler.getWeapon().getDamageThisRound());
                    
                    vijand.isDead();
                    
                    speler.printDamageThisRound();
                }
            }
        }
        else{
            System.out.println("Something went wrong.");
        }
    }
    public static <T> void normal(T object){
        //Neemt de effectSoort van uit de gekozen aanval.
        //Doet schade naargelang effectSoort: 'all' = elke vijand aanvallen (die nog niet dood is), 'one' = de gekozen vijand aanvallen
        //Neemt daarachter de gegenreerde damageThisRound and doet deze schade naar de vijand(en).
        if (object instanceof Speler){
            Speler speler = (Speler) object;
            
            String aanvalSoort = speler.getWeapon().getChosenAttack().getAanvalSoort();
        
            if (aanvalSoort.equals("Damage One")){
                Vijand vijand = speler.getHuidigeVijand();
                vijand.setAmountOfLives(vijand.getAmountOfLives() - speler.getWeapon().getDamageThisRound());
                
                vijand.isDead();
                speler.printDamageThisRound();
            }
            else if (aanvalSoort.equals("Damage All")){
                for (Vijand vijand: Spel.getVijanden()){
                    if (!vijand.getIsDead()){
                        vijand.setAmountOfLives(vijand.getAmountOfLives() - speler.getWeapon().getDamageThisRound());
                        
                        vijand.isDead();
                        
                        speler.printDamageThisRound();
                    }
                }
            }
            else{
                System.out.println("Something went wrong.");
            }
        }
        else if (object instanceof Vijand){
            Vijand vijand = (Vijand) object;
            Speler speler = vijand.getHuidigeSpeler();
            
            String aanvalSoort = vijand.getChosenAttack().getAanvalSoort();
            
            if (aanvalSoort.equals("Damage One")){
                speler.setAmountOfLives(speler.getAmountOfLives() - vijand.getDamageThisRound());
                
                speler.isDead();
                vijand.printDamageThisRound();
            }
            else if (aanvalSoort.equals("Damage All")){
                for (Speler spelertje: Spel.getSpelers()){
                    if (!spelertje.isDead()){
                        spelertje.setAmountOfLives(spelertje.getAmountOfLives() - vijand.getDamageThisRound());
                        
                        spelertje.isDead();
                        
                        vijand.printDamageThisRound();
                    }
                }
            }
            else{
                System.out.println("Something went wrong.");
            }
            //verdere code hier
        }
    }
    public static <T> void damageOverTime(T object){
        //Zoals in Normal maar voegt ook het DamageOverTime effect toe aan een array in Speler / Vijand waar deze de schade wordt gegeven na elke ronde.
        if (object instanceof Speler){
            Speler speler = (Speler) object;
            Aanval aanval = speler.getWeapon().getChosenAttack();
            
            if (aanval.getAanvalSoort().equals("Damage One")){
                Vijand vijand = speler.getHuidigeVijand();
                vijand.setDamageOverTime(aanval.getEffect());
                
                vijand.isDead();
                
                speler.printDamageThisRound();
            }
            else if (aanval.getAanvalSoort().equals("Damage All")){
                for (Vijand vijand: Spel.getVijanden()){
                    if (!vijand.isDead()){
                        vijand.setDamageOverTime(aanval.getEffect());
                        
                        vijand.isDead();
                        
                        speler.printDamageThisRound();
                    }
                }
            }
            else{
                System.out.println("Something went wrong.");
            }
        }
        else if (object instanceof Vijand){
            Vijand vijand = (Vijand) object;
            Speler speler = vijand.getHuidigeSpeler();
            Aanval aanval = vijand.getChosenAttack();
            
            String aanvalSoort = vijand.getChosenAttack().getAanvalSoort();
            
            if (aanvalSoort.equals("Damage One")){
                speler.setDamageOverTime(aanval.getEffect());
                
                speler.isDead();
                vijand.printDamageThisRound();
            }
            else if (aanvalSoort.equals("Damage All")){
                for (Speler spelertje: Spel.getSpelers()){
                    if (!spelertje.isDead()){
                        spelertje.setDamageOverTime(aanval.getEffect());
                
                        spelertje.isDead();
                        
                        vijand.printDamageThisRound();
                    }
                }
            }
            else{
                System.out.println("Something went wrong.");
            }
            //verdere code hier
        }
    }
    public static <T> void multiHit(T object){
        //doet value1 keer zijn aanval.
        if (object instanceof Speler){
            Speler speler = (Speler) object;
            Aanval aanval = speler.getWeapon().getChosenAttack();
        
            if (aanval.getAanvalSoort().equals("Damage One")){
                Vijand vijand = speler.getHuidigeVijand();
                
                for (int i = 0; i < aanval.getEffect().getValue1(); i++){
                    vijand.setAmountOfLives(vijand.getAmountOfLives() - speler.getWeapon().getDamageThisRound());
                    vijand.isDead();
                }
                
                speler.printDamageThisRound();
            }
            else if (aanval.getAanvalSoort().equals("Damage All")){
                for (Vijand vijand: Spel.getVijanden()){
                    if (!vijand.getIsDead()){
                        for (int i = 0; i < aanval.getEffect().getValue1(); i++){
                            vijand.setAmountOfLives(vijand.getAmountOfLives() - speler.getWeapon().getDamageThisRound());
                            
                            vijand.isDead();
                        }
                        
                        speler.printDamageThisRound();
                    }
                }
            }
            else{
                System.out.println("Something went wrong.");
            }
        }
        else if (object instanceof Vijand){
            Vijand vijand = (Vijand) object;
            Speler speler = vijand.getHuidigeSpeler();
            Aanval aanval = vijand.getChosenAttack();
            
            String aanvalSoort = vijand.getChosenAttack().getAanvalSoort();
            
            if (aanvalSoort.equals("Damage One")){
                for (int i = 0; i < aanval.getEffect().getValue1(); i++){
                    speler.setAmountOfLives(speler.getAmountOfLives() - vijand.getDamageThisRound());
                    speler.isDead();
                }
                
                vijand.printDamageThisRound();
            }
            else if (aanvalSoort.equals("Damage All")){
                for (Speler spelertje: Spel.getSpelers()){
                    if (!spelertje.isDead()){
                        for (int i = 0; i < aanval.getEffect().getValue1(); i++){
                            spelertje.setAmountOfLives(spelertje.getAmountOfLives() - vijand.getDamageThisRound());
                            
                            spelertje.isDead();
                        }
                        
                        speler.printDamageThisRound();
                    }
                }
            }
            else{
                System.out.println("Something went wrong.");
            }
            //verdere code hier
        }
    }
    public static <T> void finalHit(T object){
        //checkt (voor de gekozen vijand of elke vijand) als zijn levens % onder of gelijk aan value1 zijn van hun max levens en doet dan value2 meer schade. Anders normale schade.
        double damageMultiplier = 1;
        double damageMultiplier2 = 1;
        
        Random rnd = new Random();
        if (object instanceof Speler){
            Speler speler = (Speler) object;
            Vijand vijand = speler.getHuidigeVijand();
            Aanval aanval = speler.getWeapon().getChosenAttack();
            
            if (Math.round(vijand.getAmountOfLives() / vijand.getMaxAmountOfLives()* 100) <= aanval.getEffect().getValue1()){ //80 / 40 * 100      % = 30   --> 50 <= 30 = false
                damageMultiplier2 = aanval.getEffect().getValue2();
                
            }
            //schade berekenen
            int getal2 = rnd.nextInt(100) + 1;
            
            if (speler.getWeapon().getCritChance() >= getal2){
                damageMultiplier = 1.5;
                System.out.println("Crit!");
            }
            
            speler.getWeapon().setDamageThisRound((int) Math.round((0.8 + Math.random() * (1.2 - 0.8)) * aanval.getWaarde() * damageMultiplier2 * damageMultiplier));
            
            if (aanval.getAanvalSoort().equals("Damage One")){
                vijand.setAmountOfLives(vijand.getAmountOfLives() - speler.getWeapon().getDamageThisRound());
                vijand.isDead();
                
                speler.printDamageThisRound();
            }
            else if (aanval.getAanvalSoort().equals("Damage All")){
                for (Vijand vijandje: Spel.getVijanden()){
                    if (!vijandje.getIsDead()){
                        for (int i = 0; i < aanval.getEffect().getValue1(); i++){
                            vijandje.setAmountOfLives(vijandje.getAmountOfLives() - speler.getWeapon().getDamageThisRound());
                            
                            vijandje.isDead();
                        }
                        
                        speler.printDamageThisRound();
                    }
                }
            }
        }
        else if (object instanceof Vijand){
            Vijand vijand = (Vijand) object;
            Speler speler = vijand.getHuidigeSpeler();
            Aanval aanval = vijand.getChosenAttack();
            
            String aanvalSoort = vijand.getChosenAttack().getAanvalSoort();
            
            if (Math.round(speler.getAmountOfLives() / speler.getMaxAmountOfLives() * 100) <= aanval.getEffect().getValue1()){ //80 / 40 * 100      % = 30   --> 50 <= 30 = false
                damageMultiplier2 = aanval.getEffect().getValue2();
                
            }
            
            //schade berekenen
            vijand.setDamageThisRound((int) Math.round((0.8 + Math.random() * (1.2 - 0.8)) * aanval.getWaarde() * damageMultiplier2 * damageMultiplier));
            
            if (aanval.getAanvalSoort().equals("Damage One")){
                speler.setAmountOfLives(speler.getAmountOfLives() - vijand.getDamageThisRound());
                speler.isDead();
                
                vijand.printDamageThisRound();
            }
            else if (aanval.getAanvalSoort().equals("Damage All")){
                for (Speler spelertje: Spel.getSpelers()){
                    if (!spelertje.isDead()){
                        for (int i = 0; i < aanval.getEffect().getValue1(); i++){
                            spelertje.setAmountOfLives(spelertje.getAmountOfLives() - vijand.getDamageThisRound());
                            
                            spelertje.isDead();
                        }
                        
                        vijand.printDamageThisRound();
                    }
                }
            }
            else{
                System.out.println("Something went wrong.");
            }
            //verdere code hier
        }
    }
    public static <T> void removeGold(T object){
        //neemt value1 munten weg van de speler (is eerst random gegenereerd zoals DamageThisRound).
        if (object instanceof Speler){
            Speler speler = (Speler) object;
            Vijand vijand = speler.getHuidigeVijand();
            Aanval aanval = speler.getWeapon().getChosenAttack();
            
            if (aanval.getAanvalSoort().equals("Damage One")){
                if (!vijand.isDead()){
                    int stolenCoins = speler.getWeapon().calculateValue(speler.getWeapon().getChosenAttack().getEffect().getValue1());
                    Rugzak.setCoins(Rugzak.getCoins() + stolenCoins);
                    
                    System.out.println("Stole " + stolenCoins + " coins from " + vijand.getName());
                    normal(speler);
                }
                else{
                    System.out.println("You cannot steal coins from a dead enemy.");
                }
            }
            else if (aanval.getAanvalSoort().equals("Damage All")){
                int totalStolenCoins = 0;
                
                for (Vijand vijandje: Spel.getVijanden()){
                    if (!vijandje.isDead()){
                        int stolenCoins = speler.getWeapon().calculateValue(speler.getWeapon().getChosenAttack().getEffect().getValue1());
                        
                        totalStolenCoins += stolenCoins;
                        Rugzak.setCoins(Rugzak.getCoins() + stolenCoins);
                        
                        normal(speler);
                        vijandje.isDead();
                        
                        speler.printDamageThisRound();
                    }
                    System.out.println("Stole " + totalStolenCoins + " coins in total.");
                }
            }
        }
        else if (object instanceof Vijand){
            Vijand vijand = (Vijand) object;
            Speler speler = vijand.getHuidigeSpeler();
            Aanval aanval = vijand.getChosenAttack();
            
            String aanvalSoort = vijand.getChosenAttack().getAanvalSoort();
            
            if (aanvalSoort.equals("Damage One")){
                if (!speler.isDead()){
                    int stolenCoins = speler.getWeapon().calculateValue(vijand.getChosenAttack().getEffect().getValue1());
                    Rugzak.setCoins(Rugzak.getCoins() - stolenCoins);
                    
                    System.out.println(vijand.getName() + " stole " + stolenCoins + " coins.");
                    normal(vijand);
                }
            }
            else if (aanvalSoort.equals("Damage All")){
                //dit is raar voor vijand.
                
                if (!speler.isDead()){
                    int stolenCoins = speler.getWeapon().calculateValue(vijand.getChosenAttack().getEffect().getValue1());
                    Rugzak.setCoins(Rugzak.getCoins() - stolenCoins);
                    
                    System.out.println(vijand.getName() + " stole " + stolenCoins + " coins.");
                    normal(vijand);
                }
            }
            else{
                System.out.println("Something went wrong.");
            }
            //verdere code hier
        }
    }
    public String getEffectKind(){
        return effectKind;
    }
    public void setEffectKind(){
        this.effectKind = effectKind;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getValue1(){
        return value1;
    }
    public void setValue1(int value1){
        this.value1 = value1;
    }
    public double getValue2(){
        return value2;
    }
    public void setValue2(double value2){
        this.value2 = value2;
    }
    public static String[] getAllEffectKinds(){
        return allEffectKinds;
    }
    public static Effect[] getAllEffects(){
        return allEffects;
    }
}