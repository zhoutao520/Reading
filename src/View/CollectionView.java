package View;

public class CollectionView {

	private int	id;	//收藏编号
	private String	userId;	//用户编号
	private int	bookId;	//图书编号
	private int	historyChapter;	//上次浏览章节
	private String	chapterName;	//章节名
	private String	authorName;	//作者昵称
	private String	bookName;	//书名
	private String	secondName;	//类型名
	private String	firstName;	//类型名
	private byte	state;	//图书状态（0-审核中，1-新建，2-签约作品）
	private String	profile;	//图书简介
	private int	wordNum;	//字数
	private int	collectionNum;	//收藏数
	private int	clickNum;	//点击数
	private String	cover;	//封面
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
	public int getHistoryChapter() {
		return historyChapter;
	}
	public void setHistoryChapter(int historyChapter) {
		this.historyChapter = historyChapter;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public int getWordNum() {
		return wordNum;
	}
	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}
	public int getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}
	public int getClickNum() {
		return clickNum;
	}
	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
}
