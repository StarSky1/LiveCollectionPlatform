<%@ page  language="java" pageEncoding="UTF-8" %>
<nav id="pageNation" aria-label="Page navigation" class="col-md-6 col-md-offset-2 Page">
    <ul class="pagination pagination-lg">
        <li class="prePage" :class="{disabled: prePageDisabled}">
            <a href="#" aria-label="Previous" @click.prevent="prePage">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <li v-for="item in pages" :class="{active : item.isActive}">
            <a v-if="item.display==undefined" href="#" class="pagebtn"  @click.prevent="turnPage">{{ item.pageno }}</a>
            <a v-else class="disabledLink">···</a>
        </li>
        <li class="nextPage" :class="{disabled: nextPageDisabled}">
            <a href="#" aria-label="Next" @click.prevent="nextPage">
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
            prePageDisabled: true,
            nextPageDisabled: false
        },
        computed: {
            // 计算属性的 getter
            pages: function () {
                // `this` 指向 vm 实例
                var pages=[];
                var total_page=Math.ceil(this.total / this.pagesize);
                var show_size=10;
                if(total_page==1){
                    this.nextPageDisabled=true;
                }
                if(total_page<=show_size){
                    start=1;
                    end=total_page;
                }else{
                    start=1;
                    end=show_size;
                }
                if(total_page>show_size && this.pageno>end){
                    start=Math.floor(this.pageno/show_size)*show_size;
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
                    pages.push({display: false});
                    for(var i=total_page-2;i<=total_page;i++){  //显示最后三页
                        pages.push({pageno: i});
                    }
                }
                if(total_page>show_size && end>=total_page-2 && end<total_page){
                    for(var i=end+1;i<=total_page;i++){
                        pages.push({pageno: i});
                    }
                }
                if(end>=total_page){
                    var len=end-total_page; //多出的页数
                    pages.splice(-len,len);
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
                    this.prePageDisabled=true;
                    this.nextPageDisabled=false;
                }else if(this.pageno==total_page){
                    this.prePageDisabled=false;
                    this.nextPageDisabled=true;
                }else{
                    this.prePageDisabled=false;
                    this.nextPageDisabled=false;
                }
                getVideolist(vm,page_vm,"${currentUser.userId}",this.pageno,vm1.searchWord,vm.cateName,vm.platform);
            },
            prePage: function(event){
                if(this.prePageDisabled) return;
                var current=this.pageno-1;
                if(current==1){
                    $(".prePage").addClass("disabled");
                }
                $(".nextPage").removeClass("disabled");
                this.pageno=current;
                getVideolist(vm,page_vm,"${currentUser.userId}",current,vm1.searchWord,vm.cateName,vm.platform);
            },
            nextPage: function(event){
                if(this.nextPageDisabled) return;
                var current=this.pageno+1;
                var total_page=Math.ceil(this.total / this.pagesize);
                if(this.pageno==total_page){
                    $(".nextPage").addClass("disabled");
                }
                $(".prePage").removeClass("disabled");
                this.pageno=current;
                getVideolist(vm,page_vm,"${currentUser.userId}",current,vm1.searchWord,vm.cateName,vm.platform);
            }
        }
    });
</script>
