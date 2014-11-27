package com.bjm.pms.crawler.core.processor;
/**
 * 任务处理器链
 * @author javacoo
 * @since 2011-11-09
 */
public class ProcessorChain {
    /**下一个处理器链*/
	private ProcessorChain nextProcessorChain;
	/**第一个处理器*/
    private Processor firstProcessor;
    
    public ProcessorChain(Processor firstProcessor) {
		super();
		this.firstProcessor = firstProcessor;
	}
    
	
	public ProcessorChain getNextProcessorChain() {
		return nextProcessorChain;
	}


	public void setNextProcessorChain(ProcessorChain nextProcessorChain) {
		this.nextProcessorChain = nextProcessorChain;
	}


	public Processor getFirstProcessor() {
		return firstProcessor;
	}
	public void setFirstProcessor(Processor firstProcessor) {
		this.firstProcessor = firstProcessor;
	}
    
    

}
