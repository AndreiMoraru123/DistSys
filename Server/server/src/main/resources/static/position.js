function getPositions() {
    var criteria = new Criteria();
    sendRequest("GET", "position?" + $.param(criteria), null, getPositionsSuccessHandler, null);
}

function Criteria() {
    var deviceId = $('#deviceId').val().trim(); // select data from input and trim it
    if (deviceId.length > 0) {
        this.terminalId = deviceId;
    }

    var startDate = $('#startDate').val().trim(); // select data from input and trim it
    if (startDate.length > 0) {
        this.startDate = startDate;
    }

    var endDate = $('#endDate').val().trim(); // select data from input and trim it
    if (endDate.length > 0) {
        this.endDate = endDate;
    }
}

function getPositionsSuccessHandler(respData) {

    let listResp = [];

    for(let i=0; i<respData.length; i++){
        listResp.push({lat: respData[i].latitude, lng: respData[i].longitude})
    }

    localStorage.setItem("responseData",JSON.stringify(listResp));


    /*    console.log(respData[0].latitude,respData[0].longitude);
        var myLatLng = {lat: respData[0].latitude, lng: respData[0].longitude};
        localStorage.setItem("myLat",myLatLng.lat);
        localStorage.setItem("myLong",myLatLng.lng);

        window.location = "map.html";*/

     window.location = "map.html";

}