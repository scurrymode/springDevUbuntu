package com.sist.mapred;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordDriver {
	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setMapperClass(WordMapper.class);
			job.setReducerClass(WordReducer.class);
			job.setJarByClass(WordDriver.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			FileInputFormat.addInputPath(job, new Path("./input"));
			FileOutputFormat.setOutputPath(job, new Path("./output"));
			
			job.waitForCompletion(true);
			} catch (Exception e) {
		}
	}

}
