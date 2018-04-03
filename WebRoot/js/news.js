 new Vue({
      el: '#app',
      data: {
    	  id:'',
    	  search:"",
    	  page:0,
    	  size:0,
    	  news:[]
      },
      methods:{
    	  exit(){
    		  this.clearCookie("admin");
    		  location.href="../index.jsp";  
    	  },
    	  add(){
    		location.href="addNews.jsp";  
    	  },
    	  searchNews(){
    		  this.page=0;
    		  this.getNews();
    	  },
    	  handleDelete:function(id){
    		  var url = "/BookReading/servlet/NoticeServlet";
	          var date = new URLSearchParams();
	          date.append("method","delNotice");
	          date.append("id",id);
	          axios.post(url,date)
	          .then(response=>{
	          })
	          .catch(error=>{
	              console.log(error);
	          })
	          this.getNews();
    	  },
    	  handleSizeChange(val) {
    	     this.page = val-1;
    	     this.getNews();
    	  },
	      handleCurrentChange(val) {
    		 this.page = val-1;
    		 this.getNews();
	      },
	      getNews(){
	    	  var url = "/BookReading/servlet/NoticeServlet";
	          var date = new URLSearchParams();
	          date.append("method","getNotices");
	          date.append("size",this.page);
	          axios.post(url,date)
	          .then(response=>{
	             if(response.data.success){
	                 this.size = response.data.total;
	                 this.news = response.data.rows;
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
    	  this.id = this.getCookie("admin");
    	  this.getNews();
      }
    });