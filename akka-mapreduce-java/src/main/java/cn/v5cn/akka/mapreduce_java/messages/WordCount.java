package cn.v5cn.akka.mapreduce_java.messages;

public final class WordCount {
	private final String word;
	private final Integer count;
	
	public WordCount(String inWord,Integer inCount){
		this.word = inWord;
		this.count = inCount;
	}

	public String getWord() {
		return word;
	}

	public Integer getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "WordCount [word=" + word + ", count=" + count + "]";
	}
	
}
