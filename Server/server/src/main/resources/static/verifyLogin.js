function VerifyLocalStorageIfExistUser() {

    var verify = localStorage.getItem("userConnected");
    console.log("da")
    if(verify == "true"){
        return false;
    }else
    {
        window.location = "aut.html";
    }
}