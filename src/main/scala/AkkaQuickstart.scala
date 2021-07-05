// 

// akka specific
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
// akka http specific
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
// spray specific (JSON marshalling)
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
// cors
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import java.io.FileNotFoundException
import java.io.IOException
import scala.collection.mutable.ArrayBuffer

import scala.collection.mutable.Map
//import com.lambdaworks.jacks._

//JDBC Driver Configuration
import java.sql.{Connection,DriverManager}

final case class Customer (
  id: Long,  
  name: String,
  location: String
  )

// def ExecuteQuert(query: String)  = {
//            val url = "jdbc:mysql://localhost:3306/chotaybikes"
//             val driver = "com.mysql.jdbc.Driver"
//             val username = "root"
//             val password = "admin"
//             var connection:Connection = _
//             try {
//                 Class.forName(driver)
//                 connection = DriverManager.getConnection(url, username, password)
//                 val statement = connection.createStatement
//                 val rs = statement.executeQuery("SELECT host, user FROM user")
//                 return rs
//             }
//             catch {
//                 case e: Exception => e.printStackTrace
//             }
//             connection.close
// }
object Main extends App{

  implicit val actorSystem = ActorSystem(Behaviors.empty, "akka-http")
  implicit val userMarshaller: spray.json.RootJsonFormat[Customer] = jsonFormat3(Customer.apply)
   val durl = "jdbc:mysql://localhost:3306/chotybhai_customers"
   val ddriver = "com.mysql.jdbc.Driver"
   val dusername = "root"
   val dpassword = "admin"

  val getCustomer = get {
      concat(
        path("getallcustomers") {
           val url = durl
            val driver = ddriver
            val username = dusername
            val password = dpassword
             try {
                 
           
                Class.forName(driver)
                var connection = DriverManager.getConnection(url, username, password)
                val statement = connection.createStatement
                val rs = statement.executeQuery("SELECT * FROM chotybhai_customers.customers;")
               
                while (rs.next) {
                  val name = rs.getString("name")
                  val location = rs.getString("location")
                  val id = rs.getString("id")
                  println("id = %s, name = %s, location = %s".format(id,name,location))
                  complete(StatusCodes.OK) //Returning status codes
                  //here we have to better parse the results in json inorder for it to be REST API
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, id))
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, name))
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, location))
                 
              }
               connection.close()
             complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hi, Upstart from Users are fetched but shown on console JSON conversion of Result Set not Set!"))

           
                 
            } catch {
                case e: Exception => {
                  e.printStackTrace
                  complete(StatusCodes.NotFound)
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hi,There was some error"))

                  
                }
            }
             
          },
        path("customer" / LongNumber) {
          customerid => {
             val url = durl
            val driver = ddriver
            val username = dusername
            val password = dpassword
             try {
                 
                println("hello")
                Class.forName(driver)
                var connection = DriverManager.getConnection(url, username, password)
                val statement = connection.createStatement
                
                var q = "SELECT * FROM chotybhai_customers.customers where id = '" +customerid.toString()+ "';"
                println(q)
                 val rs = statement.executeQuery(q)
                while (rs.next) {
                  val name = rs.getString("name")
                  val location = rs.getString("location")
                  val id = rs.getString("id")
                  println("id= %s,name = %s, location = %s".format(id,name,location))
                  complete(StatusCodes.OK)
                  //here we have to better parse the results in json inorder for it to be REST API
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, id))
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, name))
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, location))
              }
              connection.close()
              complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hi, Upstart some from Users are fetched but shown on console JSON conversion of ResultSet not Set!"))


              
                 
            } catch {
                case e: Exception =>{
                  e.printStackTrace
                  complete(StatusCodes.NotFound)
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hi,There was some error"))
                  
                } 
            }
          


           }
        }
      )
  }
  
  val createCustomer = post {
   // println("save user1")
    path("addcustomer") {
      
     println("save user")
      entity(as[Customer]) { 
        customer => {
          println("save user")

            val url = durl
            val driver = ddriver
            val username = dusername
            val password = dpassword
             try {
                 
           
                Class.forName(driver)
                var connection = DriverManager.getConnection(url, username, password)
                val statement = connection.createStatement
                val statement1 = connection.createStatement
                val createtable = statement.executeUpdate("CREATE TABLE IF NOT EXISTS `chotybhai_customers`.`customers` ( `id` BIGINT(20) NOT NULL AUTO_INCREMENT, `username` VARCHAR(200) NOT NULL,  `password` VARCHAR(300) NOT NULL, PRIMARY KEY (`id`) );");
                println("Table Created")
                val rs = statement1.executeUpdate("insert into chotybhai_customers.customers(name,location) values(\""+customer.name+"\",\""+customer.location+"\");")
                connection.close()
                complete(StatusCodes.OK)
                complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Customer Created"))
                
               
            } catch {
                case e: Exception => {
                  e.printStackTrace
                  complete(StatusCodes.NotFound)
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Customer Not Created"))
                  


                }
            }
            


          
        
       }
      }
     
  
      
    }
  }

  val updateCustomer = put {
    path("updatecustomer") {

      entity (as[Customer]) {
        customer => {
          println("update Customer")
             val url = durl
            val driver = ddriver
            val username = dusername
            val password = dpassword
             try {
                 
           
                Class.forName(driver)
                var connection = DriverManager.getConnection(url, username, password)
                val statement = connection.createStatement
                val rs = statement.executeUpdate("UPDATE chotybhai_customers.customers SET name=\""+customer.name+"\", location=\""+customer.location+"\" WHERE id='"+customer.id+"'; ")
               connection.close()
                complete(StatusCodes.OK) //Returning status codes
                
              complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Customer Updated"))
            } catch {
               
                case e: Exception => {
                   e.printStackTrace
                   complete(StatusCodes.NotFound)
                   complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Customer Not Updated"))
                 
                }
                
            }
            

            
          
        }
      }


         // complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hello world put!"))

    }
  }

  val deleteCustomer = delete {
    path ("deletecustomer" / LongNumber) {
      customer => {
       println("Deleting Customer")
          val url = durl
            val driver = ddriver
            val username = dusername
            val password = dpassword
             try {
                 
           
                Class.forName(driver)
                var connection = DriverManager.getConnection(url, username, password)
                val statement = connection.createStatement
                val rs = statement.executeUpdate("DELETE FROM chotybhai_customers.customers WHERE id='"+customer.toString()+"';")
               connection.close()
                complete(StatusCodes.OK) //Returning status codes
                
                  complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Customer Deleted"))
            } catch {
                case e: Exception => {
                   e.printStackTrace
                  complete(StatusCodes.NotFound)
                 
                }
            }
           


        
      }
    }
  }

  val route = cors() {
    concat(getCustomer, createCustomer, updateCustomer, deleteCustomer)
  }

  val bindFuture = Http().newServerAt("127.0.0.1", 8080).bind(route)
}
