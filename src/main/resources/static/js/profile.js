const y = document.getElementById("address");
const x = document.getElementById("lat");
const z = document.getElementById("lon");
var lat;
var lng;


document.getElementById("getAddress").addEventListener("click", () => {
    getLocation();
});

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.watchPosition(showPosition);
    } else {
        window.alert("Geolocation is not supported by this browser.");
    }
}

function showPosition(position) {
    x.value = lat;
    z.value = lng;
    lat = position.coords.latitude;
    lng = position.coords.longitude;
    getaddress();
}

function getaddress() {


    const url = "https://geocode.maps.co/reverse?lat=" + lat + "&lon=" + lng + "&api_key=65fa8ee090481811229554ypa955afb";

    fetch(url)
        .then(response => response.json())
        .then(data => y.value = data.display_name )
        .catch(error => console.error('Error fetching JSON:', error));


}