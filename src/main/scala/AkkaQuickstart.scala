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

  val getCustomer = get {
      concat(
        path("getallcustomers") {
           val url = "jdbc:mysql://localhost:3306/chotybhai_customers"
            val driver = "com.mysql.jdbc.Driver"
            val username = "root"
            val password = "admin"
             try {
                 
           
                Class.forName(driver)
                var connection = DriverManager.getConnection(url, username, password)
                val statement = connection.createStatement
                val rs = statement.executeQuery("SELECT * FROM chotybhai_customers.customers;")
                while (rs.next) {
                    val id = rs.getString("id")
                    val name = rs.getString("name")
                    val location = rs.getString("location")
                    println("name = %s, location = %s".format(name,location))
                    complete(Customer(id.toLong, name,location))
                }
                 connection.close
            } catch {
                case e: Exception => e.printStackTrace
            }
            finally{
                
            }
           


          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hi, Upstart from scala akka http server!"))
        },
        path("customer" / LongNumber) {
          customerid => {
            println("get cystomer by id")
              customerid match {
                case 1 => complete(Customer(customerid, "syedAdil","Islamabad"))
                case _ => complete(StatusCodes.NotFound)
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
           complete(Customer(customer.id, customer.name,customer.location))
    //     complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hello world from scala akka http server!"))
       }
      }
     
  
      
    }
  }

  val updateCustomer = put {
    path("updatecustomer") {

      entity (as[Customer]) {
        customer => {
          println("update Customer")
          complete(Customer(customer.id, customer.name,customer.location))
        }
      }


         // complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hello world put!"))

    }
  }

  val deleteCustomer = delete {
    path ("deletecustomer" / LongNumber) {
      customer => {
        println(s"Customer ${customer}")
        complete(Customer(customer, "syed","Islamabad"))
      }
    }
  }

  val route = cors() {
    concat(getCustomer, createCustomer, updateCustomer, deleteCustomer)
  }

  val bindFuture = Http().newServerAt("127.0.0.1", 8080).bind(route)
}
