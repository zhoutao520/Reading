var vm = new Vue({
    el:"#reg",
    data:{
        act:true,
        codeFlag:true,
        phone:'',
        code:'',
        proveCode:'',
        pwd:'',
        repwd:'',
        getCodeMsg:'获取验证码',
        sec:3,
        error:[{
            flag:false,
            phone:''
        },
        {
            flag:false,
            code:''
        },
        {
            flag:false,
            pwd:''
        },
        {
            flag:false,
            repwd:''
        }]
    },
    watch:{
        phone(){
            this.checkPhone();
        },
        code(){
            this.checkCode();
        },
        pwd(){
            this.checkPwd();
        },
        repwd(){
            this.checkRepwd();
        },
        act(){
            var time = setInterval(function(){
                if(--vm.sec<=1){
                    clearInterval(time);
                    location.href = 'index.jsp';
                }
            },1000);
        }
    },
    methods:{
        getCode(){
        	if(this.checkPhone){
              var url = "/BookReading/servlet/UserServlet";
              var date = new URLSearchParams();
              date.append("method","checkPhone");
              date.append("phone",this.phone);
              axios.post(url,date)
              .then(response=>{
                  if(!response.data.success){
                      this.error[0].flag=true;
                      this.error[0].phone='手机号已被注册';
                      return false;
                  }
                  var index = 10;
                  this.codeFlag = false;
                  var url2 = "/BookReading/servlet/codeServlet";
                  var nonce = Math.round(Math.random()*10000);
                  var date2 = new URLSearchParams();
                  date2.append("phone",this.phone);
                  date2.append("nonce",nonce);
                  axios.post(url2,date2)
                  .then(response=>{
                  	this.proveCode = response.data.obj;
                  	var time = setInterval(function(){
                  		if(index<=0){
                  			vm.codeFlag = true;
                  			vm.getCodeMsg = '获取验证码';
                  			clearInterval(time);
                  		}else{
                  			vm.getCodeMsg = '('+index+')重新发送';
                  			index--;
                  		}
                  	},1000);
                  })
                  .catch(error=>{
                      console.log(error);
                  }) 
                  
              })
              .catch(error=>{
                  console.log(error);
              })
                
            }
        },
        submit(){
            if(this.checkPhone()&&this.checkCode()&&this.checkPwd()&&this.checkRepwd()){
                var url = "/BookReading/servlet/UserServlet";
                var date = new URLSearchParams();
                date.append("method","addUser");
                date.append("phone",this.phone);
                date.append("password",this.pwd);
                axios.post(url,date)
                .then(response=>{
                    this.act = false;
                    this.setCookie("user",this.phone,1);
                })
                .catch(error=>{
                    console.log(error);
                })
            }
        },
        checkPhone(){
            if(this.phone==''||this.phone==' '){
                this.error[0].flag=true;
                this.error[0].phone='请输入手机号';
                return false;
            }else if(!(/^1\d{10}$/.test(this.phone))){
                this.error[0].flag=true;
                this.error[0].phone='手机号输入有误';
                return false;
            }
            return true;
        },
        checkCode(){
            if(this.code==''||this.code==' '){
                this.error[1].flag=true;
                this.error[1].code='请输入验证码';
                return false;
            }else if(this.proveCode!=this.code){
                this.error[1].flag=true;
                this.error[1].code='验证码输入错误';
                return false;
            }else{
                this.error[1].flag=false;
                this.error[1].code='';
                return true;
            }
        },
        checkPwd(){
            if(this.pwd==''||this.pwd==' '){
                this.error[2].flag=true;
                this.error[2].pwd='请输入密码';
                return false;
            }else if(!(/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test(this.pwd))){
            	this.error[2].flag=true;
                this.error[2].pwd='6-18位英文字母及数字';
                return false;
            }
            	this.error[2].flag=false;
                this.error[2].pwd='';
                return true;
        },
        checkRepwd(){
            if(this.repwd==''||this.repwd==' '){
                this.error[3].flag=true;
                this.error[3].repwd='请再次输入密码';
                return false;
            }else if(this.repwd!=this.pwd){
                this.error[3].flag=true;
                this.error[3].repwd='您两次输入的密码不一致';
                return false;
            }else{
                this.error[3].flag=false;
                this.error[3].repwd='';
                return true;
            }
        },
         //设置cookie
        setCookie: function (cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + d.toUTCString();
            document.cookie = cname + "=" + cvalue + "; " + expires;
        }
    }
});