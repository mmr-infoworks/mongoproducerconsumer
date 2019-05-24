package io.infoworks.mpc;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import static io.infoworks.mpc.Constants.*;

public class JobCreator implements Runnable {
  MongoClient mongoClient;
  int seqNumber =0;
  DB jobsDb;
  String name;
  long sleepTime = SLEEP_TIME;
  public JobCreator(MongoClient mongoClient,String name) {
    this.mongoClient = mongoClient;
    jobsDb = mongoClient.getDB(JOBS_DB);
    this.name = name;
  }
  public JobCreator(MongoClient mongoClient,String name,long sleepTime) {
    this.mongoClient = mongoClient;
    jobsDb = mongoClient.getDB(JOBS_DB);
    this.name = name;
    this.sleepTime = sleepTime;
  }


  public void run() {
    while (true) {
      DBObject dbObject = createnextDBObject();
      DBCollection queue = jobsDb.getCollection(QUEUE_COLLECTION);
      queue.insert(dbObject);
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private DBObject createnextDBObject() {
    BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
    docBuilder.append("timestamp", System.currentTimeMillis());
    docBuilder.append("seqNumber", name + seqNumber++);
    docBuilder.append("status","QUEUED");
    return docBuilder.get();
  }

  public static void main(String[] args) {
    MongoClient client = new MongoClient("localhost", 27017);
    long sleepTime = Long.parseLong(args[1]);
    Thread writer = new Thread(new JobCreator(client,args[0],sleepTime));
    writer.run();
  }
}
