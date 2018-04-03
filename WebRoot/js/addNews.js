 new Vue({
  el: '#app',
  data: {
	  id:'',
    ruleForm: {
      title: '',
      content: '',
      imageUrl:''
    },
    rules: {
      title: [
        { required: true, message: '请输入标题', trigger: 'blur' },
        { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
      ],
      region: [
        { required: true, message: '请选择资讯类型', trigger: 'change' }
      ],
      imageUrl: [
        { required: true, message: '请上传图片', trigger: 'change' }
      ],
      content: [
        { required: true, message: '请填写资讯内容', trigger: 'blur' }
      ]
    },
    postData: {
        token: 'bGvVcdihGtNWPpWxXuE_7xg1Rkg2l8DBE1zivbsn:p7dtwW7ej9vGnP_1JPtcMT-pptY=:eyJzY29wZSI6ImJvb2tyZWFkaW5nIiwiZGVhZGxpbmUiOjE1NDA0NzcxMzZ9'
    },
  },
  methods:{
	  exit(){
		  this.clearCookie("admin");
		  location.href="../index.jsp";  
	  },
	  handleAvatarSuccess(res, file) {   //上传成功后在图片框显示图片
        this.ruleForm.imageUrl ='http://oyn1b4wss.bkt.clouddn.com/'+ res.key;
        console.log(res);
	  },
	  submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
        	  var url = "/BookReading/servlet/NoticeServlet";
        	  var date = new URLSearchParams();
	          date.append("method","addNotice");
	          date.append("adminId",this.getCookie("admin"));
	          date.append("title",this.ruleForm.title);
	          date.append("content",this.ruleForm.content);
	          date.append("photo",this.ruleForm.imageUrl);
              axios.post(url,date)
              .then(response=>{
                  location.href="news.jsp";
              })
              .catch(error=>{
                  console.log(error);
              })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
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
  }
});