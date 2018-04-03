var vm = new Vue({
    el:'#app',
    data:{
        id:'',
        bookId:'',
        chapters:[],
        chapter:'',
        content:'',
        cata:false,
        font:false,
        fontSize:'16px',
        shelf:''
    },
    methods:{
    	chap:function(id){
    		if(!id){
    			if(this.chapter.id!=this.chapters[0].id){
    				for(let i = 1;i<this.chapters.length;i++){
    					if(this.chapter.id==this.chapters[i].id){
    						this.getChap(this.chapters[i-1].id);
    						this.upshelf(this.chapters[i-1].id);
    						return;
    					}
    				}
    			}
    		}else{
    			if(this.chapter.id!=this.chapters[this.chapters.length-1].id){
    				for(let i = 0;i<this.chapters.length-1;i++){
    					if(this.chapter.id==this.chapters[i].id){
    						this.getChap(this.chapters[i+1].id);
    						this.upshelf(this.chapters[i+1].id);
    						return;
    					}
    				}
    			}
    		}
    	},
    	upshelf:function(id){
    		if(this.shelf!=''){
				var url = "/BookReading/servlet/CollectionServlet";
		        var date = new URLSearchParams();
		        date.append("method","updateCollection");
		        date.append("historyChapter",id);
		        date.append("id",this.shelf);
		        axios.post(url,date)
		        .then(response=>{
		        })
		        .catch(error=>{
		            console.log(error);
		        })
			}
    	},
    	getChap:function(chapterId){
    		var url = "/BookReading/servlet/ChapterServlet";
	        var date = new URLSearchParams();
	        date.append("method","getChapter");
	        date.append("bookId",this.bookId);
	        date.append("id",chapterId);
	        axios.post(url,date)
	        .then(response=>{
	        	if(response.data.success){
	        		this.chapter = response.data.rows;
	        		this.chapters = response.data.chapters;
	        		this.content= response.data.rows.content.replace(/\n/g,"</p><p>");
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
    	var author = this.getCookie("author");
    	var user = this.getCookie("user");
    	this.shelf = this.getCookie("shelf");
    	if(author!=''){
    		this.id = author;
    	}else if(user!=''){
    		this.id = user;
    	}
    	this.bookId = this.GetQueryString("bookId");
    	var chapterId = this.GetQueryString("chapterId");
    	this.getChap(chapterId);
    	this.upshelf(chapterId);
    }
});