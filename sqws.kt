import com.sun.net.httpserver.HttpServer
import java.io.PrintWriter
import java.net.InetSocketAddress

import java.sql.* // Connection, DriverManager, SQLException
import java.util.Properties

/**
    https://www.tutorialkart.com/kotlin/connect-to-mysql-database-from-kotlin-using-jdbc/

    $ wget https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.27.2.1/sqlite-jdbc-3.27.2.1.jar
    $ kotlinc sqws.kt; kotlin -cp ".:./sqlite-jdbc-3.27.2.1.jar" SqwsKt

    Minimal embedded HTTP server in Kotlin using Java built in HttpServer
**/

fun main(args: Array<String>) {
	// val conn = DriverManager.getConnection( "jdbc:sqlite:/home/u/dev/Q-Project/Mobile/Kotlin/Simple Webserver SQlite/sampledb.db")
    val conn = DriverManager.getConnection( "jdbc:sqlite:./sampledb.db")
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    try {
        //stmt = conn!!.createStatement()
        stmt = conn.createStatement()
        resultset = stmt!!.executeQuery("SELECT * FROM items;")
        if (stmt.execute("SELECT * FROM items;")) {
            resultset = stmt.resultSet
        }
        // while (resultset!!.next()) {
        //     println(resultset.getString("name"))
        // }
    } catch (ex: SQLException) {
        // handle any errors
        ex.printStackTrace()
    }

    HttpServer.create(InetSocketAddress(8080), 0).apply {
        println("browse http://localhost:8080/hello")
        createContext("/hello") { http ->
            http.responseHeaders.add("Content-type", "text/plain")
            http.sendResponseHeaders(200, 0);
            PrintWriter(http.responseBody).use { out ->
                out.println( "ok")
            	while (resultset!!.next()) {
            	    out.println(resultset.getString("name"))
            	}
                //out.println("Hello ${http.remoteAddress.hostName}!" + "Umar" )
            }
        }
        start()
	}
}