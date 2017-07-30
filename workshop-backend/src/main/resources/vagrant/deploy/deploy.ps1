echo export APP_NAME=workshop >> /etc/profile
echo export APP_VERSION=1.0-SNAPSHOT >> /etc/profile
echo export APP_ARTIFACTID=workshop-webapp >> /etc/profile
echo export JAVA_HOME=/u01/jdk  >> /etc/profile

. /etc/profile

export PATH=$JAVA_HOME/bin:$PATH

echo "+--------------------------------------------------+"
echo "| Kill Tomcat                                      |"
echo "+--------------------------------------------------+"

service tomcat stop

rm -rf ROOT_DIR/tomcat/webapps/*

echo "+--------------------------------------------------+"
echo "| Downloading WAR File                             |"
echo "+--------------------------------------------------+"
cd temp
if [[ $APP_VERSION == *"SNAPSHOT" ]]
then
	wget https://u343:P%40ssw0rd@artifactory.psdevelop.com/artifactory/PS-NightlyBuilds/com/progressoft/workshop/$APP_ARTIFACTID/$APP_VERSION/$APP_ARTIFACTID-$APP_VERSION.war 1>/dev/null 2>/dev/null
else
	wget https://u343:P%40ssw0rd@artifactory.psdevelop.com/artifactory/PS-Releases/com/progressoft/workshop/$APP_ARTIFACTID/$APP_VERSION/$APP_ARTIFACTID-$APP_VERSION.war 1>/dev/null 2>/dev/null
fi
cd ..

unzip -qo temp/*.war -d ROOT_DIR/tomcat/webapps/$APP_NAME
chmod -R 777 ROOT_DIR/tomcat/webapps/*

service tomcat stop
sleep 5
/u01/tomcat/bin/startup.sh & tail -f /u01/tomcat/logs/catalina.out
sleep 3
curl http://localhost:8080/$APP_NAME
echo "+--------------------------------------------------+"
echo "| $APP_NAME Started                                |"
echo "+--------------------------------------------------+"
