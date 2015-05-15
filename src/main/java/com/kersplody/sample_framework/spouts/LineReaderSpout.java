package com.kersplody.sample_framework.spouts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class LineReaderSpout extends BaseRichSpout {

    private String inputFile;
    private SpoutOutputCollector collector;
    private boolean completed = false;
    private FileReader fileReader;
    private TopologyContext context;
    
    @Inject
    public void setInputFile(@Named("input-file") String inputFile) {
	this.inputFile = inputFile;
    }

    @Override
    public void nextTuple() {
	if (completed) {
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
 
	    }
	}
	String str;
	BufferedReader reader = new BufferedReader(fileReader);
	try {
	    while ((str = reader.readLine()) != null) {
		this.collector.emit(new Values(str), str);
	    }
	} catch (Exception e) {
	    throw new RuntimeException("Error reading typle", e);
	} finally {
	    completed = true;
	}
  

    }

    @Override
    public void open (Map config, TopologyContext context, SpoutOutputCollector collector) {
	try {
	    this.context = context;
	    this.fileReader = new FileReader(inputFile);
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("Error reading file "
				       + inputFile);
	}
	this.collector = collector; 
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	declarer.declare(new Fields("line"));
    }
}
