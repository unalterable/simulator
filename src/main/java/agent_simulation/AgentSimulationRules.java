package agent_simulation;

import simulator_platform.ISimulatorRules;
import simulator_platform.records.IImporter;
import simulator_platform.records.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static agent_simulation.AgentMap.*;

public class AgentSimulationRules implements ISimulatorRules{

    IImporter importer;
    HashMap<Integer, List> breedChanges = new HashMap<Integer, List>();

    static final int MAX_YEARS = 15;
    private int years = 0;
    private double brandFactor;

    public AgentSimulationRules(double brandFactor, IImporter importer){
        this.importer = importer;
        this.brandFactor = brandFactor;
    }

    @Override
    public List<Record> getStartData() throws Exception {
        return importer.importRecords();
    }

    @Override
    public Boolean continueTicking() {
        return years < MAX_YEARS;
    }

    @Override
    public void tick(List records) throws IOException {
        years++;
        for(Object agent : records) tickAgent((Record) agent);
    }

    @Override
    public List<Integer> analyseResults(List data) throws IOException {
        int agentsInBreedC = 0, agentsInBreedNC = 0, breedCLost = 0, breedCGained = 0, breedCRegained = 0;
        for(Object obj : data){
            Record agent = (Record) obj;
            if(agent.getStr(BREED).equals(BREED_NC)) agentsInBreedNC++;
            if(agent.getStr(BREED).equals(BREED_C)) agentsInBreedC++;
        }
        for(List changes : breedChanges.values()){
            if(changes.get(changes.size()-1).equals(BREED_C)){
                if(changes.get(0).equals(BREED_NC)) breedCRegained++;
                else breedCGained++;
            } else if (changes.get(changes.size()-1).equals(BREED_NC)){
                breedCLost++;
            }
        }
        return new ArrayList<Integer>(Arrays.asList(agentsInBreedC, agentsInBreedNC, breedCLost, breedCGained, breedCRegained ));
    }

    private void tickAgent(Record agent) throws IOException {
        agent.setInt(AGE, agent.getInt(AGE) + 1);
        if (!agent.getBool(AUTO_RENEW) && shouldToggleBreed(agent)) toggleBreedAndLog(agent);
    }

    private boolean shouldToggleBreed(Record agent) {
        double affinity = (agent.getDouble(PAYMENT_AT_PURCHASE) / agent.getDouble(ATTRIBUTE_PRICE)) +
                (2 * agent.getDouble(ATTRIBUTE_PROMOTIONS) * agent.getInt(INERTIA_FOR_SWITCH));
        double nc_factor = (agent.getStr(BREED).equals(BREED_NC)) ? brandFactor : 1;
        return affinity < (agent.getInt(SOCIAL_GRADE) * agent.getDouble(ATTRIBUTE_BRAND) * nc_factor);
    }

    private void toggleBreedAndLog(Record agent) throws IOException {
        if(agent.getStr(BREED).equals(BREED_C)) {
            agent.setStr(BREED, BREED_NC);
        } else if (agent.getStr(BREED).equals(BREED_NC)) {
            agent.setStr(BREED, BREED_C);
        }
        logNewBreed(agent);
    }

    private void logNewBreed(Record agent) throws IOException {
        List<String> currentList;
        if(breedChanges.containsKey(agent.getID())) {
            currentList = breedChanges.get(agent.getID());
        } else {
            currentList = new ArrayList<String>();
        }
        currentList.add(agent.getStr(BREED));
        breedChanges.put(agent.getID(),currentList);
    }
}
