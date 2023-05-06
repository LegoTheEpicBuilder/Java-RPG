public class Aanval
{
    private String naam;
    private int waarde;
    private String description;
    private String aanvalSoort; //dit moet nog geïmplementeerd worden.
    
    private String[] categoriën;
    private Effect effect; //de effecten die horen bij een aanval
    
    private static String[] aanvalSoorten = {"Damage One", "Damage All", "Heal Self", "Heal All", "Buff Self", "Buff All", "Debuff One", "Debuff All"};
    private static String[] allBaseAttacks = {"Overhead Smash", "Crushing Blow", "Slash", "Spin Attack", "Stab", "Precision Throw", "Uppercut", "Quick Punches"};
    private static String[] allWeaponAttacks = {"Overhead Smash", "Crushing Blow", "Slash", "Spin Attack", "Stab", "Precision Throw", "Uppercut", "Quick Punches"};
    private static String[] allCategories = {"Melee", "Ranged", "Other"};
    
    private static Aanval[] allPlayerAttacks = new Aanval[]{
    new Aanval("Uppercut", 18, "Damage One", null, new String[] {"Melee"}, "Do an uppercut."),
    new Aanval("Quick Punches", 10, "Damage One", new Effect("Multi Hit", "/", 2, 0), new String[] {"Melee"}, "Hit the enemy twice rapidly."),
    new Aanval("Overhead Smash", 25, "Damage One", null, new String[] {"Melee"}, "Smash the enemy real hard."),
    new Aanval("Crushing Blow", 20, "Damage One", new Effect("Final Hit", "/", 30, 1.5), new String[] {"Melee"}, "Does 2x damage if enemy under 50 health."),
    new Aanval("Slash", 30, "Damage One", null, new String[] {"Melee"}, "Slash through the enemy."),
    new Aanval("Spin Attack", 15, "Damage All", null, new String[] {"Melee"}, "Does a spin attack with the weapon that let's it do damage to all enemies."),
    new Aanval("Stab", 15, "Damage One", new Effect("Damage Over Time", "Bleed", 5, 3), new String[] {"Melee"}, "Lets the attacked enemy bleed for 2 turns taking 5 damage each time."),
    new Aanval("Precision Throw", 20, "Damage One", null, new String[] {"Melee"}, "Throw your weapon to the chosen enemy."),
    new Aanval("Full Metal Jacket", 35, "Damage One", null, new String[] {"Ranged"}, "Shoot a devestating bullet to the enemy."),
    new Aanval("Final Blow", 20, "Damage One", new Effect("Final Hit", "/", 30, 1.5), new String[] {"Ranged"}, "Shoot a deadly bullet to the enemy. Does 2x damage if enemy under 50 health."),
    new Aanval("Arrow Shot", 25, "Damage One", null, new String[] {"Ranged"}, "Shoot the enemy with a general arrow."),
    new Aanval("Heavy Bolt", 28, "Damage One", new Effect("Final Hit", "/", 30, 1.5), new String[] {"Ranged"}, "Shoot a very heavy bolt at your enemy."),
    
    
    //aanvallen exclusief voor winkel
    new Aanval("Healing Drink", 25, "Heal Self", null, new String[] {"Other"}, "Heals the user a chunk of his lives back."),
    new Aanval("Medkit", 50, "Heal Self", null, new String[] {"Other"}, "Heals the user a lot."),
    new Aanval("Healing Spell", 15, "Heal All", null, new String[] {"Other"}, "Heals everyone in the team that isn't dead."),
    new Aanval("Arcane Blast", 30, "Damage One", null, new String[] {"Ranged"}, "SHAZAM! Attacks the player with a powerful spell."),
    new Aanval("Fireball", 15, "Damage One", new Effect("Damage Over Time", "Burn", 5, 3), new String[] {"Ranged"}, "Shoot a fireball setting the enemy on fire."),
    new Aanval("Ground Slam", 25, "Damage All", null, new String[] {"Melee"}, "Slam your weapon on the ground making it damage everyone."),
    new Aanval("Fire Arrow", 25, "Damage One", new Effect("Damage Over Time", "Burn", 5, 3), new String[] {"Ranged"}, "Hit your enemy with a fiery arrow setting him on fire."),
    new Aanval("Block", 18, "Damage One", null, new String[] {"Melee"}, "Block the next enemy attack partially."),
    new Aanval("Double Shot", 12, "Damage One", new Effect("Multi Hit", "/", 2, 0), new String[] {"Ranged"}, "Shoot an arrow twice rapidly."),
    };
    
    // static String[] allAttacks = {"Claws", "Roar", "Spell", "Healing Drink", "Punch", "Gold Steal", "Bite", "Grab"};
    private static Aanval[] allEnemyAttacks = new Aanval[]{
    new Aanval("Claws", 18, "Damage One", new Effect("Damage Over Time", "Bleed", 5, 3), new String[] {"Melee"}, "Attacks player with its sharp claws letting the player bleed."),
    new Aanval("Roar", 0, "Debuff One", null, new String[] {"Other"}, "Makes the player scared letting them do less damage."),
    new Aanval("Arcane Blast", 30, "Damage One", null, new String[] {"Ranged"}, "SHAZAM! Attacks the player with a powerful spell."),
    new Aanval("Healing Drink", 25, "Heal Self", null, new String[] {"Other"}, "Heals the user."),
    new Aanval("Punch", 15, "Damage One", null, new String[] {"Melee"}, "Punch the player."),
    new Aanval("Gold Steal", 5, "Damage One", new Effect("Remove Gold", "/", 20, 0), new String[] {"Melee"}, "Steals some coins of the players."),
    new Aanval("Bite", 15, "Damage One", new Effect("Damage Over Time", "Bleed", 5, 3), new String[] {"Melee"}, "Om nom nom."),
    new Aanval("Grab", 20, "Damage One", null, new String[] {"Melee"}, "Grabs the player.")
    };
    
    
    public Aanval(String naam, int waarde, String aanvalSoort, Effect effect, String[] categoriën, String description){
        this.naam = naam;
        this.waarde = waarde;
        this.aanvalSoort = aanvalSoort;
        this.effect = effect;
        this.categoriën = categoriën;
        this.description = description;
    }
    public static void determineBaseAttacks(Wapen wapen){
        int i = 0;
        
        for (String weaponName: wapen.getWeaponNames()){
            if (wapen.getWeaponName().equals(weaponName)){
                wapen.setBaseAanvallen(i);
                return;
            }
            i = i + 2;
        }
        System.out.println("You have the maximal amount of attacks.");
    }
    public static String[] getAllBaseAttacks(){
        return allBaseAttacks;
    }
    public static Aanval[] getAllPlayerAttacks(){
        return allPlayerAttacks;
    }
    public static Aanval[] getAllEnemyAttacks(){
        return allEnemyAttacks;
    }
    public String getName(){
        return naam;
    }
    public Effect getSpecialEffect(){
        return effect;
    }
    public String[] getCategoriën(){
        return categoriën;
    }
    public int getWaarde(){
        return waarde;
    }
    public String getDescription(){
        return description;
    }
    public static void printAllPlayerAttacks(){
        System.out.println("\n" + "__________" + "\n" + "Player attacks: " + "\n");
        for (int i = 0; i < allPlayerAttacks.length; i++){
            Aanval attack = Aanval.getAllPlayerAttacks()[i];
            System.out.println( i + 1 + ": " + attack.getName() + " | Power: " + attack.getWaarde());
            System.out.println("Description: " + attack.getDescription() + "\n");
            System.out.println("Effect: " + attack.getAanvalSoort() + "     " + attack.getEffect()); //moet veranderen
            System.out.println("Categories: ");
            
            for (String categorie: attack.getCategoriën()){
                System.out.print(categorie + "     ");
            }
            System.out.println(); //rare bug fixen
            System.out.println("_____");
        }
    }
    public static void printAllEnemyAttacks(){
        System.out.println("\n" + "__________" + "\n" + "Enemy attacks: " + "\n");
        for (int i = 0; i < allEnemyAttacks.length; i++){
            Aanval attack = Aanval.getAllEnemyAttacks()[i];
            System.out.println( i + 1 + ": " + attack.getName() + " | Power: " + attack.getWaarde());
            System.out.println("Description: " + attack.getDescription() + "\n");
            System.out.println("Effect: " + attack.getAanvalSoort() + "     " + attack.getEffect()); //moet veranderen
            System.out.println("Categories: ");
            
            for (String categorie: attack.getCategoriën()){
                System.out.print(categorie + "     ");
            }
            System.out.println(); //rare bug fixen
            System.out.println("_____");
        }
    }
    public static String[] getAanvalSoorten(){
        return aanvalSoorten;
    }
    public String getAanvalSoort(){
        return aanvalSoort;
    }
    public void setAanvalSoort(String aanvalSoort){
        this.aanvalSoort = aanvalSoort;
    }
    public Effect getEffect(){
        return effect;
    }
    public void setEffect(Effect effect){
        this.effect = effect;
    }
}
