package io.infoworks.mpc;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import static io.infoworks.mpc.Constants.*;

public class JobReader implements Runnable {
  MongoClient mongoClient;
  DB jobsDb;
  String name;
  public JobReader(MongoClient mongoClient,String name) {
    this.mongoClient = mongoClient;
    jobsDb = mongoClient.getDB(JOBS_DB);
    this.name = name;
  }

  public void run() {
    while (true) {
      DBCollection queue =  jobsDb.getCollection(QUEUE_COLLECTION);
      DBObject query = new BasicDBObject();
      ((BasicDBObject) query).put("status","QUEUED");
      DBObject updChild = new BasicDBObject();
      ((BasicDBObject) updChild).put("status","RUNNING");
      ((BasicDBObject) updChild).put("runner",name);
      DBObject upd = new BasicDBObject();
      ((BasicDBObject) upd).put("$set",updChild);

      DBObject jobToProcess = queue.findAndModify(query,upd);
      if (jobToProcess!=null) {
        Object seqNumber = jobToProcess.get("seqNumber");
        System.out.println(String.format("SeqNumber %s ProcessedBy: %s ",seqNumber,name));
      }


      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }


    }
  }

  public static void main(String[] args) {
    MongoClient client = new MongoClient("localhost", 27017);
    Thread reader = new Thread(new JobReader(client,args[0]));
    reader.run();
  }

}
