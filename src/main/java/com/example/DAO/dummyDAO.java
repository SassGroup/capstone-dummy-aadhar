package com.example.DAO;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dummyDAO {
    public String checkAerospike(String aadharno) {
       System.out.println(aadharno);
       AerospikeClient client = new AerospikeClient("aerospike-service", 3000); //okteto
    //    AerospikeClient client = new AerospikeClient("localhost", 3000);
       List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
       RecordSet r1 = null;
       Statement st = new Statement();
       QueryPolicy qp = new QueryPolicy();

       try {
           st.setNamespace("test");
           st.setSetName("aadharTable");
           //client = AerospikeClientConfig.getInstance().getAerospikeClientMetaData();

           qp.filterExp = Exp.build(
                   Exp.eq(Exp.stringBin("aadharNo"), Exp.val(aadharno))
           );

           r1 = client.query(qp, st);
           while (r1.next()) {
               //Key key1 = r1.getKey();
               Record record = r1.getRecord();
               System.out.println(record);
               //log.info("SwimlaneDaoSwimelaneResponseFetch Record from Swimelane3 Table - {}",record);
               // Map<String,String> temp = new HashMap<>();
               //  for(String val: SwimlaneConstants.SWIMLANE_PAYLOAD_RESPONSE_MAP_AEROSPIKE_BIN.keySet()){
               //    if(record.bins.get(val)!=null){
               //      temp.put(SwimlaneConstants.SWIMLANE_PAYLOAD_RESPONSE_MAP_AEROSPIKE_BIN.get(val),String.valueOf(record.bins.get(val)));
               // }
               //}
               return "present";
           }
           return "not present";
       }catch (Exception e) {
           String y = e.toString();
           return y;
       }
        // return "present";
    }
    public Map<String, Object> fetchAerospike(String aadharno){
    //    AerospikeClient client = new AerospikeClient("localhost", 3000);
       AerospikeClient client = new AerospikeClient("aerospike-service", 3000); //okteto
       List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
       RecordSet r1 = null;
       Statement st = new Statement();
       QueryPolicy qp = new QueryPolicy();
       String[] bins = new String[]{"aadharNo", "emailID", "address"};
       Map<String, Object> Result = null;

       try {
           st.setNamespace("test");
           st.setSetName("aadharTable");
           st.setBinNames(bins);
           //client = AerospikeClientConfig.getInstance().getAerospikeClientMetaData();

           qp.filterExp = Exp.build(
                   Exp.eq(Exp.stringBin("aadharNo"), Exp.val(aadharno))
           );

           r1 = client.query(qp, st);
           while (r1.next()) {
               //Key key1 = r1.getKey();
               Record record = r1.getRecord();
               // String response = record.toString();
               Result = record.bins;
               //log.info("SwimlaneDaoSwimelaneResponseFetch Record from Swimelane3 Table - {}",record);
               // Map<String,String> temp = new HashMap<>();
               //  for(String val: SwimlaneConstants.SWIMLANE_PAYLOAD_RESPONSE_MAP_AEROSPIKE_BIN.keySet()){
               //    if(record.bins.get(val)!=null){
               //      temp.put(SwimlaneConstants.SWIMLANE_PAYLOAD_RESPONSE_MAP_AEROSPIKE_BIN.get(val),String.valueOf(record.bins.get(val)));
               // }
               //}
               return Result;
           }
           return null;
       }catch (Exception e){
           String y = e.toString();
           return null;}

        // Map<String, Object> rajData = new HashMap<>() {{
        //     put("aadharNo", 22222222);
        //     put("emailID", "abheekbabel@gmailcom");
        //     put("address", "Bangalore");
        // }};

        // return rajData;
    }
}
