package com.util;

import java.util.ArrayList;
import java.util.List;

import com.model.BinaryCompareObject;

public class FormulaUtil {

	public static double computeExpectedFreq(int freq, int freqOther,
			int totalFreq) {

		double expectedFreq = (double) (freq * freqOther)
				/ (double) (totalFreq);

		return expectedFreq;

	}

	public static double computeLogLikelihoodValue(
			List<Double> otherFreqValues, double expectedFreq) {

		double logLikelihood = 0;

		if (otherFreqValues != null && !otherFreqValues.isEmpty()) {

			double totalSum = 0;
			for (Double tempFreq : otherFreqValues) {

				double value = tempFreq * Math.log(tempFreq / expectedFreq);
				totalSum = totalSum + value;
			}

			logLikelihood = 2 * totalSum;
		}

		return logLikelihood;

	}

	public double computeRVCoefficent(List<Integer> freqRight,
			List<Integer> freqLeft, double meanRight, double meanLeft,
			double standardDevRight, double standardDevLeft) {

		double rv = 0;

		if ((freqLeft != null && !freqLeft.isEmpty())
				&& (freqRight != null && !freqRight.isEmpty())) {

			double value1 = freqLeft.size() - 1;
			
			double value2=freqRight.size()-1;
			
			double value=1;
			if(value1<value2){
				
				value=value1;
			}else{
				value= value2;
			}

			double totalSum = 0;
			for (int i = 0; i < value; i++) {

				double xvalue = freqLeft.get(i);
				double yvalue = freqRight.get(i);

				double totalSumTemp = (double) (xvalue - meanLeft)
						* (yvalue - meanRight);

				totalSum = totalSum + totalSumTemp;
			}

			if (standardDevRight == 0 || standardDevLeft == 0) {
				rv = 0;
			} else {
				rv = (double) (1 / value)
						* ((double) totalSum / (double) (standardDevRight * standardDevLeft));
			}
		}
		return rv;

	}

	public BinaryCompareObject computeBinarySimilar(
			List<String> distinctTokenNamesLeft,
			List<String> distinctTokenNamesRight) {

		Long timeTakenStart = System.currentTimeMillis();

		BinaryCompareObject binaryCompareObject = new BinaryCompareObject();

		List<String> tokenLeft = new ArrayList<String>();
		List<String> tokenRight = new ArrayList<String>();
		List<Integer> countSame = new ArrayList<Integer>();

		int tokenCountSame = 0;

		int tokenCountNotSame = 0;

		if (distinctTokenNamesLeft != null && distinctTokenNamesRight != null) {

			int sizeLeft = distinctTokenNamesLeft.size();

			int sizeRight = distinctTokenNamesRight.size();

			if (sizeLeft > sizeRight) {

				for (int i = 0; i < sizeRight; i++) {

					String tokenName1 = distinctTokenNamesLeft.get(i);

					String tokenName2 = distinctTokenNamesRight.get(i);

					tokenLeft.add(tokenName1);
					tokenRight.add(tokenName2);

					String binaryTokenName1 = toBinary(tokenName1, 8);

					String binaryTokenName2 = toBinary(tokenName2, 8);

					if (binaryTokenName1.equals(binaryTokenName2)) {

						countSame.add(1);
						tokenCountSame = tokenCountSame + 1;
					} else {
						countSame.add(0);
						tokenCountNotSame = tokenCountNotSame + 1;
					}
				}

				binaryCompareObject.setCountNotSame(tokenCountNotSame);
				binaryCompareObject.setCountSame(tokenCountSame);
				binaryCompareObject.setCountSameList(countSame);
				binaryCompareObject.setTokenListLeft(tokenLeft);
				binaryCompareObject.setTokenListRight(tokenRight);

			} else {

				for (int i = 0; i < sizeLeft; i++) {

					String tokenName1 = distinctTokenNamesLeft.get(i);

					String tokenName2 = distinctTokenNamesRight.get(i);

					tokenLeft.add(tokenName1);
					tokenRight.add(tokenName2);

					String binaryTokenName1 = toBinary(tokenName1, 8);

					String binaryTokenName2 = toBinary(tokenName2, 8);

					if (binaryTokenName1.equals(binaryTokenName2)) {

						countSame.add(1);
						tokenCountSame = tokenCountSame + 1;
					} else {
						countSame.add(0);
						tokenCountNotSame = tokenCountNotSame + 1;
					}
				}

				binaryCompareObject.setCountNotSame(tokenCountNotSame);
				binaryCompareObject.setCountSame(tokenCountSame);
				binaryCompareObject.setCountSameList(countSame);
				binaryCompareObject.setTokenListLeft(tokenLeft);
				binaryCompareObject.setTokenListRight(tokenRight);

			}

		}
		Long timeTakenStop = System.currentTimeMillis();

		Long timeTakenBinary = timeTakenStop - timeTakenStart;

		binaryCompareObject.setTimeTakenBinary(timeTakenBinary);

		double accuracy = ((double) tokenCountSame / (double) (tokenCountNotSame + tokenCountSame));

		binaryCompareObject.setAccuracy(accuracy);

		return binaryCompareObject;

	}

	public static String toBinary(String str, int bits) {
		String result = "";
		String tmpStr;
		int tmpInt;
		char[] messChar = str.toCharArray();

		for (int i = 0; i < messChar.length; i++) {
			tmpStr = Integer.toBinaryString(messChar[i]);
			tmpInt = tmpStr.length();
			if (tmpInt != bits) {
				tmpInt = bits - tmpInt;
				if (tmpInt == bits) {
					result += tmpStr;
				} else if (tmpInt > 0) {
					for (int j = 0; j < tmpInt; j++) {
						result += "0";
					}
					result += tmpStr;
				} else {
					System.err.println("argument 'bits' is too small");
				}
			} else {
				result += tmpStr;
			}
			result += " "; // separator
		}

		return result;
	}

}
