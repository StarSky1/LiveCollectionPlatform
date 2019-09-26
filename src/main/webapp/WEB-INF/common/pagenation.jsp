<%@ page  language="java" pageEncoding="UTF-8" %>
<nav id="pageNation" aria-label="Page navigation" class="col-md-6 col-md-offset-2 Page">
    <ul class="pagination pagination-lg">
        <li class="disabled prePage">
            <a href="#" aria-label="Previous" @click.prevent="prePage">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <li v-for="item in pages" :class="{active : item.isActive}">
            <a v-if="item.display==undefined" href="#" class="pagebtn"  @click.prevent="turnPage">{{ item.pageno }}</a>
            <a v-else>···</a>
        </li>
        <li class="nextPage">
            <a href="#liveList" aria-label="Next" @click.prevent="nextPage">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>

    <form class="form-inline col-md-3">
        <div class="form-group">
            <label for="InputPage">第</label>
            <input type="text" class="form-control" id="InputPage" placeholder="几">
            <label>页</label>
        </div>
        <button type="submit" class="btn btn-success" @click.prevent="turnPage">go</button>
    </form>
</nav>

<script>
    var start,end;
    var page_vm=new Vue({
        el: "#pageNation",
        data: {
            total: 0,
            pagesize: 0,
            pageno: 1,
        },
        computed: {
            // 计算属性的 getter
            pages: function () {
                // `this` 指向 vm 实例
                var pages=[];
                var total_page=Math.ceil(this.total / this.pagesize);
                var show_size=10;
                if(total_page<show_size){
                    start=1;
                    end=total_page;
                }else{
                    start=1;
                    end=10;
                }
                if(total_page>show_size && this.pageno>=end){
                    start=Math.floor(this.pageno/10)*10-1;
                    end=start+show_size+1;
                    pages.push({pageno: 1});   //始终显示第一页
                    pages.push({display: false}); //在start前显示省略号
                }
                for(var i=start;i<=end;i++){
                    var obj={};
                    obj["pageno"]=i;
                    obj["isActive"]=false;
                    if(i==this.pageno){
                        obj["isActive"]=true;
                    }
                    pages.push(obj);
                }
                if(total_page>show_size && end<total_page-2){
                    pages.splice(end,0,{display: false});
                    for(var i=total_page-2;i<total_page;i++){
                        pages.push({pageno: i});
                    }
                    pages.push({pageno: total_page});  //始终显示最后一页
                }
                if(total_page>show_size && end>=total_page-2 && end<total_page){
                    for(var i=end+1;i<=total_page;i++){
                        pages.push({pageno: i});
                    }
                }
                if(end>=total_page){
                    pages.splice(total_page-start+3,end-total_page);
                }
                return pages;
            }
        },
        methods: {
            turnPage: function(event){
                if(event.target.tagName.toLowerCase()=="button"){
                    var pageno=parseInt($("#InputPage").val());
                }else{
                    var pageno=parseInt(event.target.innerText);
                }
                var total_page=Math.ceil(this.total / this.pagesize);
                if(pageno>total_page || pageno<1){
                    return;
                }
                this.pageno=pageno;
                if(this.pageno==1){
                    $(".prePage").addClass("disabled");
                    $(".nextPage").removeClass("disabled");
                }else if(this.pageno==total_page){
                    $(".nextPage").addClass("disabled");
                    $(".prePage").removeClass("disabled");
                }else{
                    $(".prePage").removeClass("disabled");
                    $(".nextPage").removeClass("disabled");
                }
                getVideolist(vm,vm1.logined,this.pageno,vm1.searchWord);
            },
            prePage: function(event){
                var current=this.pageno-1;
                if(current==1){
                    $(".prePage").addClass("disabled");
                }
                $(".nextPage").removeClass("disabled");
                this.pageno=current;
                getVideolist(this,vm1.logined,current,vm1.searchWord);
            },
            nextPage: function(event){
                var current=this.pageno+1;
                var total_page=Math.ceil(this.total / this.pagesize);
                if(this.pageno==total_page){
                    $(".nextPage").addClass("disabled");
                }
                $(".prePage").removeClass("disabled");
                this.pageno=current;
                getVideolist(this,vm1.logined,current,vm1.searchWord);
            }
        }
    });
</script>
