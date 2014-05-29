package controllers

import play.api.mvc._
import org.squeryl.PrimitiveTypeMode._
import database.{MyDB, User}
import scala.util.Random

object Application extends Controller {

  def index = Action {
    Ok(
      """
        |<li> <a href="/users" target="_blank">See all users</a>
        |<li> <a href="/newuser" target="_blank">click to create a random user</a>
      """.stripMargin).as("text/html")
  }

  def users = Action {
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

}
