package net.study.functional.lesson10_OOP_classes



object OOPBasics extends App {

  //val person = Person("John", 18)

 // println(person)


  val personObjectSingle: Person.type = Person


  def performToRun(runnable : CanRun) = println(runnable.run)

  def performToEat(canEat : CanEat) = println(canEat.eat)

/*  performToRun(new Mammal)
  performToRun(new Bug)*/

  val someAnimal =  new CanRun with CanEat {
    override def speed: Int = 70
    override def food: String = "potato"
  }

  performToRun(someAnimal)
  performToEat(someAnimal)


  val sortedIntList = List(3,1,5,7).sorted

  //println(sortedIntList)



  val people = List(Person("John", 18), Person("John", 17), Person("Adam", 19))

  def sortObjects[T](list: List[T])(implicit ordering: Ordering[T]): Seq[T] = list.sorted

  println()

  {
    import net.study.functional.lesson10_OOP_classes.Orderings.peopleOrdering
    println(sortObjects(people))
  }

}

object Orderings {
  implicit val peopleOrdering: Ordering[Person] = (l, r) => l.age.compareTo(r.age)
}

class Person private(var name: String, val age: Int, var isMarriage: Boolean) {


  private def this(name: String, age: Int) {
    this(name, age, {
      import net.study.functional.lesson10_OOP_classes.Person.isMarriageDefined
      isMarriageDefined(age)
    })
  }

  override def toString: String = s"Person: $name, $age"
}

object Person {
  private def isMarriageDefined(age : Int) = age > 18
  def apply(name: String, age: Int): Person = new Person(name, age, true)
  def apply(name: String, age: Int, isMarriage: Boolean): Person = new Person(name, age, isMarriage)

}


