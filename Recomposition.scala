package br.ufc.spark

import org.apache.log4j.Logger
import scala.io.Source
import java.io._

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import java.io.File;
//import scala.collection.parallel.mutable.ParTrieMap.Size

// For implicit conversions like converting RDDs to DataFrames

//import org.apache.spark.implicits._

//import spark.implicits._


object Recomposition {

   def main(args: Array[String]) {

  // create Spark context with Spark configuration
    val conf = new SparkConf()
 .setMaster("local[*]")
 .setAppName("QSM")
 
 //create spark context object
 val sc = new SparkContext(conf)

    // Read text file in spark RDD 

/*for (line <- Source.fromFile(filename).getLines) {
    println(line)
}*/
    
 val Qfile = args(0)
 val Q = Source.fromFile(Qfile).getLines.toArray.map(_.toInt).map(_.toByte)
 //val Qint = Integer.parseInt.Q
 //Q foreach println
 
 val Sfile = args(1)
 val S = Source.fromFile(Sfile).getLines.toArray.map(_.toInt)
//S foreach println
 
 val Mfile = args(2)
 val M = Source.fromFile(Mfile).getLines.toArray.map(_.toInt)
// M foreach println
 
 val fileSize = M.reduceLeft(_ max _).toInt
 val fileSizeTotal = fileSize+1
 println("File size: "+fileSizeTotal)
 
 var linhaM=0;
 
val finalFile = new Array[Byte](fileSize+1)
finalFile foreach println
    
 
 //val i : BigDecimal =
 
for (i <- 0 to Q.size-1)
   {	
   	//println("i:= "+i)
     
      // println("S(i):= "+S(i))
     //println("linhaM antes: "+linhaM)
       for (k<-linhaM to (S(i)+linhaM -1))
         
       {   //println("k:= "+k)
       			finalFile.update(M(k), Q(i))
       			//println("Byte "+finalFile(M(k))+" Posicao "+M(k))
       }
     
       linhaM=linhaM + S(i).toInt
       //println("linhaM depois: "+linhaM)
       
   }

/*val bos = new BufferedOutputStream(new FileOutputStream(args(3))
Stream.continually(bos.write(finalFile))
bos.close();   */
def destination = new File(args(3))


 
 
   val outStream = new FileOutputStream(destination);  
   val byteOutStream = new ByteArrayOutputStream();  
   // writing bytes in to byte output stream  
   byteOutStream.write(finalFile); //data  
   byteOutStream.writeTo(outStream);  
   outStream.close();  
   

/*def serialise(value: Any): Array[Byte] = {
    val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(value)
    oos.close
    stream.toByteArray
  }

serialise(finalFile)*/

/*val bos = new BufferedOutputStream(new FileOutputStream(destination))
bos.write(finalFile)
bos.close()*/


  // finalFile foreach println

 
sc.stop
}}

