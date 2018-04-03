package bean;

public class SecondType {

	private int	id;	//类型编号
	private int	firstTypeId;	//一级类型编号
	private String	name;	//类型名
	private String	photo;	//类型图片
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFirstTypeId() {
		return firstTypeId;
	}
	public void setFirstTypeId(int firstTypeId) {
		this.firstTypeId = firstTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
