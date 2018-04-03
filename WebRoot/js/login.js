var vm = new Vue({
    el:'#app',
    data:{
        phone:'',
        phoneL:'block',
        pwd:'',
        pwdL:'block',
        errorT:'none',
        error:'',
        btn:'登 录'
    },
    watch:{
        phone(){
            if(this.phone!=''){
                this.phoneL = 'none';
                this.errorT = 'none';
            }else{
                this.phoneL = 'block';
            }
        },
        pwd(){
            if(this.pwd!=''){
                this.pwdL = 'none';
                this.errorT = 'none';
            }else{
                this.pwdL = 'block';
            }
        }
    },
    methods:{
        login(){
            if(this.phone == ''){
                this.errorT = 'block';
                this.error = '请输入账号';
                return false;
            }
            if(this.pwd == ''){
                this.errorT = 'block';
                this.error = '请输入密码';
                return false;
            }
            var url = "/BookReading/servlet/AuthorServlet";
            var date = new URLSearchParams();
            date.append("method","login");
            date.append("phone",this.phone);
            date.append("password",this.pwd);
            axios.post(url,date)
            .then(response=>{
                if(response.data.success){
                    vm.btn = '正在登录...';
                    this.setCookie("author",this.phone,1);
                    var time = setTimeout(function(){
                        location.href = 'authorBook.jsp';
                    },1000);
                }else{
                	this.errorT = 'block';
                    this.error = response.data.msg;
                    return true;
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
        }
    }
});