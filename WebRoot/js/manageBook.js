var vm = new Vue({
    el:'#app',
    data:{
        id:'',
        book:'',
        chapter:[],
        sign:'',
        edit:false,
        chaptersSize:'',
        cpage:0
    },
    methods:{
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
            		this.chapter = this.chapter.concat(response.data.rows);
            		this.chaptersSize = response.data.total;
            	}
            })
            .catch(error=>{
            	console.log(error);
            })
    	},
    	del:function(id,wordNum){
    		if(confirm("你确定删除该章吗？")){
    			var url = "/BookReading/servlet/ChapterServlet";
	            
	            var date = new URLSearchParams();
	            date.append('method','delChapter');
	            date.append('bookId',this.book.bookId);
	            date.append('id',id);
	            date.append('wordNum',wordNum);
	    		axios.post(url,date)
	            .then(response=>{
	            	location.reload();;
	            })
	            .catch(error=>{
	                console.log(error);
	            })
    		}
    	},
    	submit:function(){
    		if(confirm("你确定提交吗？")){
	        	var url = "/BookReading/servlet/BookServlet";
	            var date = new URLSearchParams();
	            date.append('method','updateBook');
	            date.append('label',this.book.label);
	            date.append('profile',this.book.profile);
	            date.append('cover',this.book.cover);
	            date.append('id',this.book.bookId);
	    		axios.post(url,date)
	            .then(response=>{
	            	location.reload();
	            })
	            .catch(error=>{
	                console.log(error);
	            })
    		}
        },
    	uploadCover:function(e){
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
            location.href = 'index.jsp';
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
        var id = this.getCookie("author");
        this.id = id;
        var s = this.GetQueryString("sign");
        s==1?this.sign=true:this.sign=false;
        var bookId = this.GetQueryString("bookId");
        var url2 = "/BookReading/servlet/BookServlet";
        var date2 = new URLSearchParams();
        date2.append("method","getBook");
        date2.append("bookId",bookId);
        axios.post(url2,date2)
        .then(response=>{
           if(response.data.success){
               this.book = response.data.rows;
           }
        })
        .catch(error=>{
            console.log(error);
        })
        var url = "/BookReading/servlet/ChapterServlet";
        var date = new URLSearchParams();
        date.append("method","getChapters");
        date.append("bookId",bookId);
        date.append("page",this.cpage);
        axios.post(url,date)
        .then(response=>{
        	if(response.data.success){
        		this.chapter = response.data.rows;
        		this.chaptersSize = response.data.total;
        	}
        })
        .catch(error=>{
        	console.log(error);
        })
    }
});