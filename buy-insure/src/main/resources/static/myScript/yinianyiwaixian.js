function searchTerms(item_id) {
    //alert(item_id);
    var f=document.createElement("form");
    f.action="http://localhost:8899/buy/searchTerms/" + item_id;
    f.target="_blank";
    f.method="post";
    document.body.appendChild(f);
    f.submit();
}