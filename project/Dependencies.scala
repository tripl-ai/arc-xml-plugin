import sbt._

object Dependencies {
  // versions
  lazy val sparkVersion = "3.0.0"

  // testing
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.7" % "test,it"

  // arc
  val arc = "ai.tripl" %% "arc" % "3.2.0" % "provided"
  val typesafeConfig = "com.typesafe" % "config" % "1.3.1" intransitive()

  // spark
  val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
  val sparkHive = "org.apache.spark" %% "spark-hive" % sparkVersion % "provided"
  val sparkAvro = "org.apache.spark" %% "spark-avro" % sparkVersion % "provided"

  // spark XML
  val sparkXML = "com.databricks" %% "spark-xml" % "0.9.0"

  // Project
  val etlDeps = Seq(
    scalaTest,

    arc,
    typesafeConfig,

    sparkSql,
    sparkHive,
    sparkAvro,

    sparkXML
  )
}