package io.infoworks.mpc;


import com.mongodb.MongoClient;

public class Application {

  public static void main(String[] args) throws Exception{
    MongoClient client = new MongoClient("localhost", 27017);
    JobCreator jobCreator = new JobCreator(client,"node1-");
    Thread thread = new Thread(jobCreator);
    thread.start();
    for (int i = 0; i < 4; i++) {
      JobReader jobReader = new JobReader(client,"Thread-"+i);
      Thread reader = new Thread(jobReader);
      reader.start();
    }
    thread.join();
  }
}
