var vm = new Vue({
    el:'#app',
    data:{
    	id:'',
    	size:0,
    	page:0,
        active:'',
        seactive:'',
        firstId:'',
        firstName:'',
        secondId:'',
        secondName:'',
        books:[],
        firstTypes:[],
        secondTypes:[{
        	id:'',
        	name:''
        }],
        flag:false,
        search:''
    },
    
    methods:{
    	exit:function(){
    		this.clearCookie("author");
    		this.clearCookie("user");
    		location.href = 'index.jsp';
    	},
    	bookInfo:function(id){
    		location.href = "bookInfo.jsp?bookId="+id;
    	},
    	author:function(){
    		if(this.getCookie("author")!=''){
    			location.href = "authorBook.jsp";
    		}else{
    			location.href = "login.jsp";
    		}
    		
    	},
    	getBook:function(sql){
    		var url2 = "/BookReading/servlet/BookServlet";
	        var date2 = new URLSearchParams();
	        date2.append("method","getBooks");
	        date2.append("sql",sql);
	        axios.post(url2,date2)
	        .then(response=>{
	           if(response.data.success){
	               this.books = response.data.rows;
	               this.size = response.data.total;
	           }
	        })
	        .catch(error=>{
	            console.log(error);
	        })
    	},
    	select:function(id){
    		if(this.flag){
    			this.active='';
    		}else{
    			this.active='';
    			this.getSecondType(id);
    			this.active=id;
    		}
    		this.flag=!this.flag;
    	},
    	getSecondType:function(id){
            var url = "/BookReading/servlet/SecondTypeServlet";
            var date={
                params:{
                    method:"getSecondTypes",
                    firstTypeId:id
                }
            }
            axios.get(url,date)
            .then(response=>{
                this.secondTypes = response.data.rows;
            })
            .catch(error=>{
                console.log(error);
            })
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
    	var url = "/BookReading/servlet/FirstTypeServlet";
        var date={
            params:{
                method:"getFirstTypes",
            }
        }
        axios.get(url,date)
        .then(response=>{
            this.firstTypes = response.data.rows;
        })
        .catch(error=>{
            console.log(error);
        })
        this.firstId = this.GetQueryString("firstId");
        this.firstName = this.GetQueryString("firstName");
        this.secondId = this.GetQueryString("secondId");
        this.secondName = this.GetQueryString("secondName");
        if(this.firstId!=''){
        	this.active = this.firstId;
        	if(this.secondId!=''){
        		this.seactive = this.secondId;
        		this.getSecondType(this.firstId);
        		this.getBook("SELECT *from bookview WHERE secondId="+this.secondId+" and state!=0 LIMIT "+this.page+",20;");
        	}else{
        		this.getBook("SELECT *from bookview WHERE firstId="+this.firstId+" and state!=0 LIMIT "+this.page+",20;");
        	}
        }else{
        	this.getBook("SELECT *from bookview WHERE state!=0 LIMIT "+this.page+",20");
        }
    }
});