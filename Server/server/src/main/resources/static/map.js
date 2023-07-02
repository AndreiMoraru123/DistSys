var map;
var myLatLng = {lat: 46.7693924, lng: 23.5902006};

function initialize() {
    var mapCanvas = document.getElementById('map');
    var mapOptions = {
        center: new google.maps.LatLng(myLatLng),
        zoom: 8,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }

    map = new google.maps.Map(mapCanvas, mapOptions)
}

function addMarker() {

    var responseObj = localStorage.getItem("responseData")
    console.log(responseObj)

    var jsonData = JSON.parse(responseObj)

    console.log(jsonData)

    console.log(jsonData.length)

    for (var i = 0; i < jsonData.length; i++) {
        var myLatLong = jsonData[i]
        console.log(myLatLong);

        let longitude = parseFloat(myLatLong.lng)
        let latitude = parseFloat(myLatLong.lat)


        var marker = new google.maps.Marker({
            position: {lat: latitude, lng: longitude} ,
            map: map,
            title: 'Hello!'
        });
    }


}

/*
function getPosition(){
    var randLatLng = {lat: (myLatLng["lat"] + Math.floor(Math.random() * 5) + 1),
        lng: (myLatLng["lng"] + Math.floor(Math.random() * 5) + 1)};
    return randLatLng;
}

 */