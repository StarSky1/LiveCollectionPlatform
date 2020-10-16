<%@ page  language="java" pageEncoding="UTF-8" %>
<footer>
    Copyright &copy;&nbsp;2018-{{nowYear}} 杨靖 All Rights Reserved
</footer>

<script>
    var footer_vm=new Vue({
        el: document.getElementsByTagName("footer")[0],
        data: {},
        computed: {
            nowYear: function(){
                return new Date().getFullYear();
            }
        }
    });

</script>