function getRootpath() {
  var localObj = window.location;
  var contextPath = localObj.pathname.split("/")[1];
  return localObj.protocol + "//" + localObj.host;
}

function getVideolist(vm,logined,pageno,searchWord,cateName,platform){
  $.getJSON(getRootpath()+"/home/videolist.do",{pageno: pageno,searchWord: searchWord,cateName: cateName,platform: platform},function(json){
     vm.videos=json.videos;
     vm.total=json.total;
     vm.pagesize=json.pagesize;
  });
  if(logined){
	  $.getJSON(getRootpath()+"/user/carelist.do",{userId: "${currentUser.userId}"},function(json){
			vm.cares=json;
		});
  }
}

