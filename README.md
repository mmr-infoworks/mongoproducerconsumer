1.build the jar using mvn package -DskipTests
2.Create a mongo collection called queue in your local db 
3.Run test.sh for about 10 minutes
4.Kill jobs with pids matching ps -ef|grep mpc
5.Run cat node*.txt |awk '{print $2}' | sort |uniq -d 
