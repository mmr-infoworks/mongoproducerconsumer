package io.infoworks.mpc;


import com.mongodb.MongoClient;

public class Application {

  public static void main(String[] args) throws Exception{
    MongoClient producerMongoClient = new MongoClient("localhost", 27017);
    JobCreator jobCreator = new JobCreator(producerMongoClient,"node1-");
    Thread thread = new Thread(jobCreator);
    thread.start();
    for (int i = 0; i < 30; i++) {
      MongoClient consumerMongoClient = new MongoClient("localhost", 27017);
      JobReader jobReader = new JobReader(consumerMongoClient,"Thread-"+i);
      Thread reader = new Thread(jobReader);
      reader.start();
    }
    thread.join();
  }
}
