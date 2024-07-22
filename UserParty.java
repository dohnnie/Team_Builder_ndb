/*
 * Class Design: This class needs to be able to hold the data of the entire party, along with data of each pokemon individually
 */
import java.util.*;

 public class UserParty
 {
    private Pokemon[] party;

    UserParty(int partySize, String[] pokemonNames, String[][] pTypes, String[][] mTypes) {
        party = new Pokemon[partySize];

        for(int i = 0; i < partySize; i++) {
            party[i] = new Pokemon(pokemonNames[i], pTypes[i], mTypes[i]);
        }
    }

    public String getPokemonResists(int index) {
        String resists = "";
        String[] resists_list = this.party[index].getResistances(this.party[index].getResists());

        for(int i = 0; i < resists_list.length; i++) {
            resists += resists_list[i] + ", ";
        }

        return resists;
    }

    public String getPokemonWeakness(int index) {
        String weakness = "";
        String[] weakness_list = this.party[index].getResistances(this.party[index].getWeakness());
        for(int i = 0; i < weakness_list.length; i++) {
            weakness += weakness_list[i] + ", ";
        }

        return weakness;
    }

    public String getPokemonImmunity(int index) {
        String immunity = "";
        String[] immunity_list = this.party[index].getResistances(this.party[index].getImmunity());

        for(int i = 0; i < immunity_list.length; i++) {
            immunity += immunity_list[i] + ", ";
        }

        return immunity;
    }

    public String getPokemonCoverage(int index) {
        String coverage = "";
        String[] coverage_list = this.party[index].convertConverage(this.party[index].getCoverage());

        for(int i = 0; i < coverage_list.length; i++) {
            coverage += coverage_list[i] + ", ";
        }

        return coverage;
    }

    public String getPokemonCoverageNeeded(int index) {
        String noCoverage = "";
        String[] noCoverage_list = this.party[index].convertConverage(this.party[index].getNoCoverage());

        for(int i = 0; i < noCoverage_list.length; i++) {
            noCoverage += noCoverage_list[i] + ", ";
        }

        return noCoverage;
    }
 }
 
class Pokemon
{
    private String[] allTypes = {"NORMAL", "FIRE", "WATER", "ELECTRIC", "GRASS", "ICE", "FIGHTING",
                                 "POISON", "GROUND", "FLYING", "PSYCHIC", "BUG", "ROCK", "GHOST",
                                 "DRAGON", "DARK", "STEEL", "FAIRY"};

    private String name;
    private String[] types;
    private String[] mTypes;
    private float[] resistances = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    private HashSet<String> t_resists = new HashSet<>();
    private HashSet<String> t_immune = new HashSet<>();
    private HashSet<String> t_weak = new HashSet<>();

    private HashSet<String> t_coverage = new HashSet<>();
    private HashSet<String> t_noCoverage = new HashSet<>();

    Pokemon(String name, String[] types, String[] mTypes) {
        this.types = new String[types.length];
        this.mTypes = new String[mTypes.length];

        this.name = name;

        for(int i = 0; i < types.length; i++) {
            this.types[i] = types[i];
        }

        for(int i = 0; i < mTypes.length; i++) {
            this.mTypes[i] = mTypes[i];
        }


        calcResists();
        setTypeResists();

        this.mTypes = removeDuplicates(this.mTypes);
        setCoverage(mTypes);
    }
    
    public String getName() {
        return this.name;
    }

    public String[] getTypes() {
        return this.types;
    }

    public String[] getMTypes() {
        return this.mTypes;
    }

    public HashSet<String> getResists() {
        return this.t_resists;
    }

    public HashSet<String> getWeakness() {
        return this.t_weak;
    }

    public HashSet<String> getImmunity() {
        return this.t_immune;
    }

    public HashSet<String> getCoverage() {
        return this.t_coverage;
    }

    public HashSet<String> getNoCoverage() {
        return this.t_noCoverage;
    }

    public String[] getResistances(HashSet<String> resists_list) {
        String[] resistances = new String[resists_list.size()];
        int index = 0;
        for(String type : resists_list) {
            resistances[index] = type;
            index++;
        }

        return resistances;
    }

    public String[] convertConverage(HashSet<String> coverage_list) {
        String[] coverage = new String[coverage_list.size()];
        int index = 0;
        for(String type : coverage_list) {
            coverage[index] = type;
            index++;
        }

        return coverage;
    }
    
    public void calcResists() {        

        for(int i = 0; i < types.length; i++) {
            types[i] = types[i].toUpperCase();

            /*
             * normal = 0, fire = 1, water = 2, electric = 3, grass = 4, ice = 5, fighting = 6
             * poison = 7, ground = 8, flying = 9, psychic = 10, bug = 11, rock = 12, ghost = 13
             * dragon = 14, dark = 15, steel = 16, fairy = 17
             */
            if(types[i].equals("NORMAL")) {
                resistances[6] *= 0.5f; //2x weakness to fighting
                resistances[13] *= 0f; //immune to ghost
            }
            else if(types[i].equals("FIRE")) {
                resistances[1] *= 2.0f; //resists fire
                resistances[2] *= 0.5f; //2x weakness to water
                resistances[4] *= 2.0f; //resists grass
                resistances[5] *= 2.0f; //resists ice
                resistances[8] *= 0.5f; //2x weakness to ground
                resistances[11] *= 2.0f;//resists bug
                resistances[12] *= 0.5f;//2x weakness to rock
                resistances[16] *= 2.0f;//resists steel
                resistances[17] *= 2.0f;//resists fairy
            }
            else if(types[i].equals("WATER")) {
                resistances[1] *= 2.0f; //resists fire
                resistances[2] *= 2.0f; //resists water;
                resistances[3] *= .5f; //2x weakness to electric
                resistances[4] *= .5f; //2x weakness to grass
                resistances[5] *= 2.0f; //resists ice
                resistances[16] *= 2.0f; //resists steel
            }
            else if(types[i].equals("ELECTRIC")) {
                resistances[3] *= 2.0f; //resists electric
                resistances[8] *= 0.5f; //2x weakness to ground
                resistances[9] *= 2.0f; //resists flying
                resistances[16] *= 2.0f; //resists steel
            }
            else if(types[i].equals("GRASS")) {
                resistances[1] *= 0.5f; //2x weakness to fire
                resistances[2] *= 2.0f; //resists water
                resistances[3] *= 2.0f; //resists electric
                resistances[4] *= 2.0f; //resists grass
                resistances[5] *= 0.5f; //2x weakness to ice
                resistances[7] *= 0.5f; //2x weakness to poison
                resistances[8] *= 2.0f; //resists ground
                resistances[9] *= 0.5f; //2x weakness to flying
                resistances[11] *= 0.5f; //2x weakness to bug
            }
            else if(types[i].equals("ICE")) {
                resistances[1] *= 0.5f; //2x weakness to fire
                resistances[5] *= 2.0f; //resists ice
                resistances[6] *= 0.5f; //2x weakness to fighting
                resistances[12] *= 0.5f; //2x weakness to rock
            }
            else if(types[i].equals("FIGHTING")) {
                resistances[9] *= 0.5f; //2x weakness to flying
                resistances[10] *= 0.5f; //2x weakness to psychic
                resistances[11] *= 2.0f; //resists bug
                resistances[12] *= 2.0f; //resists rock
                resistances[15] *= 2.0f; //resists dark
                resistances[17] *= 0.5f; //2x weakness to fairy
            }
            else if(types[i].equals("POISON")) {
                resistances[4] *= 2.0f; //resists grass
                resistances[6] *= 2.0f; //resists fighting
                resistances[7] *= 2.0f; //resists poison
                resistances[8] *= 0.5f; //2x weakness to ground
                resistances[10] *= 0.5f; //2x weakness to psychic
                resistances[11] *= 2.0f; //resists bug
                resistances[17] *= 2.0f; //resists fairy
            }
            else if(types[i].equals("GROUND")) {
                resistances[2] *= 0.5f; //2x weakness to water
                resistances[3] *= 0f; //immune to electric
                resistances[4] *= 0.5f; //2x weakness to grass
                resistances[5] *= 0.5f; //2x weakness to ice
                resistances[7] *= 2.0f; //resists poison
                resistances[12] *= 2.0f; //resists rock
            }
            else if(types[i].equals("FLYING")) {
                resistances[3] *= 0.5f; //2x weakness to electric
                resistances[4] *= 2.0f; //resists grass
                resistances[5] *= 0.5f; //2x weakness to ice
                resistances[6] *= 2.0f; //resists fighting
                resistances[8] *= 0f; //immune to ground
                resistances[11] *= 2.0f; //resists bug
                resistances[12] *= 0.5f; //2x weakness to rock
            }
            else if(types[i].equals("PSYCHIC")) {
                resistances[6] *= 2.0f; //resists fighting
                resistances[10] *= 2.0f; //resists psychic
                resistances[11] *= 0.5f; //2x weakness to bug
                resistances[13] *= 0.5f; //2x weakness to ghost
                resistances[15] *= 0.5f; //2x weakness to dark
            }
            else if(types[i].equals("BUG")) {
                resistances[1] *= 0.5f; //2x weakness to fire
                resistances[4] *= 2.0f; //resists grass
                resistances[6] *= 2.0f; //resists fighting
                resistances[8] *= 2.0f; //resists ground
                resistances[9] *= 0.5f; //2x weakness to flying
                resistances[12] *= 0.5f; //2x weakness to rock
            }
            else if(types[i].equals("ROCK")) {
                resistances[0] *= 2.0f; //resists normal
                resistances[1] *= 2.0f; //resists fire
                resistances[2] *= 0.5f; //2x weakness to water
                resistances[4] *= 0.5f; //2x weakness to grass
                resistances[6] *= 0.5f; //2x weakness to fighting
                resistances[7] *= 2.0f; //resists poison
                resistances[8] *= 0.5f; //2x weakness to ground
                resistances[9] *= 2.0f; //resists flying
                resistances[16] *= 0.5f; //2x weakness to steel
            }
            else if(types[i].equals("GHOST")) {
                resistances[0] *= 0f; //immune to normal
                resistances[6] *= 0f; //immunte to fighting
                resistances[7] *= 2.0f; //resists poison
                resistances[11] *= 2.0f; //resists bug
                resistances[13] *= 0.5f; //2x weakness to ghost
                resistances[15] *= 0.5f; //2x weakness to dark
            }
            else if(types[i].equals("DRAGON")) {
                resistances[1] *= 2.0f; //resists fire
                resistances[2] *= 2.0f; //resist water
                resistances[3] *= 2.0f; //resists electric
                resistances[4] *= 2.0f; //resists grass
                resistances[5] *= 0.5f; //2x weakness to ice
                resistances[14] *= 0.5f; //2x weakness to dragon
                resistances[17] *= 0.5f; //2x weakness to fairy
            }
            else if(types[i].equals("DARK")) {
                resistances[6] *= 0.5f; //2x weakness to fighting
                resistances[10] *= 0f; //immune to psychic
                resistances[11] *= 0.5f; //2x weakness to bug
                resistances[13] *= 2.0f; //resists ghost
                resistances[15] *= 2.0f; //resists dark
                resistances[17] *= 0.5f; //2x weakness to fairy
            }
            else if(types[i].equals("STEEL")) {
                resistances[0] *= 2.0f; //resists normal
                resistances[1] *= 0.5f; //2x weakness to fire
                resistances[4] *= 2.0f; //resists grass
                resistances[5] *= 2.0f; //resists ice
                resistances[6] *= 0.5f; //2x weakness to fighting
                resistances[7] *= 0f; //immune to poison
                resistances[8] *= 0.5f; //2x weakness to ground
                resistances[9] *= 2.0f; //resists flying
                resistances[10] *= 2.0f; //resists psychic
                resistances[11] *= 2.0f; //resists bug
                resistances[12] *= 2.0f; //resists rock
                resistances[14] *= 2.0f; //resists dragon
                resistances[16] *= 2.0f; //resists steel
                resistances[17] *= 2.0f; //resists fairy
            }
            else if(types[i].equals("FAIRY")) {
                resistances[6] *= 2.0f; //resists fighting
                resistances[7] *= 0.5f; //2x weakness to poison
                resistances[11] *= 0.5f; //2x weakness to bug
                resistances[14] *= 0f; //immune to dragon
                resistances[15] *= 2.0f; //resists dark
                resistances[16] *= 0.5f; //2x weakness to steel
            }
        }
    }

    public void setTypeResists() {
        for(int index = 0; index < resistances.length; index++) {
            float resists_value = resistances[index];
            if(resists_value > 1.0f) 
                this.t_resists.add(allTypes[index]);
            else if(resists_value < 1.0f && resists_value > 0)
                this.t_weak.add(allTypes[index]);
            else if(resists_value == 0f)
                this.t_immune.add(allTypes[index]);
        }
    }

    public float[] calcCoverage(String mType) {
        mType = mType.toUpperCase();

        float[] typeCoverage = new float[18];
        for(int i = 0; i < typeCoverage.length; i++) {
            typeCoverage[i] = 1.0f;
        }

        /*
             * normal = 0, fire = 1, water = 2, electric = 3, grass = 4, ice = 5, fighting = 6
             * poison = 7, ground = 8, flying = 9, psychic = 10, bug = 11, rock = 12, ghost = 13
             * dragon = 14, dark = 15, steel = 16, fairy = 17
             */
        if(mType.equals("NORMAL")) {
            typeCoverage[12] = 0.5f; //Not very effective against rock
            typeCoverage[13] = 0.0f; //No effect against ghost
            typeCoverage[16] = 0.5f; //Not very effective against steel
        }
        else if(mType.equals("FIRE")) {
            typeCoverage[1] = 0.5f; //Not very effective against fire
            typeCoverage[2] = 0.5f; //Not very effective against water
            typeCoverage[4] = 2.0f; //Super effective against grass
            typeCoverage[5] = 2.0f; //Super effective against grass
            typeCoverage[11] = 2.0f; //Super effective against bug
            typeCoverage[12] = 0.5f; //Not very effective against rock
            typeCoverage[14] = 0.5f; //Not very effective against dragon
            typeCoverage[16] = 2.0f; //Super effective against steel
        }
        else if(mType.equals("WATER")) {
            typeCoverage[1] = 2.0f; //Super effective against fire
            typeCoverage[2] = 0.5f; //Not very effective against water
            typeCoverage[4] = 0.5f; //Not very effective against grass
            typeCoverage[8] = 2.0f; //Super effective against ground
            typeCoverage[12] = 2.0f; //Super effective against rock
            typeCoverage[14] = 0.5f; //Not very effective against dragon
        }
        else if(mType.equals("ELECTRIC")) {
            typeCoverage[2] = 2.0f; //Super effective against water
            typeCoverage[3] = 0.5f; //Not very effective against electric
            typeCoverage[4] = 0.5f; //Not very effective against grass
            typeCoverage[8] = 0.0f; //No effect against ground
            typeCoverage[9] = 2.0f; //Super effective against flying
            typeCoverage[14] = 0.5f; //Not very effective against dragon
        }
        else if(mType.equals("GRASS")) {
            typeCoverage[1] = 0.5f; //Not very effective against grass
            typeCoverage[2] = 2.0f; //Super effective against water
            typeCoverage[4] = 0.5f; //Not very efffective against grass
            typeCoverage[7] = 0.5f; //Not very effective against poison
            typeCoverage[8] = 2.0f; //Super effective against ground
            typeCoverage[9] = 0.5f; //Not very effective against flying
            typeCoverage[11] = 0.5f; //Not very effective against bug
            typeCoverage[12] = 2.0f; //Super effective against rock
            typeCoverage[14] = 0.5f; //Not very effective against dragon
            typeCoverage[16] = 0.5f; //Not very effective against steel
        }
        else if(mType.equals("ICE")) {
            typeCoverage[1] = 0.5f; //Not very effective against fire
            typeCoverage[2] = 0.5f; //Not very effective against water
            typeCoverage[4] = 2.0f; //Super effective against grass
            typeCoverage[5] = 0.5f; //Not very effective against ice
            typeCoverage[8] = 2.0f; //Super effective against ground
            typeCoverage[9] = 2.0f; //Super effective against flying
            typeCoverage[14] = 2.0f; //Super effective against dragon
            typeCoverage[16] = 0.5f; //Not very effective against steel
        }
        else if(mType.equals("FIGHTING")) {
            typeCoverage[0] = 2.0f; //Super effective against normal
            typeCoverage[5] = 2.0f; //Super effective against ice
            typeCoverage[7] = 0.5f; //Not very effective against poison
            typeCoverage[9] = 0.5f; //Not very effective against flying
            typeCoverage[10] = 0.5f; //Not very effective against psychic
            typeCoverage[11] = 0.5f; //Not very effective against bug
            typeCoverage[12] = 2.0f; //Super effective against rock
            typeCoverage[13] = 0.0f; //No effect against ghost
            typeCoverage[15] = 2.0f; //Super effective against dark
            typeCoverage[16] = 2.0f; //Super effective against steel
            typeCoverage[17] = 0.5f; //Not very effective against fairy
        }
        else if(mType.equals("POISON")) {
            typeCoverage[4] = 2.0f; //Super effective against grass
            typeCoverage[7] = 0.5f; //Not very effective against poison
            typeCoverage[8] = 0.5f; //Not very effective against ground
            typeCoverage[12] = 0.5f; //Not very effective against rock
            typeCoverage[13] = 0.5f; //Not very effective against ghost
            typeCoverage[16] = 0.0f; //No effect against steel
            typeCoverage[17] = 2.0f; //Super effective against fairy
        }
        else if(mType.equals("GROUND")) {
            typeCoverage[1] = 2.0f; //Super effective against fire
            typeCoverage[3] = 2.0f; //Super effective against electric
            typeCoverage[4] = 0.5f; //Not very effective against ground
            typeCoverage[7] = 2.0f; //Super effective against poison
            typeCoverage[9] = 0.0f; //No effect against flying
            typeCoverage[11] = 0.5f; //Not very effective against bug
            typeCoverage[12] = 2.0f; //Super effective against rock
            typeCoverage[16] = 2.0f; //Super effective against steel
        }
        else if(mType.equals("FLYING")) {
            typeCoverage[3] = 0.5f; //Not very effective against electric
            typeCoverage[4] = 2.0f; //Super effective against grass
            typeCoverage[6] = 2.0f; //Super effective against fighting
            typeCoverage[11] = 2.0f; //Super effective against bug
            typeCoverage[12] = 0.5f; //Not very effective against rock
            typeCoverage[16] = 0.5f; //Not very effective against steel
        }
        else if(mType.equals("PSYCHIC")) {
            typeCoverage[6] = 2.0f; //Super effective against fighting
            typeCoverage[7] = 2.0f; //Super effective against poison
            typeCoverage[10] = 0.5f; //Not very effective against psychic
            typeCoverage[15] = 0.0f; //No effect against dark
            typeCoverage[16] = 0.5f; //Not very effective against steel
        }
        else if(mType.equals("BUG")) {
            typeCoverage[1] = 0.5f; //Not very effective against fire
            typeCoverage[4] = 2.0f; //Super effective against grass
            typeCoverage[6] = 0.5f; //Not very effective against fighting
            typeCoverage[7] = 0.5f; //Not very effective against poison
            typeCoverage[9] = 0.5f; //Not very effective against flying
            typeCoverage[10] = 2.0f; //Super effective against psychic
            typeCoverage[13] = 0.5f; //Not very effective against ghost
            typeCoverage[15] = 2.0f; //Super effective against dark
            typeCoverage[16] = 0.5f; //Not very effective against steel
            typeCoverage[17] = 0.5f; //Not very effective against fairy
        }
        else if(mType.equals("ROCK")) {
            typeCoverage[1] = 2.0f; //Super effective against fire
            typeCoverage[5] = 2.0f; //Super effective against ice
            typeCoverage[6] = 0.5f; //Not very effective against fighting
            typeCoverage[8] = 0.5f; //Not very effective against ground
            typeCoverage[9] = 2.0f; //Super effective against flying
            typeCoverage[11] = 2.0f; //Super effective against bug
            typeCoverage[16] = 0.5f; //Not very effective against steel
        }
        else if(mType.equals("GHOST")) {
            typeCoverage[0] = 0.0f; //No effect against normal
            typeCoverage[10] = 2.0f; //Super effective against psychic
            typeCoverage[13] = 2.0f; //Super effective against ghost
            typeCoverage[15] = 0.5f; //Not very effective against dark
        }
        else if(mType.equals("DRAGON")) {
            typeCoverage[14] = 2.0f; //Super effective against dragon
            typeCoverage[16] = 0.5f; //Not very effective against steel
            typeCoverage[17] = 0.0f; //No effect against fairy
        }
        else if(mType.equals("DARK")) {
            typeCoverage[6] = 0.5f; //Not very effective against fighting
            typeCoverage[10] = 2.0f; //Super effective against psychic
            typeCoverage[13] = 2.0f; //Super effective against ghost
            typeCoverage[15] = 0.5f; //Not very effective against dark
            typeCoverage[17] = 0.5f; //Not very effective against fairy
        }
        else if(mType.equals("STEEL")) {
            typeCoverage[1] = 0.5f; //Not very effective against fire
            typeCoverage[2] = 0.5f; //Not very effective against water
            typeCoverage[3] = 0.5f; //Not very effective against electric
            typeCoverage[5] = 2.0f; //Super effective against ice
            typeCoverage[12] = 2.0f; //Super effective against rock
            typeCoverage[16] = 0.5f; //Not very effective against steel
            typeCoverage[17] = 2.0f; //Super effective against steel
        }
        else if(mType.equals("FAIRY")) {
            typeCoverage[1] = 0.5f; //Not very effective against fire
            typeCoverage[6] = 2.0f; //Super effective against fighting
            typeCoverage[7] = 0.5f; //Not very effective against poison
            typeCoverage[14] = 2.0f; //Super effective against dragon
            typeCoverage[15] = 2.0f; //Super effective against dark
            typeCoverage[16] = 0.5f; //Not very effective against steel
        }

        return typeCoverage;
    }

    public void setCoverage(String[] mTypes) {
        Set<String> neutral_list = new HashSet<>();
        
        for(int i = 0; i < mTypes.length; i++) {

            float[] currType_val = calcCoverage(mTypes[i]);

            for(int j = 0; j < currType_val.length; j++) {

                if(currType_val[j] > 1.0f) {
                    if(this.t_noCoverage.contains(allTypes[j]))
                        this.t_noCoverage.remove(allTypes[j]);

                    this.t_coverage.add(allTypes[j]);
                }
                else if(currType_val[j] == 1.0f) {
                    neutral_list.add(allTypes[j]);
                }
                else if(currType_val[j] < 1.0f) {
                    if(!this.t_coverage.contains(allTypes[j]))
                        this.t_noCoverage.add(allTypes[j]);
                }

                if(neutral_list.contains(allTypes[j]) && this.t_noCoverage.contains(allTypes[j]))
                    this.t_noCoverage.remove(allTypes[j]);
            }
        }
    }

    public String[] removeDuplicates(String[] mTypes) {
        Arrays.sort(mTypes);

        int curr = 1;
        for(int i = 0; i < mTypes.length - 1; i++) {
            if(!mTypes[i].equals(mTypes[i + 1])) {
                mTypes[curr] = mTypes[i + 1];
                curr++;

            }
        }

        String[] reduced_mTypes_list = new String[curr];

        for(int i = 0; i < reduced_mTypes_list.length; i++) {
            reduced_mTypes_list[i] = mTypes[i];
        }

        return reduced_mTypes_list;
    }
}