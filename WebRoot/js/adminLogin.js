 new Vue({
  el: '#app',
  data: {
	  ruleForm: {
          name: '',
          pwd:''
        },
        rules: {
          name: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            { min: 4, max: 4, message: '账号有误', trigger: 'blur' }
          ],
          pwd:[
			{ required: true, message: '请输入密码', trigger: 'blur' },
			{ min: 6, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }  
          ]
        }
  },
  methods:{
	//设置cookie
      setCookie: function (cname, cvalue, exdays) {
          var d = new Date();
          d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
          var expires = "expires=" + d.toUTCString();
          document.cookie = cname + "=" + cvalue + "; " + expires;
      },
	  submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
        	  var url = "/BookReading/servlet/AdminServlet";
              var date = new URLSearchParams();
              date.append("method","login");
              date.append("id",this.ruleForm.name);
              date.append("password",this.ruleForm.pwd);
              axios.post(url,date)
              .then(response=>{
                  if(response.data.success){
                      this.setCookie("admin",this.ruleForm.name,1);
                      var time = setTimeout(function(){
                          location.href = 'admin.jsp';
                      },500);
                  }else{
                  	alert(response.data.msg);
                  }
              })
              .catch(error=>{
                  console.log(error);
              })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
  }
});