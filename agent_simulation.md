# Agent Simulation

## Notes:

Both the initial data and the results can be found in "data/".

I was asked to implement a set of simulations with the following rules:

    Brand_Factor
    INPUT - Range (0.1 -> 2.9)

    Run for 15 Years
    Every Year:	Increment Age
    	If Auto Renew - Maintain Breed
        Else No Auto-Renew:	Affinity
            Affinity = 	Payment_at_Purchase/Attribute_Price + (2 * Attribute_Promotions * Inertia_for_Switch)
            If Breed_C	Switch to Breed_NC if Affinity < (Social_Grade * Attribute_Brand)
            If Breed_NC	Switch to Breed_C if Affinity < (Social_Grade * Attribute_Brand * Brand_Factor)

    Output:
        Agents in each Breed
    	Breed_C Lost (Switched to Breed_NC)
    	Breed_C Gained (Switch from Breed_NC)
    	Breed_C Regained (Switched to NC, then back to C)

    
*It was unclear whether an agent which was firstly Breed_C, then Breed_NC, then Breed_C again should count in all three catagories.*

*Therefore i assumed (if the life cycle of an agent was Breed_C=>Breed_NC=>Breed_C it should only count as Breed_C_Regained so as not to polute the other metrics. And that an agent Breed_C=>Breed_NC=>Breed_C=>Breed_NC=>Breed_C should still only count as 1 Breed_C_Regained.
If this remains unlcear please refer to tests ("src/test/java/agent_simulation/AgentSimulationRulesTest.java") for clarity.*

*This had the unusal effect of registering 0 Breed_C_Regained when the years was an odd number. Therefore I included results from a simulation set run for 16 years instead of 15.*



## Documentation for Agent Simulation Implementation:


#### AgentMap.java
Defines the schema of the data to be used.

Found in “src/main/java/agent_simulation/”
Tests found in “src/test/java/agent_simulation/” (100% methods covered)
implements IRecordImportMap

Constructor: None

Methods:
* void mapStringValuesToRecord(Record record, String key, String value);
    sets the appropriate Strings, Integers, Booleans, and Doubles on the record.



#### AgentSimulationRules.java
Defines the parameters and rules of an Agent Simulation

Found in “src/main/java/agent_simulation/”
Tests found in “src/test/java/agent_simulation/” (100% methods covered)
implements ISimulationRules

Constructor:
* AgentSimulationRules(double brandFactor, IImporter importer)
    To use this class you must instantiate it, passing the "Brand Factor" to be used in this simulation, and an IImporter pointed at the correct data set.

Methods:
* List<Record> getStartData();

    retrieves the starting data from the IImporter passed into the constructor
    
* Boolean continueTicking();

    will continue until it reaches MAX_YEARS (set to 15)
    
* void tick(List<Record> records);

    implements the following rules:
        Affinity = 	Payment_at_Purchase/Attribute_Price + (2 * Attribute_Promotions * Inertia_for_Switch)
        If Breed_C	Switch to Breed_NC if Affinity < (Social_Grade * Attribute_Brand)
        If Breed_NC	Switch to Breed_C if Affinity < (Social_Grade * Attribute_Brand * Brand_Factor)
        
* List analyseResults(List data);

    calculates the following:
        Agents in each Breed
        Breed_C Lost (Switched to Breed_NC)
        Breed_C Gained (Switch from Breed_NC)
        Breed_C Regained (Switched to NC, then back to C)


#### AgentSimulatorSetRules.java
Defines the parameters and rules of an Agent Simulation Set that varies "Brand Factor".

Found in “src/main/java/agent_simulation/”
Tests found in “src/test/java/agent_simulation/” (100% methods covered)
implements ISimulationSetRules

Constructor:
public AgentSimulationSetRules(IImporter importer)
    To use this class you must instantiate it, passing an IImporter implementation.

Methods:

`List getVariantFactorsForSet();`

gives a list of the different "Brand Factor"'s to be used.

`ISimulatorRules buildSimulationRules(Object factor);`
builds an AgentSimulationRules for the given factor.
    
`HashMap analyseAllResults(HashMap allResults)`
collates all the results across the simulation and outputs it to the file using DefaultLogger.