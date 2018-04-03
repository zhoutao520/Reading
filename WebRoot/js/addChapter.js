var vm = new Vue({
    el:'#app',
    data:{
        id:'',
        size:'0',
        bookId:'',
        bookName:'',
        chapterId:'',
        chapterName:'',
        chapterNameT:'',
        chapterContent:''
    },
    watch:{
        chapterName:function(){
            this.check();
        },
        chapterContent:function(){
        	this.size = this.chapterContent.length;
        }
    },
    methods:{
        submit:function(){
        	if(this.check() && confirm("你确定提交吗？")){
	        	var url2 = "/BookReading/servlet/ChapterServlet";
	            var date2 = new URLSearchParams();
	            if(this.chapterId != ''){
		            date2.append("method","updateChapter");
		            date2.append("id",this.chapterId);
	            }else{
	            	date2.append("method","addChapter");
	            	date2.append("bookId",this.bookId);
	            }
	            date2.append("name",this.chapterName);
	            date2.append("wordNum",this.size);
	            date2.append("content",this.chapterContent);
	            axios.post(url2,date2)
	            .then(response=>{
	               if(response.data.success){	            	 
	            	   history.go(-1);location.reload();
	               }
	            })
	            .catch(error=>{
	                console.log(error);
	            })
        	}
        },
        check:function(){
            if(this.chapterName == ''){
                this.chapterNameT = '请输入章节名';
                return false;
            }else if(this.chapterName.length>20){
                this.chapterNameT = '章节名不能超过20个字';
                return false;
            }else{
                this.chapterNameT = '';
                return true;
            }
        },
        exit:function(){
            this.clearCookie("author");
            location.href = 'index.jsp';
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
        this.id = this.getCookie("author");
        this.bookId = this.GetQueryString("bookId");
        this.bookName = this.GetQueryString("bookName");
        this.chapterId = this.GetQueryString("chapterId");
        if(this.chapterId!=''){
        	var url2 = "/BookReading/servlet/ChapterServlet";
            var date2 = new URLSearchParams();
            date2.append("method","getChapter");
            date2.append("id",this.chapterId);
            date2.append("bookId",this.bookId);
            axios.post(url2,date2)
            .then(response=>{
               if(response.data.success){
            	   var data = response.data.rows;
            	   this.chapterName = data.name;
            	   this.chapterContent = data.content;
               }
            })
            .catch(error=>{
                console.log(error);
            })
        }
        
    }
});