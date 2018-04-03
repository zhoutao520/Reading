 new Vue({
      el: '#app',
      data: {
    	  id:'',
    	  search:"",
    	  page:0,
    	  size:0,
    	  sort:0,
    	  books:[]
      },
      methods:{
    	  exit(){
    		  this.clearCookie("admin");
    		  location.href="../index.jsp";  
    	  },
    	  recommend(bookId,state){
    		  var url = "/BookReading/servlet/BookServlet";
	          var date = new URLSearchParams();
	          date.append("method","updateIsRecommend");
	          date.append("bookId",bookId);
	          date.append("state",state);
	          axios.post(url,date)
	          .then(response=>{
	          })
	          .catch(error=>{
	              console.log(error);
	          })
	          this.getBooks();
    	  },
    	  handleCommand(command){
    		  this.sort=command;
    		  this.getBooks();
    	  },
    	  searchBook(){
    		  this.page=0;
    		  this.getBooks();
    	  },
    	  handleSizeChange(val) {
    	     this.page = val-1;
    	     this.getBooks();
    	  },
	      handleCurrentChange(val) {
    		 this.page = val-1;
    		 this.getBooks();
	      },
	      getBooks(){
	    	  var url = "/BookReading/servlet/BookServlet";
	          var date = new URLSearchParams();
	          date.append("method","recommendBook");
	          date.append("size",this.page);
	          date.append("sort",this.sort);
	          date.append("search",this.search);
	          axios.post(url,date)
	          .then(response=>{
	             if(response.data.success){
	                 this.size = response.data.total;
	                 this.books = response.data.rows;
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
    	  this.getBooks();
      }
    });