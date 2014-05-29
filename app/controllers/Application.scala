package controllers

import play.api.mvc._
import org.squeryl.PrimitiveTypeMode._
import database.{MyDB, User}
import scala.util.Random
import play.api.db.DB
import play.api.Play
import com.jolbox.bonecp.BoneCPDataSource

object Application extends Controller {

  def index = Action {
    Ok(
      """
        |<li> <a href="/users" target="_blank">See all users</a>
        |<li> <a href="/newuser" target="_blank">click to create a random user</a>
      """.stripMargin).as("text/html")
  }

  def users = Action {
    seePoolInfo

    val users = inTransaction {
      MyDB.users.where(u => true === true).toList
    }
    Ok(users.toString())
  }

  def createUser = Action {
    val name = "Freewind@" + System.currentTimeMillis()
    inTransaction {
      val user = new User(name, Random.nextInt())
      MyDB.users.insert(user)
    }
    Ok("created user: " + name)
  }

  def seePoolInfo {
    val dataSource = DB.getDataSource()(Play.current).asInstanceOf[BoneCPDataSource]
    val pool = dataSource.getPool
    val statistics = pool.getStatistics
    println("=====================================")
    println("Total leased: " + dataSource.getTotalLeased)
    println("getTotalCreatedConnections: " + statistics.getTotalCreatedConnections)
    println("getConnectionsRequested: " + statistics.getConnectionsRequested)
    println("getTotalFree: " + statistics.getTotalFree)
    println("=====================================")
  }

}
