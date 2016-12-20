package agent_simulation;

import simulator_platform.IRecordImportMap;
import simulator_platform.records.Record;

public class AgentMap implements IRecordImportMap {

    public static final String BREED = "Agent_Breed";
    public static final String POLICY_ID = "Policy_ID";
    public static final String AGE = "Age";
    public static final String SOCIAL_GRADE = "Social_Grade";
    public static final String PAYMENT_AT_PURCHASE = "Payment_at_Purchase";
    public static final String ATTRIBUTE_BRAND = "Attribute_Brand";
    public static final String ATTRIBUTE_PRICE = "Attribute_Price";
    public static final String ATTRIBUTE_PROMOTIONS = "Attribute_Promotions";
    public static final String AUTO_RENEW = "Auto_Renew";
    public static final String INERTIA_FOR_SWITCH = "Inertia_for_Switch";
    public static final String BREED_C = "Breed_C";
    public static final String BREED_NC = "Breed_NC";

    @Override
    public void mapStringValuesToRecord(Record record, String key, String value) {

        if (key.equals(BREED)){
            record.setStr(key, value);

        } else if (key.equals(POLICY_ID) || key.equals(PAYMENT_AT_PURCHASE) || key.equals(ATTRIBUTE_BRAND) || key.equals(ATTRIBUTE_PRICE) || key.equals(ATTRIBUTE_PROMOTIONS)){
            record.setDoubleFromStr(key, value);

        } else if (key.equals(AGE) || key.equals(SOCIAL_GRADE) || key.equals(INERTIA_FOR_SWITCH)){
            record.setIntFromStr(key,  value);

        } else if (key.equals(AUTO_RENEW)){
            record.setBoolFromStr(key, value);
        }
    }
}
