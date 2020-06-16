package ai.tripl.arc

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

import org.apache.spark.sql._

import ai.tripl.arc.util.log.LoggerFactory
import ai.tripl.arc.util.TestUtils

import ai.tripl.arc.api._
import ai.tripl.arc.api.API._
import ai.tripl.arc.config._
import ai.tripl.arc.util._
import ai.tripl.arc.udf.UDF

class UDFSuite extends FunSuite with BeforeAndAfter {

  var session: SparkSession = _
  var logger: ai.tripl.arc.util.log.logger.Logger = _
  var expected: String = _

  before {
    implicit val spark = SparkSession
                  .builder()
                  .master("local[*]")
                  .config("spark.ui.port", "9999")
                  .appName("Spark ETL Test")
                  .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    // set for deterministic timezone
    spark.conf.set("spark.sql.session.timeZone", "UTC")

    session = spark
    logger = LoggerFactory.getLogger(spark.sparkContext.applicationId)
    val arcContext = TestUtils.getARCContext(isStreaming=false)

    // register udf
    UDF.registerUDFs()(spark, logger, arcContext)
  }

  after {
    session.stop()
  }

  test("UDFSuite: to_xml") {
    implicit val spark = session

    val df = spark.sql("""
    SELECT
      to_xml(
        NAMED_STRUCT(
          'Document', NAMED_STRUCT(
              '_VALUE', NAMED_STRUCT(
                'child0', 0,
                'child1', NAMED_STRUCT(
                  'nested0', 0,
                  'nested1', 'nestedvalue'
                )
              ),
          '_attribute', 'attribute'
          )
        )
      ) AS xml
      ,to_xml(null) AS null_test
    """)

    assert(df.first.getString(0) ==
    """<Document attribute="attribute">
    |  <child0>0</child0>
    |  <child1>
    |    <nested0>0</nested0>
    |    <nested1>nestedvalue</nested1>
    |  </child1>
    |</Document>""".stripMargin)
    assert(df.first.isNullAt(1))
  }

}

