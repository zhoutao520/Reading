var vm = new Vue({
    el:'#app',
    data:{
    	id:'',
        search:'',
        mark:0,
        timer:'',
        editbooks:[],
        userbooks:[],
        hotbooks:[],
        clickbooks:[],
        newbooks:[],
        randbooks:[],
        imgArray:[],
    },
    methods:{
    	rand:function(){
    		var url = "/BookReading/servlet/BookServlet";
            var date = new URLSearchParams();
            date.append("method","randBook");
            axios.post(url,date)
            .then(response=>{
                if(response.data.success){
                    this.randbooks=response.data.randbooks;
                    this.updateNum(this.randbooks);
                }
            })
            .catch(error=>{
                console.log(error);
            })
    	},
    	cata:function(id,name){
    		location.href = "category.jsp?firstId="+id+"&firstName="+name;
    	},
    	autoPlay:function(){
    		this.mark++;
    		if(this.mark === 4){
    			this.mark = 0;
    		}
    	},
    	play:function(){
    		this.timer = setInterval(this.autoPlay,3000);
    	},
    	change:function(i){
    		this.mark = i;
    	},
    	stop:function () {  
    	      clearInterval(this.timer);  
    	},  
    	move:function () {  
    	      this.timer = setInterval(this.autoPlay, 3000);
    	},
    	exit:function(){
    		this.clearCookie("author");
    		this.clearCookie("user");
    		location.href = 'index.jsp';
    	},
    	updateNum:function(book){
    		book.forEach(item=>{
            	if(item.wordNum>10000){
            		item.wordNum/=10000;
            		item.wordNum+='万';
            	}
            })
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
            if(r!=null)return  unescape(r[2]); return null;
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
    	this.play();
        var author = this.getCookie("author");
    	var user = this.getCookie("user");
    	if(author!=''){
    		this.id = author;
    	}else if(user!=''){
    		this.id = user;
    	}
    	var url = "/BookReading/servlet/BookServlet";
        var date = new URLSearchParams();
        date.append("method","indexBook");
        axios.post(url,date)
        .then(response=>{
            if(response.data.success){
                this.editbooks=response.data.editbooks;
                this.userbooks=response.data.userbooks;
                this.hotbooks=response.data.hotbooks;
                this.clickbooks=response.data.clickbooks;
                this.newbooks=response.data.newbooks;
                this.randbooks=response.data.randbooks;
                this.imgArray = response.data.notices;
                this.updateNum(this.editbooks);
                this.updateNum(this.userbooks);
                this.updateNum(this.hotbooks);
                this.updateNum(this.clickbooks);
                this.updateNum(this.newbooks);
                this.updateNum(this.randbooks);
            }
        })
        .catch(error=>{
            console.log(error);
        })
    }
});