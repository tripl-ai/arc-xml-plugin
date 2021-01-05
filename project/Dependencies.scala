import sbt._

object Dependencies {
  // versions
  lazy val sparkVersion = "3.0.1"

  // testing
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.7" % "test,it"

  // arc
  val arc = "ai.tripl" %% "arc" % "3.7.0" % "provided"

  // spark
  val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"

  // spark XML
  val sparkXML = "com.databricks" %% "spark-xml" % "0.9.0"

  // Project
  val etlDeps = Seq(
    scalaTest,

    arc,

    sparkSql,

    sparkXML
  )
}