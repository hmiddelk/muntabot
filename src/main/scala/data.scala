package shared

lazy val terms = Seq[(Week, Concepts | Contrasts | Code)](
  Week(1) -> Concepts(
    "procedure",
    "while statement",
    "for statement",
    "assignment",
    "compilation",
    "reference type",
    "boolean value",
    "sequenced execution",
    "abstraction"
  ),
  Week(1) -> Concepts.forScala(
    "function",
    "string interpolator",
    "value types",
    "REPL"
  ),
  Week(1) -> Concepts.forJava(
    "primitive types",
    "jshell"
  ),
  Week(1) -> Contrasts(
    "while" -> "for-do",
    "type" -> "value",
    "compilation error" -> "runtime error",
    "Char" -> "String",
    "source code" -> "machine code"
  ),
  Week(1) -> Contrasts.forScala(
    "map" -> "foreach",
    "for-expression" -> "for-statement",
    "Vector" -> "Array",
    "if-expression" -> "if-statement",
    "def" -> "val",
    "var" -> "val"
  ),
  Week(1) -> Code(
    "an expression that calculates the area of a circle given the radius of r" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Define%20name%20for%20expressions",
    "a Boolean expression that is true if x is greater than zero or x is less than -42" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Alternatives%20with%20if-expressions",
    "a loop that prints the first 42 integers" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Control%20structures:%20alternatives%20and%20repetition",
    "a function isEven(n: Int) which is true if n is an even number" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Control%20structures:%20alternatives%20and%20repetition"
  ),
  Week(2) -> Concepts(
    "map collection method",
    "indexing",
    "control structure",
    "main program"
  ),
  Week(2) -> Concepts.forScala(
    "for expression"
  ),
  Week(2) -> Contrasts(
    "pseudocode" -> "executable implementation",
    "parameter" -> "argument",
    "returntype" -> "parametertype"
  ),
  Week(2) -> Contrasts.forScala(
    "for-do" -> "for-yield"
  ),
  Week(2) -> Code(
    "a function that returns the smallest integer in a sequence of integers,\nwithout using built-in min" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Loop%20through%20a%20collection%20with%20a%20while-statement",
    "a function that returns the largest integer in a sequence of integers,\nwithout using built-in max" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Loop%20through%20a%20collection%20with%20a%20while-statement",
    "two mutable variables that are first initialized\nwith two different integers and then swap values with each other" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Variable%20declaration%20and%20assignment%20statement",
    "a function that sums all integers in a sequence,\nwithout using built-in sums" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Loop%20through%20the%20elements%20in%20a%20vector"
  ),
  Week(3) -> Concepts(
    "anonymous function",
    "lambda function",
    "predicate",
    "real function",
    "activation record",
    "call stack",
    "return type"
  ),
  Week(3) -> Concepts.forScala(
    "local function",
    "function as real value"
  ),
  Week(3) -> Concepts.forScala(
    "default argument",
    "named arguments"
  ),
  Week(3) -> Contrasts.forScala(
    "overloading" -> "default-argument",
    "named argument" -> "default-argument"
  ),
  Week(3) -> Code(
    "an anonymous function Int => Int which gives the square and which is applied to all elements of an integer sequence" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Anonymous%20functions",
     "a definition of a spurious function that returns random even integers between n and m" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Examples%20of%20impure%20functions:%20random%20numbers"
  ),
  Week(4) -> Concepts(
    "package",
    "private member",
    "namespace",
    "import",
    "dotnotation",
    "classpath"
  ),
  Week(4) -> Concepts.forJava(
    "singleton pattern"
  ),
  Week(4) -> Concepts.forScala(
    "singleton objekt",
    "apply method",
    "tuple",
    "extension method"
  ),
  Week(4) -> Contrasts.forScala(
    "call by value" -> "call by name",
    "val" -> "lazy val",
    "def" -> "lazy val"
  ),
  Week(5) -> Concepts(
    "class",
    "attribute",
    "factory method",
    "instantiation",
    "state",
    "keyword this",
    "string representation"
  ),
  Week(5) -> Concepts.forScala(
    "class parameter",
    "companion object",
    "uniform access principle"
  ),
  Week(5) -> Concepts.forJava(
    "static member",
    "Record"
  ),
  Week(5) -> Contrasts(
    "getter" -> "setter",
    "referential equality" -> "value or structural equality"
  ),
  Week(5) -> Contrasts.forScala(
    "function" -> "method",
    "class" -> "case-class",
    "singleton object" -> "class"
  ),
  Week(6) -> Concepts(
    "exception"
  ),
  Week(6) -> Concepts.forScala(
    "pattern matching",
    "match expression",
    "constructor pattern",
    "Option",
    "Try"
  ),
  Week(6) -> Concepts.forJava(
    "Optional",
    "switch expression"
  ),
  Week(6) -> Contrasts.forScala(
    "Try" -> "try-catch"
  ),
  Week(7) -> Concepts(
    "pseudo code",
    "sequence method",
    "filtering",
    "transformation",
    "registration",
    "in-place modification",
    "string similarity",
    "sorted sequence of strings",
    "enumeration",
    "linear search",
    "insertion sort"
  ),
  Week(7) -> Concepts.forScala(
    "repeated parameters",
    "ArrayBuffer"
  ),
  Week(7) -> Concepts.forJava(
    "ArrayList"
  ),
  Week(7) -> Contrasts(
    "transformation to new sequence" -> "in-place transformation"
  ),
  Week(7) -> Contrasts.forScala(
    "mutable collection" -> "immutable collection",
    "ArrayBuffer" -> "Array",
    "enumerated values with integers" -> "enumerated values with enum",
    "sealed type hierarchy" -> "unsealed type hierarchy",
    "List" -> "ListBuffer"
  ),
  Week(7) -> Code(
    "a function that linearly searches for an instance of the class Person\nwith a given last name in an unsorted sequence of people\nusing a while (and not find/indexOf).\n The function's return type should be Option" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Searching",
    "a program that records 1000 dice rolls in an Array" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Registration",
    "a function copy that copies an Array of integers\nelement by element to a new Array\nthat uses a while statement in the implementation" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Algorithm:%20SEQ-COPY",
    "a function insert that given an Array of integers\ninserts an element at a specific location\nusing a while statement in the implementation" ->  "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Example:%20SEQ-INSERT%2FREMOVE-COPY",
    "a function that determines whether two strings are equal\nthat uses a while statement in the implementation" ->  "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Comparing%20strings:%20equality"
  ),
  Week(8) -> Concepts(
    "nested structure",
    "type parameter",
    "generic funktion"
  ),
  Week(8) -> Concepts.forScala(
    "matrix"
  ),
  Week(8) -> Contrasts(
    "generic datastructure" -> "generic function"
  ),
  Week(8) -> Contrasts.forScala(
    "matrix" -> "vector"
  ),
  Week(8) -> Code(
    "a function that prints a string matrix row by row" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Iterate%20over%20nested%20structure",
    "a predicate that tests whether a nested sequence of integers\nis a mathematical matrix with m rows and n columns\nwith and without the help of the existing method forall" ->  "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=How%20to%20index%20in%20matrices%3F"
  ),
  Week(9) -> Contrasts(
    "set" -> "sequence",
    "set" -> "map",
    "map" -> "sequence"
  ),
  Week(9) -> Code(
    "a function readPersonSet that reads lines of comma-separated last names and first names from a text file\nand creates an array of instances of the case class Person and throws an exception if there are duplicates" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Reading%20text%20from%20file%20and%20webpage",
    "a function readPersonMap that reads lines with comma-separated last names and first names and social security numbers from a text file\nand creates a key-value table with social security numbers of type Long as the key and instances of the case class Person as the value" -> "https://hmiddelk.github.io/muntabot/compendium.pdf#nameddest=Reading%20text%20from%20file%20and%20webpage"
  ),
  Week(10) -> Concepts(
    "inheritance",
    "abstract class",
    "subtype polymorphism",
    "basetype",
    "final member",
    "code review",
    "protected member",
    "override",
    "dynamic binding",
    "code duplication",
    "anonymous class"
  ),
  Week(10) -> Concepts.forScala(
    "sealed type",
    "trait",
    "mixin composition"
  ),
  Week(10) -> Concepts.forJava(
    "interface",
    "multiple interface inheritance"
  ),
  Week(10) -> Contrasts(
    "subtype" -> "supertype",
    "inheritance" -> "composition",
    "abstract member" -> "concrete member",
    "static type" -> "dynamic type"
  ),
  Week(10) -> Contrasts.forScala(
    "class" -> "trait"
  )
)

lazy val comparisons: Seq[Comparison] = Seq(
  Comparison(Week(1),  "if-expression",    "ternary operator"),
  Comparison(Week(3),  "anonymous function", "lambda function"),
  Comparison(Week(1),  "value types",      "primitive types"),
  Comparison(Week(1),  "REPL",             "jshell"),
  Comparison(Week(1),  "procedure",        "void method"),
  Comparison(Week(4),  "singleton object",  "singleton pattern"),
  Comparison(Week(5),  "companion object",  "static member"),
  Comparison(Week(5),  "case class",        "Record"),
  Comparison(Week(6),  "Option",            "Optional"),
  Comparison(Week(6),  "match expression",  "switch expression"),
  Comparison(Week(7),  "ArrayBuffer",       "ArrayList"),
  Comparison(Week(10), "trait",             "interface"),
  Comparison(Week(10), "mixin composition", "multiple interface inheritance"),
)
