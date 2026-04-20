package rehearsal

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.window
import muntabot.Muntabot

object Rehearsal extends App:
  type Subpage = "week" | "category" | "compare"
  val page = "#rehearsal"
  val title = "All questions from muntabot"
  var containerElement: dom.Node = null
  var searchTerm = ""
  val searchables = collection.mutable.Buffer[String]()
  private var _currentSubpage: Subpage = "week"

  override def currentSubpage = _currentSubpage

  def setSubpage(subpage: Subpage) =
    document.location.hash = s"$page/$subpage"
    _currentSubpage = subpage

  def setupUI(): Unit =
    try {
      _currentSubpage =
        document.location.hash.split("/")(1).asInstanceOf[Subpage]
    } catch error => {}
    containerElement = setupCommonComponents()
    runSubpage()

  def runSubpage() =
    if (currentSubpage == "week") then perWeek()
    else if (currentSubpage == "category") then perCategory()
    else if (currentSubpage == "compare") then compareView()

  def searchInput =
    document.getElementById("search-input").asInstanceOf[dom.html.Input]

  def setupCommonComponents(): dom.Element =
    val containerElement = Document.appendDynamicContainer()

    Document.appendLinkToApp(containerElement, Muntabot, "Randomize one question at a time")

    Document.appendText(
      containerElement,
      "h1",
      "All questions from muntabot"
    )

    Document.appendInput(containerElement, "Search", "search-input") {
      searchTerm = searchInput.value
      if searchTerm.length > 0 then
        searchView()
        searchInput.value = searchTerm
      else
        searchables.clear
        runSubpage()

    }

    Document.appendButton(
      containerElement,
      "Weekly",
      disabled = currentSubpage == "week"
    ) {
      searchTerm = ""
      setSubpage("week")
      perWeek()
    }

    Document.appendButton(
      containerElement,
      "Per category",
      disabled = currentSubpage == "category"
    ) {
      searchTerm = ""
      setSubpage("category")
      perCategory()
    }

    var scalaBtn: dom.html.Button = null
    var javaBtn: dom.html.Button = null

    scalaBtn = Document.appendButton(containerElement, "● Scala", disabled = true) {
      langFilter = LangFilter.Scala
      scalaBtn.disabled = true
      scalaBtn.textContent = "● Scala"
      javaBtn.disabled = false
      javaBtn.textContent = "○ Java"
      searchables.clear()
      runSubpage()
    }
    javaBtn = Document.appendButton(containerElement, "○ Java") {
      langFilter = LangFilter.Java
      scalaBtn.disabled = false
      scalaBtn.textContent = "○ Scala"
      javaBtn.disabled = true
      javaBtn.textContent = "● Java"
      searchables.clear()
      runSubpage()
    }

    Document.appendText(
      containerElement,
      "p",
      "Answer the questions and do the coding assignments below. Also give examples of normal and incorrect/strange usage. For each coding assignment: also write test cases that test your code. Tools: paper, pen, REPL, quick reference."
    )

    containerElement

  def searchView(): Unit =
    val contentElement =
      Document.appendDynamicContainer("content", containerElement)
    Document.appendText(contentElement, "h2", "Search results:")
    for searchable <- searchables do
      if (searchable.toLowerCase.contains(searchTerm.toLowerCase)) then
        Document.appendText(
          contentElement,
          "p",
          searchable
        )

  def perCategory(): Unit =
    val contentElement =
      Document.appendDynamicContainer("content", containerElement)
    Document.appendText(contentElement, "h2", "Per category")

    var number = 1
    for questionType <- Questions.types do
      val questions: Seq[String | (String, String)] = (for
        (_, q) <- terms
        if q.title == questionType.title && visibleIn(q.lang, langFilter)
      yield
        val items: Seq[String | (String, String)] = q match
          case xs: Concepts  => xs.cs
          case xs: Contrasts => xs.cs
          case xs: Code      => xs.cs
        items
      ).flatten
      if questions.nonEmpty then
        Document.appendText(contentElement, "h3", questionType.title)
        for question <- questions do
          val questionString =
            s"$number. ${questionType.getShortQuestion(question)}"
          searchables.append(questionString)
          Document.appendText(
            contentElement,
            "p",
            questionString
          )
          number += 1

  def compareView(): Unit =
    val contentElement = Document.appendDynamicContainer("content", containerElement)
    Document.appendText(contentElement, "h2", "Scala ↔ Java")
    val weeks = comparisons.map(_.week).distinct
    for week <- weeks do
      Document.appendText(contentElement, "h3", s"Week ${week.w}: ${Week.title(week)}")
      val table = document.createElement("table")
      table.classList.add("compare-table")
      val headerRow = document.createElement("tr")
      val scalaHeader = document.createElement("th")
      scalaHeader.textContent = "Scala"
      val javaHeader = document.createElement("th")
      javaHeader.textContent = "Java"
      headerRow.appendChild(scalaHeader)
      headerRow.appendChild(javaHeader)
      table.appendChild(headerRow)
      for comp <- comparisons.filter(_.week == week) do
        val row = document.createElement("tr")
        val scalaCell = document.createElement("td")
        scalaCell.textContent = comp.scalaName
        val javaCell = document.createElement("td")
        javaCell.textContent = comp.javaName
        row.appendChild(scalaCell)
        row.appendChild(javaCell)
        table.appendChild(row)
      contentElement.appendChild(table)
    Document.appendText(contentElement, "h3", CompareScalaJava.title)
    var number = 1
    for comp <- comparisons do
      val q = (comp.scalaName, comp.javaName)
      val questionString = s"$number. ${CompareScalaJava.getShortQuestion(q)}"
      searchables.append(questionString.toString)
      Document.appendText(contentElement, "p", questionString.toString)
      number += 1

  def perWeek(): Unit =
    val contentElement =
      Document.appendDynamicContainer("content", containerElement)
    val weeks = terms.map(_._1).distinct
    var number = 1
    for week <- weeks do
      val thisWeek = terms.filter(_._1 == week)
      val filteredWeek = thisWeek.filter((_, q) => visibleIn(q.lang, langFilter))
      if filteredWeek.nonEmpty then
        Document.appendText(
          contentElement,
          "h2",
          s"Week ${week.w}: ${Week.title(week)}"
        )
        for questionType <- Questions.types do
          val questionsForType: Seq[String | (String, String)] = (for
            (_, q) <- filteredWeek if q.title == questionType.title
          yield
            val items: Seq[String | (String, String)] = q match
              case xs: Concepts  => xs.cs
              case xs: Contrasts => xs.cs
              case xs: Code      => xs.cs
            items
          ).flatten
          if questionsForType.nonEmpty then
            Document.appendText(contentElement, "h3", questionType.title)
            for question <- questionsForType do
              val questionString = s"$number. ${questionType.getShortQuestion(question)}"
              searchables.append(questionString)
              Document.appendText(
                contentElement,
                "p",
                questionString
              )
              number += 1
