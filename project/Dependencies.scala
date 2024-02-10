import sbt._

object Dependencies {
  // versions
  lazy val sparkVersion = "3.3.4"

  // testing
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.7" % "test,it"

  // arc
  val arc = "ai.tripl" %% "arc" % "4.2.0" % "provided"

  // spark
  val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"

  // spark XML
  val sparkXML = "com.databricks" %% "spark-xml" % "0.16.0"

  // Project
  val etlDeps = Seq(
    scalaTest,

    arc,

    sparkSql,

    sparkXML
  )
}