package com.kersplody.sample_framework.bolts;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class PrinterBolt extends BaseRichBolt {

    @Override
    public void execute(Tuple input) {

	String word = input.getString(0);
	Integer count = input.getInteger(1);
	System.out.println("Word: " + word +
			   " Count: " + count.toString());
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

}
