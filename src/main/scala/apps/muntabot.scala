package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import rehearsal.Rehearsal

object Muntabot extends App:
  val page = "#muntabot"
  val title = "muntabot"

  val MaxWeek = 10

  var untilWeek = MaxWeek

  def weekInput =
    document.getElementById("week-input").asInstanceOf[dom.html.Input]

  def setupUI(): Unit =
    val containerElement = Document.appendDynamicContainer()

    Document.appendText(
      containerElement,
      "p",
      "Randomize one question at a time through reading week:"
    )

    Document.appendInput(containerElement, "Enter integers (1-10)", "week-input") {
      val newInput = weekInput.value

      if newInput == "" then untilWeek = MaxWeek
      else
        val toWeek =
          newInput.trim.filter(_.isDigit).toIntOption.getOrElse(MaxWeek)
        untilWeek = MaxWeek.min(toWeek)
        weekInput.value = untilWeek.toString
      end if

    }

    Document.appendLink(containerElement, s"${Rehearsal.page}/week", "Spoiler all questions")

    Document.appendLink(containerElement, "#rehearsal/compare", "Scala ↔ Java")

    var scalaBtn: dom.html.Button = null
    var javaBtn: dom.html.Button = null
    var codeBtn: dom.html.Button = null

    scalaBtn = Document.appendButton(containerElement, "● Scala", disabled = true) {
      langFilter = LangFilter.Scala
      scalaBtn.disabled = true
      scalaBtn.textContent = "● Scala"
      javaBtn.disabled = false
      javaBtn.textContent = "○ Java"
      if codeBtn != null then codeBtn.disabled = false
    }
    javaBtn = Document.appendButton(containerElement, "○ Java") {
      langFilter = LangFilter.Java
      scalaBtn.disabled = false
      scalaBtn.textContent = "○ Scala"
      javaBtn.disabled = true
      javaBtn.textContent = "● Java"
      if codeBtn != null then codeBtn.disabled = true
    }

    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = "Click on the buttons above and you will receive a task."

    val showHelp = document.createElement("a").asInstanceOf[dom.html.Anchor]
    showHelp.href = ""
    showHelp.textContent = ""
    showHelp.target = "_blank" // Opens in new window

    for questionType <- Questions.types do
      val qt = questionType
      val btn = Document.appendButton(containerElement, qt.title) {
        qt match
          case Code =>
            val contents = Code
              .getQuestion(Code.pickAnyQuestion(untilWeek, Code, langFilter))
              .split("39d2c101a1a9746c5e54da6ba6a4ed48")
            showText.textContent = contents(0)
            if contents.length > 1 then
              showHelp.href = contents(1)
              showHelp.textContent = "Lookup information in the course book"
            else
              showHelp.textContent = ""
              showHelp.href = ""
          case _ =>
            showHelp.textContent = ""
            showHelp.href = ""
            showText.textContent = qt.getQuestion(
              qt.pickAnyQuestion(untilWeek, qt, langFilter)
            )
            if untilWeek < 1 || untilWeek > MaxWeek then
              untilWeek = MaxWeek
              weekInput.value = untilWeek.toString
            weekInput.value = untilWeek.toString
      }
      qt match
        case Code => codeBtn = btn
        case _    =>

    Document.appendText(
      containerElement,
      "p",
      "Tools: paper, pencil, REPL, rapid lookup."
    )

    containerElement.appendChild(showText)
    containerElement.appendChild(showHelp)
