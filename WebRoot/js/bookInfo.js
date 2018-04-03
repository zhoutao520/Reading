var vm = new Vue({
    el:'#app',
    data:{
    	id:'',
        book:'',
        label:[],
        chapters:[],
        comments:[],
        search:'',
        chaptersSize:'',
        commentSize:'',
        login:false,
        shelf:false,
        cpage:0,
        copage:0,
        comContent:''
    },
    methods:{
    	exit:function(){
    		this.clearCookie("author");
    		this.clearCookie("user");
    		location.href = 'index.jsp';
    	},
    	loadCom:function(){
    		this.copage++;
    		var url2 = "/BookReading/servlet/ChapterServlet";
            var date2 = new URLSearchParams();
            date2.append("method","getComments");
            date2.append("bookId",this.book.bookId);
            date2.append("page",this.copage);
            axios.post(url2,date2)
            .then(response=>{
            	if(response.data.success){
            		this.comments = this.comments.concat(response.data.rows);
            		this.commentSize = response.data.total;
            	}
            })
            .catch(error=>{
            	console.log(error);
            })
    	},
    	delComent:function(id){
    		var url = "/BookReading/servlet/CommentServlet";
            var date = new URLSearchParams();
            date.append("method","delComment");
            date.append("bookId",this.book.bookId);
            date.append("id",id);
            axios.post(url,date)
            .then(response=>{
            	if(response.data.success){
            		this.comments = response.data.rows;
            		this.commentSize = response.data.total;
            	}
            })
            .catch(error=>{
                console.log(error);
            })
    	},
    	likeComent:function(id){
    		var url = "/BookReading/servlet/CommentServlet";
    		var date = new URLSearchParams();
    		date.append("method","updateComment");
    		date.append("bookId",this.book.bookId);
    		date.append("id",id);
    		axios.post(url,date)
    		.then(response=>{
    			if(response.data.success){
    				this.comments = response.data.rows;
    				this.commentSize = response.data.total;
    			}
    		})
    		.catch(error=>{
    			console.log(error);
    		})
    	},
    	comment:function(){
    		if(this.comContent!=''&&this.comContent!=" "){
	    		var url = "/BookReading/servlet/CommentServlet";
	            var date = new URLSearchParams();
	            date.append("method","addComment");
	            date.append("bookId",this.book.bookId);
	            date.append("reviewerId",this.id);
	            date.append("content",this.comContent);
	            axios.post(url,date)
	            .then(response=>{
	            	if(response.data.success){
	            		this.comments = response.data.rows;
	            		this.commentSize = response.data.total;
	            		this.comContent = '';
	            	}
	            })
	            .catch(error=>{
	                console.log(error);
	            })
    		}
    	},
    	Recommend:function(){
    		var url = "/BookReading/servlet/BookServlet";
            var date = new URLSearchParams();
            date.append("method","updateRecommendNum");
            date.append("bookId",this.book.bookId);
            axios.post(url,date)
            .then(response=>{
            	
            })
            .catch(error=>{
                console.log(error);
            })
    	},
    	load:function(){
    		this.cpage++;
    		var url2 = "/BookReading/servlet/ChapterServlet";
            var date2 = new URLSearchParams();
            date2.append("method","getChapters");
            date2.append("bookId",this.book.bookId);
            date2.append("page",this.cpage);
            axios.post(url2,date2)
            .then(response=>{
            	if(response.data.success){
            		this.chapters = this.chapters.concat(response.data.rows);
            		this.chaptersSize = response.data.total;
            	}
            })
            .catch(error=>{
            	console.log(error);
            })
    	},
    	bookRead:function(id){
    		open("bookRead.jsp?bookId="+this.book.bookId+"&chapterId="+id);
    	},
    	addSub:function(){
    		if(this.id!=''){
    			var url = "/BookReading/servlet/CollectionServlet";
    	        var date = new URLSearchParams();
    	        date.append("method","addCollection");
    	        date.append("userId",this.id);
    	        date.append("bookId",this.book.bookId);
    	        axios.post(url,date)
    	        .then(response=>{
    	        	   this.shelf = true;
    	        })
    	        .catch(error=>{
    	            console.log(error);
    	        })
    		}else{
    			this.login = true;
    		}
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
    	var bookId = this.GetQueryString("bookId");
    	var url = "/BookReading/servlet/BookServlet";
        var date = new URLSearchParams();
        date.append("method","getBook");
        date.append("bookId",bookId);
        axios.post(url,date)
        .then(response=>{
           if(response.data.success){
               this.book = response.data.rows;
               this.label = this.book.label.split(",");
               if(this.book.wordNum>10000){
            	   this.book.wordNum/=10000;
            	   this.book.wordNum+='万';
               }
               if(this.book.clickNum>10000){
            	   this.book.clickNum/=10000;
            	   this.book.clickNum+='万';
               }
           }
        })
        .catch(error=>{
            console.log(error);
        })
        var url2 = "/BookReading/servlet/ChapterServlet";
        var date2 = new URLSearchParams();
        date2.append("method","getChapters");
        date2.append("bookId",bookId);
        date2.append("page",this.cpage);
        axios.post(url2,date2)
        .then(response=>{
        	if(response.data.success){
        		this.chapters = response.data.rows;
        		this.chaptersSize = response.data.total;
        	}
        })
        .catch(error=>{
        	console.log(error);
        })
        if(this.id!=''){
        	var url3 = "/BookReading/servlet/CollectionServlet";
        	var date3 = new URLSearchParams();
        	date3.append("method","check");
        	date3.append("bookId",bookId);
        	date3.append("userId",this.id);
        	axios.post(url3,date3)
        	.then(response=>{
        			this.shelf = response.data.flag;
        	})
        	.catch(error=>{
        		console.log(error);
        	})
        }
    	var url4 = "/BookReading/servlet/CommentServlet";
        var date4 = new URLSearchParams();
        date4.append("method","getComments");
        date4.append("bookId",bookId);
        date4.append("page",this.copage);
        axios.post(url4,date4)
        .then(response=>{
        	if(response.data.success){
        		this.comments = response.data.rows;
        		this.commentSize = response.data.total;
        	}
        })
        .catch(error=>{
            console.log(error);
        })
    }
});