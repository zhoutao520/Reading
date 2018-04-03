var vm = new Vue({
	el:'#app',
	data:{
		adminId:'',
		admins:[],
		add:0,
		id:'',
		name:'',
		password:'',
		phone:''
	},
	methods:{
		exit:function(){
  		  this.clearCookie("admin");
  		  location.href="../index.jsp";  
  	  	},
  	  	handleEdit(id,row){
  	  		this.id=row.id;
  	  		this.name=row.name;
  	  		this.password=row.password;
  	  		this.phone=row.phone;
  	  		this.add=2;
  	  	},
  	  	handleDelete(id,row){
	  	  	var url = "/BookReading/servlet/AdminServlet";
	        var date = new URLSearchParams();
	        date.append("method","delAdmin");
	        date.append("id",row.id);
	        axios.post(url,date)
	        .then(response=>{
	        	this.getAdmins();
	        })
	        .catch(error=>{
	            console.log(error);
	        })
  	  	},
  	  	addAdmin(){
  	  		this.id='';
  	  		this.name='';
  	  		this.password='';
  	  		this.phone='';
  	  		this.add=1;
  	  	},
  	  	onSubmit(){
	  	  	var url = "/BookReading/servlet/AdminServlet";
	        var date = new URLSearchParams();
	        if(this.add==1){
	        	date.append("method","addAdmin");
	        }else if(this.add==2){
	        	date.append("method","editAdmin");
	        }
	        date.append("id",this.id);
	        date.append("name",this.name);
	        date.append("password",this.password);
	        date.append("phone",this.phone);
	        axios.post(url,date)
	        .then(response=>{
	        	this.add=0;
	        	this.getAdmins();
	        })
	        .catch(error=>{
	            console.log(error);
	        })
  	  	},
  	  	getAdmins(){
	  	  	var url = "/BookReading/servlet/AdminServlet";
	        var date = new URLSearchParams();
	        date.append("method","getAdmins");
	        axios.post(url,date)
	        .then(response=>{
	           if(response.data.success){
	               this.admins=response.data.admins;
	           }
	        })
	        .catch(error=>{
	            console.log(error);
	        })
  	  	},
		//设置cookie
	      setCookie: function (cname, cvalue, exdays) {
	          var d = new Date();
	          d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	          var expires = "expires=" + d.toUTCString();
	          document.cookie = cname + "=" + cvalue + "; " + expires;
	      },
	    //获取cookie
	      getCookie: function (cname) {
	          var name = cname + "=";
	          var ca = document.cookie.split(';');
	          for (var i = 0; i < ca.length; i++) {
	              var c = ca[i];
	              while (c.charAt(0) == ' ') c = c.substring(1);
	              if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
	          }
	          return "";
	      },
	      //清除cookie
	      clearCookie: function (cname) {
	          this.setCookie(cname, "", -1);
	      }
	},
	created:function(){
        this.adminId = this.getCookie("admin");
        this.getAdmins();
    }
});