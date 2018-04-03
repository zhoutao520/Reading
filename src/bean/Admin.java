package bean;

public class Admin {

	private int	id;	//管理员编号
	private String	name;	//昵称
	private String	password;	//密码
	private String	phone;	//电话
	private byte	role;	//权限（0为总管理员，其他为次管理员）
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public byte getRole() {
		return role;
	}
	public void setRole(byte role) {
		this.role = role;
	}
	
}
