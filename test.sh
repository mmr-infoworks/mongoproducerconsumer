#!/bin/bash
java -cp target/mpc-1.0-SNAPSHOT-fat.jar io.infoworks.mpc.JobReader node1 1000 > node1.txt & 
java -cp target/mpc-1.0-SNAPSHOT-fat.jar io.infoworks.mpc.JobReader node2 1000 > node2.txt & 
java -cp target/mpc-1.0-SNAPSHOT-fat.jar io.infoworks.mpc.JobReader node3 1000 > node3.txt & 
java -cp target/mpc-1.0-SNAPSHOT-fat.jar io.infoworks.mpc.JobReader node4 1000 > node4.txt & 
java -cp target/mpc-1.0-SNAPSHOT-fat.jar io.infoworks.mpc.JobCreator creatornode1- 40000 > creatornode1.txt & 
java -cp target/mpc-1.0-SNAPSHOT-fat.jar io.infoworks.mpc.JobCreator creatornode2- 40000 > creatornode2.txt & 
