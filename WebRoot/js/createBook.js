var vm = new Vue({
    el:"#createBook",
    data:{
    	authorId:'',
        name:"",
        cover:"../img/wgd-btn-6.png",
        firstTypes:[],
        secondTypes:[],
        firstTypeId:"",
        secondTypeId:"",
        label:'',
        profile:'',
        size:'0',
        advice:''
    },
    watch:{
    	name:function(){
    		var url = "/BookReading/servlet/BookServlet";
            
            var date = new URLSearchParams();
            date.append("method","checkBook");
            date.append("name",this.name);
    		axios.post(url,date)
            .then(response=>{
            	if(response.data.success){
            		this.advice = response.data.msg;
            	}else{
            		this.advice = '';
            	}
            })
            .catch(error=>{
                console.log(error);
            })
    	},
        firstTypeId:function(){
            this.getSecondType(this.firstTypeId);
        },
    	profile:function(){
    		this.size = this.profile.length;
    	}
    },
    methods:{
    	exit:function(){
            this.clearCookie("author");
            location.href = 'index.jsp';
        },
        submit(){
        	var url = "/BookReading/servlet/BookServlet";
            
            var date = new URLSearchParams();
            date.append('method','addBooks');
            date.append('name', this.name);
            date.append('label',this.label);
            date.append('typeId',this.secondTypeId);
            date.append('profile',this.profile);
            date.append('cover',this.cover);
            date.append('authorId',this.authorId);
    		axios.post(url,date)
            .then(response=>{
            	location.href = 'authorBook.jsp';
            })
            .catch(error=>{
                console.log(error);
            })
        },
        uploadCover(e){
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
        getFirstType(){
            var url = "/BookReading/servlet/FirstTypeServlet";
            var date={
                params:{
                    method:"getFirstTypes",
                }
            }
            axios.get(url,date)
            .then(response=>{
                this.firstTypes = response.data.rows;
                this.firstTypeId = response.data.rows[0].id;
            })
            .catch(error=>{
                console.log(error);
            })
        },
        getSecondType(id){
            var url = "/BookReading/servlet/SecondTypeServlet";
            var date={
                params:{
                    method:"getSecondTypes",
                    firstTypeId:id
                }
            }
            axios.get(url,date)
            .then(response=>{
                this.secondTypes = response.data.rows;
                this.secondTypeId = this.secondTypes[0].id;
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
        }
       
    },
    created:function(){

    },
    mounted:function(){
    	this.authorId = this.getCookie("author");
        this.getFirstType();
    }
});