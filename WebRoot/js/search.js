var vm = new Vue({
    el:'#app',
    data:{
    	id:'',
    	size:0,
    	bookSize:'',
        books:[],
        search:'',
    },
    
    methods:{
    	exit:function(){
    		this.clearCookie("author");
    		this.clearCookie("user");
    		location.href = 'index.jsp';
    	},
    	load:function(){
    		this.size++;
        	this.getBook();
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
    	getBook:function(){
    		var url2 = "/BookReading/servlet/BookServlet";
	        var date2 = new URLSearchParams();
	        date2.append("method","getSearchBooks");
	        date2.append("size",this.size);
	        date2.append("search",this.search);
	        axios.post(url2,date2)
	        .then(response=>{
	           if(response.data.success){
	               this.books = this.books.concat(response.data.rows);
	               this.bookSize = response.data.total;
	               this.books.forEach(book=>{
	            	   if(book.wordNum>10000){
		            	   book.wordNum/=10000;
		            	   book.wordNum+='万';
		               }
		               if(book.clickNum>10000){
		            	   book.clickNum/=10000;
		            	   book.clickNum+='万';
		               }
		               if(book.collectionNum>10000){
		            	   book.collectionNum/=10000;
		            	   book.collectionNum+='万';
		               }
	               })	
	           }
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
    	this.search = this.GetQueryString("search");
    	this.getBook();
    }
});