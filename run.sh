FILE=./sqlite-jdbc-3.27.2.1.jar
if [ -f "$FILE" ]; then
    echo "$FILE exists. Compile Webserver SQLite program."  
    echo "$STR"
else 
    echo "$FILE does not exist. Downloading jar from maven archive..."
    echo "$STR"
    wget https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.27.2.1/sqlite-jdbc-3.27.2.1.jar
    
fi
kotlinc sqws.kt; kotlin -cp ".:./sqlite-jdbc-3.27.2.1.jar" SqwsKt
