function getRootpath() {
  var localObj = window.location;
  var contextPath = localObj.pathname.split("/")[1];
  return localObj.protocol + "//" + localObj.host + "/" + contextPath;
}

function getVideolist(vm,pageno,cateName,platform){
  $.getJSON(getRootpath()+"/home/videolist.do",{pageno: pageno,cateName: cateName,platform: platform},function(json){
     vm.videos=json.videos;
     vm.total=json.total;
     vm.pagesize=json.pagesize;
  });
}
