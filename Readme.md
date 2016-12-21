# Simulator Platform:

This is a simple abstract simulator module, created to be a good platform on which to run custom simulations. Documentation for the Agent implementation found in [agent_simulation.md](agent_simulation.md).

* Language: basic Java 6.
* TDD: 100% test coverage on all units: JUnit, Mockito
* SOLID design
* Built as a Platform to easily allow for other simulation implementations.

### It works as follows:

##### 1) Map your data with `IImportRecordMap.java`

To import your data you can write an implementation of IImportRecordMap and pass it into the CSV importer. 
```
    IImporter importer = new CSVImporter("data/my_data.csv", new MyRecordMap());
```
##### 2) Write your simulation rules with `ISimulatorRules`:

To define a specific simulation you must write an implementation of ISimulatorRules
```
    ISimulatorRules simulationRules = new MySimulationRules();
    Simulator simulator = new Simulator(simulationRules);
```
You can then run your simulation with `simulator.run()`;

##### 3) Write rules for a simulation set with `ISimulatorSetRules`:

To define a specific simulation you must write an implementation of ISimulatorSetRules. This allows you to conduct a range of the same simulation with varying starting factors.
```
    ISimulatorSetRules setRules = new MySimulationSetRules();
    SimulatorSet simulatorSet = new SimulatorSet(setRules);
```
Your simulationSet will then build all of your simulations according to the rules specified.
You can then run your simulation set with `simulatorSet.runAll()`.



# Documentation for platform Interfaces:

## IRecordImportMap.java
Found in “src/main/java/simulator_platform/records/”

##### Interface:
* `void mapStringValuesToRecord(Record record, String key, String value);`

    This method must set the Key Value pair on the record using methods provided by Record. This must be implemented in order to map your data to the Record used by the Simulator.


## ISimulatorRules.java
Found in “src/main/java/simulator_platform/”

##### Interface:
* `List<Record> getStartData();`

    specifies the starting data.

* `Boolean continueTicking();`

    specifies the circumstances under which the simulation continues.

* `void tick(List<Record> records);`

    specifies the behaviour of the simulation each iteration

* `List analyseResults(List data);`

    specifies any analysis you want to return from the simulation



## ISimulatorSetRules.java
Found in “src/main/java/simulator_platform/”

##### Interface:
* `List getVariantFactorsForSet();`

    specifies the different starting parameters for each simulation. Must be a list of objects (any objects can be used to set the starting parameters of each simulation in the set).

* `ISimulatorRules buildSimulationRules(Object factor);`

    specifies how to build an ISimulatorRules for each simulation given a different starting factor - which can be any object.

* `HashMap analyseAllResults(HashMap allResults);`

    specifies any analysis you want to return from the simulation's own analyseResults()



Documentation for platform classes:

## Simulator.java
A class that runs a simple simulation, with rules defined by the  IsimulatorRules injected into its constructor

* Found in “src/main/java/simulator_platform/simulator/”
* Tests found in “src/test/java/simulator_platform/simulator/” (100% methods covered)
* implements ISimulator interface

##### Constructor:
* `Simulator(ISimulatorRules rules)`

	To use this class you must instantiate it, passing an object that implements ISimulatorRules.

##### Methods:
* `void run();`

	This method runs the simulation

* `List getAnalysedResults();`

	This method returns a list of analysed results from the simulation. The analysis is implemented by the  ISimulatorRules.

* `List getCurrentData();`

	This method returns a list of the current data being used for the simulation.


## SimulatorSet.java
A class that runs a  set of simple simulations, with rules defined by the  ISimulatorSetRules injected into its constructor. It can be used to explore the effect on a simulation given various starting factors. Those factors are defined by the IsimulatorSetRules.getVariantFactors(), in the ruleset you mist inject into its constructor.

* Found in “src/main/java/simulator_platform/simulator/”
* Tests found in “src/test/java/simulator_platform/simulator/” (100% methods covered)
* implements ISimulatorSet interface

##### Constructor:
* `Simulator( ISimulatorSetRules setRules)`
	To use this class you must instantiate it, passing an object that implements ISimulatorSetRules.

##### Methods:
* `void runAll();`

	This method runs all the simulations.

* `HashMap analyseAllResults;`

	This method returns a list of analysed results from all the simulations.
	The HashMap is of the form {UniqueStartingFactor => simulation_analysed_results}

* `HashMap getAllEndData;`

	This method returns a list of the end data for from all the simulations.
	The HashMap is of the form {UniqueStartingFactor => simulation_data}.


## Record.java
A simple key/value store for Strings, Integers, Doubles, and Booleans. It can also convert Strings into the other types it uses.

* Found in “src/main/java/simulator_platform/records/”
* Tests found in “src/test/java/simulator_platform/records/” (100% methods covered)

##### Constructor:
* `Record(int id)`

	To use this class you must instantiate it, passing a unique ID as an int.

##### Methods:
* `int getID()`

    returns the ID.

* `void setStr(String key, String new_value)`

    sets a string inside the record, and stores it against the given key.

* `void setBool(String key, Boolean new_value)`

    sets a Boolean inside the record, and stores it against the given key.

* `void setInt(String key, Integer new_value)`

    sets an Integer inside the record, and stores it against the given key.

* `void setDouble(String key, Double new_value)`

    sets a string inside the record, and stores it against the given key.

* `void setBoolFromStr(String key, String new_value)`

    sets a Boolean inside the record – from a string value. (N.B. “1” and “true” become true. All other strings are false.

* `void setIntFromStr(String key, String new_value)`

    sets an Integer inside the record – from a string value.

* `void setDoubleFromStr(String key, String new_value)`

    sets a Double inside the record – from a string value.

* `String getStr(String key)`

	retrieves an String stored in the record.

* `Boolean getBool(String key)`

	retrieves an Boolean stored in the record.

* `int getInt(String key)`

	retrieves an Integer stored in the record.

* `double getDouble(String key)`

	retrieves an Double stored in the record.



## CSVImporter.java
A CSV importer class that maps imported data to Records according to the IRecordImportMap that you must inject into its constructor

* Found in “src/main/java/simulator_platform/records/”
* Tests found in “src/test/java/simulator_platform/records/” (100% methods covered)
* implements IImporter interface

##### Constructor:
* `CSVImporter(String fullPath, IRecordImportMap map)`

	To use this class you must instantiate it, passing it the path to the CSV file (e.g. “data/agent_data.csv)

##### Methods:
* `List<Record> importRecords()`

	returns a List<Records>, imported and mapped.



## DefaultLogger.java
A basic logging service which writes to a file. Useful for outputting results in implementations of IsimulatorRules and IsimulatorSetRules.

* Found in “src/main/java/simulator_platform/records/”
* Tests found in “src/test/java/simulator_platform/records/” (100% methods covered)
* implements IImporter interface

##### Constructor:
* `DefaultLogger(String directory, String fileName)`

	To use this class you must instantiate it, passing it the director you would like your log, and the fileName for your log.

##### Methods:
* `void log(String key, String log)`

	this will append a simple log to the log file. The log is stored in two parts, for convenience in simulation logging.

* `String getDir();`

	this will return the directory of the log file. (e.g. “path/to/”)

* `String getFullPath();`

	this will return the full path of the log file.( e.g. “path/to/file.ext”)

* `List retrieveLogs();`

	this will retrieve all logs from the log file as a list of strings.
