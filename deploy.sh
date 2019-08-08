# BLOG_HOME=`~/deploy-practice`

# cd ~/deploy-practice
# echo cd~~~~

# git pull
git pull
echo git pull~~~~

# build
./gradlew clean build
echo clean build~~~~

# kill last server
# kill -9 $(lsof -t -i:8000)
for p in `sudo lsof -n -i:8000 | grep LISTEN | awk '{print $2}'`; do kill -9 $p; done
echo kill~~~~

# deploy new server
java -jar -Dserver.port=8000 ./build/libs/myblog-0.0.1-SNAPSHOT.jar &
echo deploy success~~~~
