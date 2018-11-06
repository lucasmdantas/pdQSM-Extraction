package br.ufc.spark
import org.apache.spark.SparkContext._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import java.io._


import java.nio.file.{Files, Paths}
import java.io.ObjectInputStream.FilterValues



import org.apache.spark.rdd.RDD;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.io.BytesWritable;

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

import java.util.Arrays;
//import java.util.Arrays.*;
//import java.util.Arrays.copyOfRange;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import shapeless._0

import org.apache.hadoop.io.compress.GzipCodec

import org.apache.spark.storage.StorageLevel

import scala.annotation.tailrec

object QSM10 {
  def main(args: Array[String]) {
 
 
 //Create conf object
 val conf = new SparkConf()
 //.setMaster("local[*]")
 .setAppName("QSM")

 
 //create spark context object
 val sc = new SparkContext(conf)
// sc.hadoopConfiguration.set("mapred.output.compress", "false")
 
val startTime = System.currentTimeMillis();
println("Start Time: "+startTime)

val blocksize =args(0).toInt
 //val readTime = System.currentTimeMillis();
//val fullFile = Files.readAllBytes(Paths.get(args(1)))

		val fullFile2 = sc.binaryRecords(args(1),blocksize).persist(StorageLevel.MEMORY_AND_DISK)

//val fileSize = fullFile.size

//val blocks = (fileSize + blocksize -1)/blocksize
//println("Blocks: "+blocks)

/// println("Primeirod modo: "+fullFile.size)
 //val blocksFile = fullFile.grouped(blocksize).toArray
 //val blocksRdd = sc.parallelize(blocksFile).persist()
// blocksRdd.foreach(k=> println("Bloco: "+k+"Tamanho Bloco:"+k.size))
 
//put block numbers
val numeredBlocks = fullFile2.zipWithIndex();
 
//(block number,byte,occured 1 time,byte position in block) 
val QSM = numeredBlocks.flatMap{
   blockchar=>
        for( i<-0 until  ((blockchar._1).length ) by 1) yield{
        (blockchar._2,(blockchar._1)(i)&0xff,1,i)
               }
     
 }

//sort by block
val orderedQSM = QSM.sortBy(r=>r._1)

// Next we want the key to be (block_number,byte)
val mapKeys =orderedQSM.map(r=> ((r._1,r._2),(r._3,r._4))).persist(StorageLevel.MEMORY_AND_DISK)


// get only (block_number,byte),(ocurred) and reduce to get Quality and Quantity
val QS = mapKeys.map(r=>(r._1,r._2._1))
.sortBy(r=> r._1._1)
.reduceByKey(_+_)

//QS foreach println

// Saving bytes(Quality) for each block
val Q = QS.map(record => record._1._2)
Q.repartition(1)saveAsTextFile(args(2)+"/QSM/Q")

//Q foreach println

//Saving number of times(Quantity) byte ocurred in each block
val S = QS.map(record => record._2)

S.repartition(1).saveAsTextFile(args(3)+"/QSM/S")

//S foreach println

// get only (block_number,byte),(position) and
//aggregate to get the positions for each block that the byte ocurred
val M = mapKeys.map(r=>(r._1,r._2._2))
.aggregateByKey("")({case (aggr , value) => aggr + "," + s"${(value)}" }, (aggr1, aggr2) => aggr1 + aggr2)
.sortBy(r=> r._1._1)
.map(r=>r._2)

//Saving Measure
M.repartition(1).saveAsTextFile(args(4)+"/QSM/MGzip",classOf[org.apache.hadoop.io.compress.GzipCodec])

val endTimeAW = System.currentTimeMillis();
// println("EndTime After W: "+endTimeAW)
 

val totalTimeAW = endTimeAW - startTime;
println("Total Time After Write: "+totalTimeAW) 
println("Total Time After Write: "+totalTimeAW)
println("Total Time After Write: "+totalTimeAW)
//Saving the execution time
//val pw = new PrintWriter(new File(args(4)+ "/QSM/totalTime.txt" ))
//pw.write(totalTimeAW.toString())
//pw.close

 

 sc.stop
 
 
 
  }
  
}