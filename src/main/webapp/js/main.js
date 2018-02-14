function getRootpath() {
  var localObj = window.location;
  var contextPath = localObj.pathname.split("/")[1];
  return localObj.protocol + "//" + localObj.host + "/" + contextPath;
}
