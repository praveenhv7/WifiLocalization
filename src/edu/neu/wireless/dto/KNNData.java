package edu.neu.wireless.dto;

import java.util.LinkedList;
import java.util.List;

import edu.neu.wireless.mapper.TestDataMapper;
import edu.neu.wireless.mapper.TrainingDataMapper;

public class KNNData {

	List<TrainingDataMapper> trainingSet;
	List<TestDataMapper> testSet;
	
	public List<TrainingDataMapper> getTrainingSet() {
		return trainingSet;
	}
	public void setTrainingSet(List<TrainingDataMapper> trainingSet) {
		this.trainingSet = trainingSet;
	}
	public List<TestDataMapper> getTestSet() {
		return testSet;
	}
	public void setTestSet(List<TestDataMapper> testSet) {
		this.testSet = testSet;
	}
}
