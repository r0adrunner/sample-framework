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

public class WordCounterBolt extends BaseRichBolt {

    Map<String, Integer> counters;
    private OutputCollector collector;

    @Override
    public void execute(Tuple input) {

	String word = input.getString(0);
	Integer count = counters.get(word);
	if (count == null)
	    count = 0;
	count++;
	counters.put(word, count);

	collector.emit(new Values(word, count));
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
	this.counters = new HashMap<String, Integer>();
	this.collector = collector;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	declarer.declare(new Fields("word", "count"));
    }

}
