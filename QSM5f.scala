package br.ufc.spark
import org.apache.spark.SparkContext._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf



import java.nio.file.{Files, Paths}
import java.io.ObjectInputStream.FilterValues



import org.apache.spark.rdd.RDD;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.io.BytesWritable;

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.io._
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


import scala.annotation.tailrec
import org.apache.spark.storage.StorageLevel
import org.apache.hadoop.io.LongWritable


object QSM5f {
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
		
		val fullFile2 = sc.binaryRecords(args(1),blocksize).persist(StorageLevel.MEMORY_AND_DISK)
		
		val zipedFile2 = fullFile2.zipWithIndex();
		println("ZipWithIndex")

		//(byte,block number,1,global position) 
		val zipedFileQSM = zipedFile2.flatMap{
			blockchar=>
			for( i<-0 until  ((blockchar._1).length ) by 1) yield{
				(((blockchar._1)(i)&0xff),blockchar._2,1,i + (blocksize)*blockchar._2 )
			}

		}
		println("FlatMap ------------------------------")

		//sort by byte
		val orderedZipedFileQSM = zipedFileQSM.sortBy(r=> r._1).persist(StorageLevel.MEMORY_AND_DISK)
		
		println("SortBy -------------------------------------")

		//get Quality(Q) and Quantity(S), reducing the times that byte ocurred in the file 
		val QS = orderedZipedFileQSM.map(registro =>(registro._1,registro._3))
		.reduceByKey(_+_)
		.persist(StorageLevel.MEMORY_AND_DISK)
    println("ReduceByKey -------------------------------------")

		val Q = QS.map(record => record._1)
		println("Map -------------------------------------")
		Q.saveAsTextFile(args(2)+"/QSM/Q")
		println("Save -------------------------------------")
		val S = QS.map(record => record._2)
		println("Map -------------------------------------")
		S.saveAsTextFile(args(3)+"/QSM/S")
		println("Save -------------------------------------")
		//Saving the positions 1 3 5 6...78 24789....123
		//we know when its a diff byte if byte_position > byte_position.next
		val M = orderedZipedFileQSM.map(record => record._4)
		 println("Map -------------------------------------")
		//M.repartition(1).saveAsTextFile(pathTosave+"/QSM/M")
		M.saveAsTextFile(args(4)+"/QSM/MGzip",classOf[org.apache.hadoop.io.compress.GzipCodec])
		println("Save -------------------------------------")

		val endTimeAW = System.currentTimeMillis();
		// println("EndTime After W: "+endTimeAW)


		val totalTimeAW = endTimeAW - startTime;
		println("Total Time After Write: "+totalTimeAW) 


		/*val pw = new PrintWriter(new File(args(4)+"/QSM/totalTime.txt" ))
		pw.write(totalTimeAW.toString())
		pw.close*/


		sc.stop



	}

}