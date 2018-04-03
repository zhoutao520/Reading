var vm = new Vue({
    el:'#app',
    data:{
    	id:'',
        search:'',
        type:1,
        name:'',
        head:'',
        sex:'',
        realName:'',
        IDCard:'',
        profile:'',
        password:'',
        oldpassword:'',
        newpassword:'',
        renewpassword:'',
        certification:false,
        check:false,
        passwordCheck:0
    },
    watch:{
    	oldpassword:function(){
    		this.password!=this.oldpassword?this.passwordCheck=1:this.passwordCheck=0;
    	},
    	newpassword:function(){
    		var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
    		if(reg.test(this.newpassword)){
    			this.passwordCheck=0;
    		}else{
    			this.passwordCheck=2;
    		}
    	},
    	renewpassword:function(){
    		this.newpassword!=this.renewpassword?this.passwordCheck=3:this.passwordCheck=0;
    	}
    },
    methods:{
    	submitPwd(){
    		if(this.password!=this.oldpassword){
    			this.passwordCheck=1;
    			return false;
    		}
    		if( this.newpassword==''){
    			this.passwordCheck=2;
    			return false;
    		} 
    		if( this.newpassword!=this.renewpassword){
    			this.passwordCheck=3;
    			return false;
    		}
    		var url = "/BookReading/servlet/UserServlet";
            var date = new URLSearchParams();
            date.append("method","editPassword");
            date.append("phone",this.id);
            date.append("password",this.newpassword);
            axios.post(url,date)
            .then(response=>{
            	if(response.data.success){
            		this.clearCookie("author");
            		this.clearCookie("user");
                    location.href = 'index.jsp';
            	}else{
            		alert("修改失败");
            	}
            })
            .catch(error=>{
                console.log(error);
            })
    	},
    	submit(){
    		var url = "/BookReading/servlet/UserServlet";
            var date = new URLSearchParams();
            date.append("method","updateUser");
            date.append("phone",this.id);
            date.append("name",this.name);
            date.append("head",this.head);
            date.append("sex",this.sex);
            date.append("realName",this.realName);
            date.append("profile",this.profile);
            date.append("IDcard",this.IDCard);
            axios.post(url,date)
            .then(response=>{
            	if(response.data.success){
            		location.reload();
            	}else{
            		alert("保存失败");
            	}
            })
            .catch(error=>{
                console.log(error);
            })
    	},
    	uploadHead(e){
    		var files = e.target.files || e.dataTransfer.files;          
            var url = "http://upload.qiniu.com";
            var tokenUrl = 'bGvVcdihGtNWPpWxXuE_7xg1Rkg2l8DBE1zivbsn:p7dtwW7ej9vGnP_1JPtcMT-pptY=:eyJzY29wZSI6ImJvb2tyZWFkaW5nIiwiZGVhZGxpbmUiOjE1NDA0NzcxMzZ9';
            var date = (new Date()).valueOf();
            var formData = new FormData();

            formData.append('key',date);
            formData.append('token', tokenUrl);
            formData.append('file', files[0]);
           
            axios.post(url,formData,{headers:{'Content-Type':'multipart/form-data'}})
            .then(response=>{
                this.cover = "http://oyn1b4wss.bkt.clouddn.com/"+date;
            })
            .catch(error=>{
                console.log(error);
            })
    	},
    	exit:function(){
    		this.clearCookie("author");
    		this.clearCookie("user");
    		location.href = 'index.jsp';
    	},
    	author:function(){
    		if(this.getCookie("author")!=''){
    			location.href = "authorBook.jsp";
    		}else{
    			location.href = "login.jsp";
    		}
    		
    	},

        //获取url参数
        GetQueryString: function (name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  decodeURI(r[2]); return '';
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
    	var author = this.getCookie("author");
    	var user = this.getCookie("user");
    	if(author!=''){
    		this.id = author;
    	}else if(user!=''){
    		this.id = user;
    	}
    	var url = "/BookReading/servlet/UserServlet";
        var date = new URLSearchParams();
        date.append("method","getUser");
        date.append("phone",this.id);
        axios.post(url,date)
        .then(response=>{
        	response.data.rows.name!=undefined && (this.name = response.data.rows.name);
        	response.data.rows.sex!=undefined && (this.sex = response.data.rows.sex);
        	response.data.rows.head!=undefined && (this.head = response.data.rows.head);
        	response.data.rows.realName!=undefined && (this.realName = response.data.rows.realName);
        	response.data.rows.iDcard!=undefined && (this.IDCard = response.data.rows.iDcard);
        	response.data.rows.profile!=undefined && (this.profile = response.data.rows.profile);
        	response.data.rows.iDcard!=undefined && response.data.rows.realName!=undefined && (this.certification = true);
        	this.password = response.data.rows.password;
        })
        .catch(error=>{
            console.log(error);
        })
        var user = '15298368810';
        var password2 = 'cq161016';
        var data = {
        	"emailOrUsernameOrPhone": user,
        	"password": password2	
        }
        axios.post('http://106.15.73.22:8080/exhibition-api/api/members/login',data)
        .then(response=>{
        	console.log(response)
        })
        .catch(error=>{
            console.log(error);
        })
    }
});
