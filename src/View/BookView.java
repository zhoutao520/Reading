package View;

public class BookView {
	private String	authorId;	//编号
	private String	authorName;	//作者昵称
	private String	head;		//头像
	private String	authorProfile;	//个人简介
	private int	bookId;		//图书编号
	private String	bookName;	//书名
	private String	label;		//标签
	private int	wordNum;	//字数
	private int	collectionNum;	//收藏数
	private byte	state;		//图书状态（0-审核中，1-新建，2-签约作品）
	private String	profile;	//图书简介
	private int	clickNum;	//点击数
	private String	cover;		//封面
	private String 	creationTime;	//创建时间
	private int	secondId;	//类型编号
	private String	secondName;	//类型名
	private int	firstId;	//类型编号
	private String	firstName;	//类型名
	private int	chapterId;	//最新章节
	private int	recommendNum;	//最新章节
	private String	chapterName;	//最新章节名
	private String	chapterTime;	//最新章节时间
	private byte isRecommend;//是否被推荐 
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getAuthorProfile() {
		return authorProfile;
	}
	public void setAuthorProfile(String authorProfile) {
		this.authorProfile = authorProfile;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public int getSecondId() {
		return secondId;
	}
	public void setSecondId(int secondId) {
		this.secondId = secondId;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public int getFirstId() {
		return firstId;
	}
	public void setFirstId(int firstId) {
		this.firstId = firstId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public String getChapterTime() {
		return chapterTime;
	}
	public void setChapterTime(String chapterTime) {
		this.chapterTime = chapterTime;
	}
	public int getRecommendNum() {
		return recommendNum;
	}
	public void setRecommendNum(int recommendNum) {
		this.recommendNum = recommendNum;
	}
	public byte getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(byte isRecommend) {
		this.isRecommend = isRecommend;
	}
	
}
