package ai.tripl.arc.plugins.udf

import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter
import java.io.CharArrayWriter

import scala.collection.JavaConverters._
import com.fasterxml.jackson.databind._

import org.apache.spark.sql._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

import ai.tripl.arc.util.log.logger.Logger
import ai.tripl.arc.api.API.ARCContext
import ai.tripl.arc.util.Utils

import ai.tripl.arc.util.SerializableConfiguration
import com.databricks.spark.xml.util._
import com.sun.xml.txw2.output.IndentingXMLStreamWriter

class XML extends ai.tripl.arc.plugins.UDFPlugin {

  val version = ai.tripl.arc.xml.BuildInfo.version

  // one udf plugin can register multiple user defined functions
  override def register()(implicit spark: SparkSession, logger: Logger, arcContext: ARCContext) = {

    // register custom UDFs via sqlContext.udf.register("funcName", func )
    spark.sqlContext.udf.register("to_xml", XMLPlugin.toXML _ )
  }

}

object XMLPlugin {

  // convert structtype to xml
  def toXML(value: Row): String = {
    if (value != null) {
      val factory = XMLOutputFactory.newInstance
      val writer = new CharArrayWriter
      val xmlWriter = factory.createXMLStreamWriter(writer)
      val indentingXmlWriter = new IndentingXMLStreamWriter(xmlWriter)
      ai.tripl.arc.load.XMLLoad.StaxXmlGenerator(value.schema, indentingXmlWriter)(value)
      indentingXmlWriter.flush
      writer.toString.trim
    } else {
      null
    }
  }
  
}
