function getRootpath() {
  var localObj = window.location;
  var contextPath = localObj.pathname.split("/")[1];
  return localObj.protocol + "//" + localObj.host;
}

function getVideolist(vm,page_vm,userId,pageno,searchWord,cateName,platform){
  $.getJSON(getRootpath()+"/home/videolist.do",{pageno: pageno,searchWord: searchWord,cateName: cateName,platform: platform},function(json){
     vm.videos=json.videos;
     page_vm.total=json.total;
     page_vm.pagesize=json.pagesize;
  });
  if(userId){
	  $.getJSON(getRootpath()+"/user/carelist.do",{userId: userId},function(json){
			vm.cares=json;
		});
  }
}

