package net.study.functional.lesson13_generics

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{ListEmptyError, ValidationError}
import net.study.functional.lesson10_OOP_classes.hometask.oop.validator.ValidatorUtil.validateOnlyPresent

import scala.reflect.ClassTag

object ScalaGenerics extends App {


  // Scala arrays are invariant
  val array: Array[Cat] = Array[Cat]()
  // var animalArray: Array[Animal] = array // not compile

  // Immutable Seq are covariant
  val immutableSeq: Seq[Cat] = Seq(new Cat("Felix"))

  val immutableDogSeq: Seq[Dog] = Seq(new Dog("Felix"))

  // covariant operation of link assigning
  var animalSeq: Seq[Animal] = immutableSeq

  println(animalSeq.isInstanceOf[Seq[_]])

  // mutation with result of new more wide Seq[Animal]
  val newList: Seq[Animal] = animalSeq :+ new Dog("Lord")

  println(newList.isInstanceOf[Seq[_]])

  // pattern matching
  immutableDogSeq match {
    case someList: List[_] => println("These are cats")
    case _                 => println("other object")
  }

  ////// Simple covariant Container

  /*
    val containerCat: AnimalBox[Cat] = new AnimalBox[Cat](new Cat("Murchik"))

    var containerAnimal: AnimalBox[Cat] = containerCat

    val containerAnimal2: AnimalBox[Animal] = containerCat.update(new Dog("Sharik"))

    val containerAnimal3: AnimalBox[Animal] = containerCat.update(new Dog("Sharik"))

    println(containerAnimal.animal.name)

    println(containerAnimal2.animal.name)
  */

  val myCatList: MyList[Cat] = SomeList(new Cat(""), MyNil)

  val myAnimalList: MyList[Animal] = myCatList

  val myAnimalList2: MyList[Animal] = myAnimalList + new Dog("Dog")

  val listStr: List[String] = Nil.::("1")

  val newList3: Seq[Object] = listStr.::(new Cat(""))


  val treatCats: Cat => Diagnosis = (toTreat: Cat) => new Diagnosis {}

  val treatDogs: Dog => Diagnosis = (toTreat: Dog) => new Diagnosis {}

  val treatAnimals: Animal => Diagnosis = (toTreat: Animal) => new Diagnosis {}

  val treatDogs2: Dog => Diagnosis = treatAnimals

  treatAnimals(new Dog(""))

  treatAnimals(new Cat(""))

  def treatDog(treatment: Dog => Diagnosis, animal: Dog) = treatment(animal)

  treatDog(treatAnimals, new Dog(""))

  trait Diagnosis

  class AdvancedDiagnosis extends Diagnosis

  val advancedAnimalTreatment: Animal => AdvancedDiagnosis = (toTreat: Animal) => new AdvancedDiagnosis

  val treatUsualAnimals: Animal => Diagnosis = advancedAnimalTreatment

  val treatUsualCats: Cat => Diagnosis = advancedAnimalTreatment


  ///////////////Wildcard///////////////////////

  val unknownList: List[_] = List[Cat]()

  val unknownList2: List[_] = List[Dog]()

  val catList = List[Cat]()


  def validateTraversableEmpty[T <: Traversable[_]](paramName: String, paramValue: Option[T]): Either[ValidationError, Unit] =
    validateOnlyPresent(paramName, paramValue, ListEmptyError)(toCheck => toCheck.nonEmpty)

  //val validationResult = validateTraversableEmpty("list", Option(Option(new Dog(""))))

  // println(validationResult)


  //////////////////ClassTag/////////////////////////////

  val anyMap = Map[Int, Any](1 -> "1", 2 -> 2, 3 -> true)

  def getTypedFromMap[T: ClassTag](map: Map[Int, Any], key: Int): Option[T] =
    map.get(key) match {
      case Some(value: T) => Some(value)
      case _              => None
    }

  println(getTypedFromMap[Boolean](anyMap, 3))

  def compare[T<: Ordered[T] ](left: T, right: T) =  left > right

  def sortAnyCollections[T: Ordering] (seq: Seq[T]) = seq.sorted
   //def sortAnyCollections[T] (seq: Seq[T])(implicit ordering: Ordering[T]) = seq.sorted

   implicit val catOrdering: Ordering[Cat] = Ordering.by(_.name)


  sortAnyCollections(catList)
}

/*trait ImmutableBox[+T] {

  def update[IN >: T](in: IN): ImmutableBox[IN]
}

class AnimalBox[+T](val animal: T) extends ImmutableBox[T] {

  override def update[IN >: T](in: IN): AnimalBox[IN] = new AnimalBox[IN](in)
}*/


trait MyList[+T] {

  def +[IN >: T](in: IN): MyList[IN] = SomeList(in, this)
}

case class SomeList[+T](head: T, tail: MyList[T]) extends MyList[T]

object MyNil extends MyList[Nothing]


///////////////////////////////////////








