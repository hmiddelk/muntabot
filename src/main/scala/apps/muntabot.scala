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

    Document.appendLinkToApp(
      containerElement,
      Rehearsal,
      "Spoiler all questions"
    )

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
      val btn = Document.appendButton(containerElement, questionType.title) {
        if questionType == Code then
          val contents = questionType
            .getQuestion(
              questionType.pickAnyQuestion(untilWeek, questionType, langFilter)
            )
            .split(
              "39d2c101a1a9746c5e54da6ba6a4ed48"
            ) // Random hash to split at, see model.scala

          showText.textContent = contents(0)

          if contents.length > 1 then
            showHelp.href = contents(1)
            showHelp.textContent = "Loopup information in the course book"
          else
            showHelp.textContent = ""
            showHelp.href = ""
        else
          showHelp.textContent = ""
          showHelp.href = ""

          showText.textContent = questionType.getQuestion(
            questionType.pickAnyQuestion(untilWeek, questionType, langFilter)
          )

          if untilWeek < 1 || untilWeek > MaxWeek then
            untilWeek = MaxWeek
            weekInput.value = untilWeek.toString

          weekInput.value = untilWeek.toString
      }
      if questionType == Code then
        codeBtn = btn

    Document.appendText(
      containerElement,
      "p",
      "Tools: paper, pencil, REPL, rapid lookup."
    )

    containerElement.appendChild(showText)
    containerElement.appendChild(showHelp)
