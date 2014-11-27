/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.base.pagination;

import java.io.Serializable;
import java.util.Collection;

/**
 * 支持分页导航的结果集对象
 * 
 * @author DuanYong
 * @since 2012-12-8下午1:51:56
 * @version 1.0
 */
public class PaginationSupport<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**数据结果集*/
	private Collection<T> data;
    /**总数*/
	private int totalCount;
	/**每页显示记录数*/
	private int pageSize = 20;
	/**总页数*/
    private int pageTotalCount = 1;
	/**当前页*/
	private int currPageNumber = 1;
	/**当前页开始记录数*/
	private int currStartIndex;
    /**是否为第一页*/
    private boolean isFirstPage=false;
    /**是否为最后一页*/
    private boolean isLastPage=false;   
    /**是否有前一页*/
    private boolean hasPreviousPage=false;
    /**是否有下一页*/
    private boolean hasNextPage=false;
    /**导航页码数*/
    private int navigatePages=8;
    /**所有导航页号*/
    private int[] navigatePageNumbers;
	
	public PaginationSupport(Collection<T> data, int totalCount, int currStartIndex, int pageSize) {
		setTotalCount(totalCount);
		setData(data);
		init(totalCount, currStartIndex, pageSize);
	}
	public PaginationSupport(int total, int currStartIndex) {
        init(total, currStartIndex,20);
    }
     
    public PaginationSupport(int total, int currStartIndex, int pageSize) {
        init(total, currStartIndex, pageSize);
    }

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
		}else {
			this.totalCount = 0;
		}
	}
	
	
     
    private void init(int totalCount, int currStartIndex, int pageSize){
        //设置基本参数
        this.totalCount=totalCount;
        this.pageSize=pageSize;
        this.pageTotalCount=(this.totalCount-1)/this.pageSize+1;
       
        int cuurPageNumber = currStartIndex / this.pageSize + 1;
        //根据输入可能错误的当前号码进行自动纠正
        if(cuurPageNumber<1){
            this.currPageNumber=1;
        }else if(cuurPageNumber>this.pageTotalCount){
            this.currPageNumber=this.pageTotalCount;
        }else{
            this.currPageNumber=cuurPageNumber;
        }
        this.currStartIndex = currStartIndex;
//        this.currStartIndex = (this.currPageNumber - 1) * this.pageSize;
//        if(this.currStartIndex > this.totalCount){
//        	this.currStartIndex = this.totalCount;
//        }
 
         
        //基本参数设定之后进行导航页面的计算
        calcNavigatePageNumbers();
         
        //以及页面边界的判定
        judgePageBoudary();
    }
     
    /**
     * 计算导航页
     */
    private void calcNavigatePageNumbers(){
        //当总页数小于或等于导航页码数时
        if(pageTotalCount<=navigatePages){
            navigatePageNumbers=new int[pageTotalCount];
            for(int i=0;i<pageTotalCount;i++){
                navigatePageNumbers[i]=i+1;
            }
        }else{ //当总页数大于导航页码数时
            navigatePageNumbers=new int[navigatePages];
            int startNum=currPageNumber-navigatePages/2;
            int endNum=currPageNumber+navigatePages/2;
             
            if(startNum<1){
                startNum=1;
                //(最前navPageCount页
                for(int i=0;i<navigatePages;i++){
                    navigatePageNumbers[i]=startNum++;
                }
            }else if(endNum>pageTotalCount){
                endNum=pageTotalCount;
                //最后navPageCount页
                for(int i=navigatePages-1;i>=0;i--){
                    navigatePageNumbers[i]=endNum--;
                }
            }else{
                //所有中间页
                for(int i=0;i<navigatePages;i++){
                    navigatePageNumbers[i]=startNum++;
                }
            }
        }
    }
 
    /**
     * 判定页面边界
     */
    private void judgePageBoudary(){
        isFirstPage = currPageNumber == 1;
        isLastPage = currPageNumber == pageTotalCount && (currPageNumber != 1 || pageTotalCount == 1);
        hasPreviousPage = currPageNumber!=1;
        hasNextPage = currPageNumber!=pageTotalCount;
    }
     
 
 
    /**
     * 得到每页显示多少条记录
     * @return {int}
     */
    public int getPageSize() {
        return pageSize;
    }
 
    /**
     * 得到页面总数
     * @return {int}
     */
    public int getPageTotalCount() {
        return pageTotalCount;
    }
 
    /**
     * 得到当前页号
     * @return {int}
     */
    public int getCurrPageNumber() {
        return currPageNumber;
    }
 
 
    /**
     * 得到所有导航页号 
     * @return {int[]}
     */
    public int[] getNavigatePageNumbers() {
        return navigatePageNumbers;
    }
 
    public boolean isFirstPage() {
        return isFirstPage;
    }
 
    public boolean isLastPage() {
        return isLastPage;
    }
 
    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }
 
    public boolean hasNextPage() {
        return hasNextPage;
    }
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setPageTotalCount(int pageTotalCount) {
		this.pageTotalCount = pageTotalCount;
	}
	public void setCurrPageNumber(int currPageNumber) {
		this.currPageNumber = currPageNumber;
	}
	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public void setNavigatePageNumbers(int[] navigatePageNumbers) {
		this.navigatePageNumbers = navigatePageNumbers;
	}
	
	public void setCurrStartIndex(int currStartIndex) {
		this.currStartIndex = currStartIndex;
	}
	/**
	 * 取得当前页总数
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-9 下午9:22:22
	 * @return
	 * @return int
	 */
	public int getCurrPageSize(){
		int currPageSize = this.getCurrStartIndex() + this.pageSize - 1;
		if(currPageSize > this.totalCount){
			currPageSize = this.totalCount;
		}
		return currPageSize;
	}
	/**
     * 取得当前页开始记录数
     * <p>方法说明:</p>
     * @auther DuanYong
     * @since 2012-12-9 下午8:55:53
     * @return
     * @return int
     */
	public int getCurrStartIndex() {
		return (this.getCurrPageNumber() - 1)  * this.pageSize + 1;
	}  
	/**
	 * 取得第一页开始记录数
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-9 下午7:34:36
	 * @return
	 * @return int
	 */
	public int getFirstStartIndex() {
		return 0;
	}
	/**
	 * 取得前一页开始记录数
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-9 下午7:34:36
	 * @return
	 * @return int
	 */
	public int getPrevStartIndex() {
		return (this.getPrevPage() - 1) * this.pageSize;
	}
	/**
	 * 取得下一页开始记录数
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-9 下午8:28:42
	 * @return
	 * @return int
	 */
	public int getNextStartIndex() {
		return (this.getNextPage() - 1) * this.pageSize;
	}
	/**
	 * 取得最后一页开始记录数
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-9 下午8:29:00
	 * @return
	 * @return int
	 */
	public int getLastStartIndex() {
		return (this.getLastPage() - 1) * this.pageSize;
	}
	
	/** 
     * 获取前一页 
     * @return 
     */  
    public int getPrevPage(){  
        return currPageNumber - 1;  
    }  
      
    /** 
     * 获取后一页 
     * @return 
     */  
    public int getNextPage(){  
        return currPageNumber + 1;  
    }  
      
    /** 
     * 获取最后一页 
     * @return 
     */  
    public int getLastPage(){  
        return pageTotalCount;  
    }
    
	
	
}
