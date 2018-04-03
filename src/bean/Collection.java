package bean;

public class Collection {

	private int	id;	//收藏编号
	private String	userId;	//用户编号
	private int	bookId;	//图书编号
	private String	time;	//收藏时间
	private int	historyChapter;	//上次浏览章节
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getHistoryChapter() {
		return historyChapter;
	}
	public void setHistoryChapter(int historyChapter) {
		this.historyChapter = historyChapter;
	}
	
}
