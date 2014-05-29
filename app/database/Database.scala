package database

import org.squeryl.{KeyedEntity, Schema}
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.annotations.Column

object MyDB extends Schema {
  val users = table[User]("users")
  on(users)(s => declare(
    s.id is autoIncremented("s_user_id")
  ))
}

case class User(name: String, @Column("age") age: Int) extends KeyedEntity[Long] {

  val id: Long = 0

}
