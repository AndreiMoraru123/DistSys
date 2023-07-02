function validate() {

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if ( username == "admin" && password == "admin"){
        localStorage.setItem("userConnected",true);
        window.location = "index.html";
        return false;
    }
    else {
        window.location = "aut.html";
    }

    // window.location = "map.html";
    return false;

}