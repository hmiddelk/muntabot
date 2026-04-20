package shared

import util.Random.nextInt as rnd

case class Week(w: Int)

object Week:
  private val titles: Map[Int, String] =
    Map(
      1 -> "Introduction",
      2 -> "Program, control-structures",
      3 -> "Function, abstraction",
      4 -> "Object, encapsulation",
      5 -> "Classes, datamodelling",
      6 -> "Patterns, error handling",
      7 -> "Sequences, enumeration",
      8 -> "Arrays, type parameters",
      9 -> "Sets, maps",
      10 -> "Inheritance, composition"
    )
  def title(week: Week): String = titles.getOrElse(week.w, "")

enum LangFilter:
  case Scala, Java

enum QuestionLang:
  case Both, ScalaOnly, JavaOnly

var langFilter: LangFilter = LangFilter.Scala

def visibleIn(ql: QuestionLang, lf: LangFilter): Boolean =
  lf match
    case LangFilter.Scala => ql != QuestionLang.JavaOnly
    case LangFilter.Java  => ql != QuestionLang.ScalaOnly

case class Comparison(week: Week, scalaName: String, javaName: String)

val countOf = collection.mutable.Map.empty[Any, Int].withDefaultValue(0)
def reg(a: Any): Unit = countOf(a) += 1

abstract class Question(cs: Vector[String | (String, String)]):
  def foreach = cs.foreach
  val lang: QuestionLang
  val title: String
  val instruction: String
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String)

abstract class Questions:
  val title: String
  val questionToAsk: String
  val instruction: String
  lazy val all: Seq[String] | Seq[(String, String)]

  def punctuation: Char = '?'

  def pickAnyQuestion(
      toWeek: Int,
      tpe: Questions,
      lf: LangFilter = LangFilter.Scala
  ): String | (String, String) =
    val termsToWeekOfType: Seq[String | (String, String)] =
      val resultNested = for
        (Week(w), q) <- terms
        if w <= toWeek && q.title == tpe.title && visibleIn(q.lang, lf)
      yield
        val items: Seq[String | (String, String)] = q match
          case xs: Concepts  => xs.cs
          case xs: Contrasts => xs.cs
          case xs: Code      => xs.cs
        items
      resultNested.flatten
    if termsToWeekOfType.isEmpty then
      s"SORRY: Muntabot has no such question for this week $toWeek"
    else
      val counts: Seq[Int] = termsToWeekOfType.map(countOf)
      val minCount: Int = counts.minOption.getOrElse(0)
      val leastUsed =
        for i <- termsToWeekOfType.indices if counts(i) == minCount
        yield termsToWeekOfType(i)
      val result = leastUsed(rnd(leastUsed.length))
      reg(result)
      result

  def getQuestion(question: String | (String, String)): String =
    s"$title: $questionToAsk\n$question$punctuation\n\n$instruction"

  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    s"$questionToAsk ${question.toString.toUpperCase}$punctuation"

object CompareScalaJava extends Questions:
  val title = "Compare Scala ↔ Java"
  val questionToAsk = Contrasts.questionToAsk
  val instruction = Contrasts.instruction

  lazy val all: Seq[(String, String)] = comparisons.map(c => (c.scalaName, c.javaName))

  override def pickAnyQuestion(
      toWeek: Int,
      tpe: Questions,
      lf: LangFilter = LangFilter.Scala
  ): String | (String, String) =
    val available = comparisons.filter(_.week.w <= toWeek).map(c => (c.scalaName, c.javaName))
    if available.isEmpty then
      s"SORRY: Muntabot has no comparison questions for week $toWeek"
    else
      val counts = available.map(countOf)
      val minCount = counts.minOption.getOrElse(0)
      val leastUsed = for i <- available.indices if counts(i) == minCount yield available(i)
      val result = leastUsed(rnd(leastUsed.length))
      reg(result)
      result

  override def getQuestion(question: String | (String, String)): String =
    question match
      case (s: String, j: String) =>
        s"$title:\n$questionToAsk Scala '$s' and Java '$j'$punctuation\n\n$instruction"
      case _ => "unexpected"

  override def getShortQuestion(question: String | (String, String)): String | (String, String) =
    question match
      case (s: String, j: String) => s"$questionToAsk Scala '$s' and Java '$j'$punctuation"
      case _ => "unexpected"

object Questions:
  val types: Vector[Questions] = Vector(Concepts, Contrasts, Code, CompareScalaJava)

case class Concepts(lang: QuestionLang, cs: Seq[String]) extends Question(cs.toVector):
  val title = Concepts.title
  val instruction = Concepts.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Concepts.getShortQuestion(question)
object Concepts extends Questions:
  val title = "Explain concepts"
  val questionToAsk = "What is meant by"
  val instruction =
    "Give examples of normal and incorrect/strange usage. Explain why/when the concept is good to have."

  def apply(cs: String*): Concepts     = new Concepts(QuestionLang.Both, cs.toSeq)
  def forScala(cs: String*): Concepts  = new Concepts(QuestionLang.ScalaOnly, cs.toSeq)
  def forJava(cs: String*): Concepts   = new Concepts(QuestionLang.JavaOnly, cs.toSeq)

  lazy val all = (for case (w, t: Concepts) <- terms yield t).map(_.cs).flatten

case class Contrasts(lang: QuestionLang, cs: Seq[(String, String)]) extends Question(cs.toVector):
  val title = Contrasts.title
  val instruction = Contrasts.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Contrasts.getShortQuestion(question)
object Contrasts extends Questions:
  val title = "Compare concepts"
  val questionToAsk = "What are the differences and similarities between"
  val instruction =
    "Give examples of normal or incorrect/strange usage that highlight differences/similarities. Explain why the concepts exist and what they are supposed to be good for."

  def apply(cs: (String, String)*): Contrasts    = new Contrasts(QuestionLang.Both, cs.toSeq)
  def forScala(cs: (String, String)*): Contrasts = new Contrasts(QuestionLang.ScalaOnly, cs.toSeq)
  def forJava(cs: (String, String)*): Contrasts  = new Contrasts(QuestionLang.JavaOnly, cs.toSeq)

  lazy val all = (for case (w, t: Contrasts) <- terms yield t).map(_.cs).flatten

case class Code(lang: QuestionLang, cs: Seq[(String, String)]) extends Question(cs.toVector):
  val title = Code.title
  val instruction = Code.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Code.getShortQuestion(question)
object Code extends Questions:
  val title = "Write code"
  val questionToAsk: String =
    "Write code on paper with"
  val instruction: String = "Write test cases that test your code."

  def apply(cs: (String, String)*): Code = new Code(QuestionLang.ScalaOnly, cs.toSeq)

  override def punctuation = '.'

  lazy val all = (for case (w, t: Code) <- terms yield t).map(_.cs).flatten
  override def getQuestion(
      question: String | (String, String)
  ): String =
    question match
      case question: (String, String) => s"${questionToAsk}:\n${question._1}39d2c101a1a9746c5e54da6ba6a4ed48${question._2}" // Random hash to split at
      case _ => "unexpected"

  override def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    question match
      case question: (String, String) => s"$questionToAsk ${question._1.toString.toUpperCase}$punctuation"
      case _ => "unexpected"
