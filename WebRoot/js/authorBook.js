var vm = new Vue({
    el:'#app',
    data:{
        id:'',
        size:'',
        books:''
    },
    methods:{
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
        var url2 = "/BookReading/servlet/BookServlet";
        var date2 = new URLSearchParams();
        date2.append("method","getBooks");
        date2.append("sql","select * from bookview where authorId="+id);
        axios.post(url2,date2)
        .then(response=>{
           if(response.data.success){
               this.size = response.data.total;
               this.books = response.data.rows;
           }
        })
        .catch(error=>{
            console.log(error);
        })
    }
});