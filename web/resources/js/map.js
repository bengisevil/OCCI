/* 
 * Created by Mahek Mehta on 2018.12.02  * 
 * Copyright © 2018 Mahek Mehta. All rights reserved. * 
 * Adapted from Google Places API sample *
 */

var map, places, infoWindow;
var markers = [];
var autocomplete;
var countryRestrict = {'country': 'us'};

var countries = {
    'ca': {//Canada
        center: {lat: 62, lng: -110.0},
        zoom: 4
    },
    'mx': {//Mexico
        center: {lat: 23.6, lng: -102.5},
        zoom: 4
    },
    'us': {//United States - Default
        center: {lat: 37.1, lng: -95.7},
        zoom: 4
    },
};

/**
 * Initializes map with the United States at center
 */
function createMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: countries['us'].zoom,
        center: countries['us'].center,
        mapTypeControl: false,
        panControl: false,
        zoomControl: false,
        streetViewControl: false
    });

    infoWindow = new google.maps.InfoWindow({
        content: document.getElementById('info-content')
    });

    //only allows cities to be inserted
    autocomplete = new google.maps.places.Autocomplete(
            /** @type {!HTMLInputElement} */ (
                    document.getElementById('autocomplete')), {
        types: ['(cities)'],
        componentRestrictions: countryRestrict
    });
    places = new google.maps.places.PlacesService(map);

    autocomplete.addListener('place_changed', onSubmit);
    //checks if user changes country from drop down
    document.getElementById('country').addEventListener(
            'change', setCountry);
}

/**
 * Called when user selects a city from the autocomplete 
 * dropdown. 
 */
function onSubmit() {
    var place = autocomplete.getPlace();
    if (place.geometry) {
        map.panTo(place.geometry.location);
        map.setZoom(13);
        search();
    } else {
        document.getElementById('autocomplete').placeholder = 'Enter a city';
    }
}

// Restrict search to search for drug addiction centers
function search() {
    var search = {
        bounds: map.getBounds(),
        keyword: ['Drug Addiction Clinic']
    };

    places.nearbySearch(search, function (results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            clearResults(); //clears old results
            clearMarkers();
            //Creates a marker for every result and assigns it a letter
            for (var i = 0; i < results.length; i++) {
                var markerLetter = String.fromCharCode('A'.charCodeAt(0) + (i % 26));
                var markerIcon = 'https://developers.google.com/maps/documentation/javascript/images/marker_green' + markerLetter + '.png';
                //Adds markers to map
                markers[i] = new google.maps.Marker({
                    position: results[i].geometry.location,
                    animation: google.maps.Animation.DROP,
                    icon: markerIcon
                });
                //Shows detail window if user clicks on marker
                markers[i].placeResult = results[i];
                google.maps.event.addListener(markers[i], 'click', showInfoWindow);
                setTimeout(dropMarker(i), i * 100);
                addResult(results[i], i);
            }
        }
    });
}
//Clears all markers
function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        if (markers[i]) {
            markers[i].setMap(null);
        }
    }
    markers = [];
}

//Adds country restricition
function setCountry() {
    var country = document.getElementById('country').value;
    autocomplete.setComponentRestrictions({'country': country});
    map.setCenter(countries[country].center);
    map.setZoom(countries[country].zoom);

    clearResults();
    clearMarkers();
}

//Drops markers
function dropMarker(i) {
    return function () {
        markers[i].setMap(map);
    };
}
/**
 * Adds search result to map with the next letter marker
 */
function addResult(result, i) {
    var results = document.getElementById('results');
    var markerLetter = String.fromCharCode('A'.charCodeAt(0) + (i % 26));
    var markerIcon = 'https://developers.google.com/maps/documentation/javascript/images/marker_green' + markerLetter + '.png';

    var tr = document.createElement('tr');
    tr.style.backgroundColor = (i % 2 === 0 ? '#F0F0F0' : '#FFFFFF');
    tr.onclick = function () {
        google.maps.event.trigger(markers[i], 'click');
    };

    var iconTd = document.createElement('td');
    var nameTd = document.createElement('td');
    var icon = document.createElement('img');
    icon.src = markerIcon;
    icon.setAttribute('class', 'placeIcon');
    icon.setAttribute('className', 'placeIcon');
    var name = document.createTextNode(result.name);
    iconTd.appendChild(icon);
    nameTd.appendChild(name);
    tr.appendChild(iconTd);
    tr.appendChild(nameTd);
    results.appendChild(tr);
}

/**
 * Clears results
 */
function clearResults() {
    var results = document.getElementById('results');
    while (results.childNodes[0]) {
        results.removeChild(results.childNodes[0]);
    }
}

/**
 * Creates info window when a user clicks on a marker
 */
function showInfoWindow() {
    var marker = this;
    places.getDetails({placeId: marker.placeResult.place_id},
            function (place, status) {
                if (status !== google.maps.places.PlacesServiceStatus.OK) {
                    return;
                }
                infoWindow.open(map, marker);
                document.getElementById('iw-icon').innerHTML = '<img class="centerIcon" ' +
                        'src="' + place.icon + '"/>';
                document.getElementById('iw-url').innerHTML = '<b><a href="' + place.url +
                        '">' + place.name + '</a></b>';
                document.getElementById('iw-address').textContent = place.vicinity;

                if (place.formatted_phone_number) {
                    document.getElementById('iw-phone-row').style.display = '';
                    document.getElementById('iw-phone').textContent =
                            place.formatted_phone_number;
                } else {
                    document.getElementById('iw-phone-row').style.display = 'none';
                }
                //Gets ratings
                if (place.rating) {
                    var ratingHtml = '';
                    for (var i = 0; i < 5; i++) {
                        if (place.rating < (i + 0.5)) {
                            ratingHtml += '&#10025;';
                        } else {
                            ratingHtml += '&#10029;';
                        }
                        document.getElementById('iw-rating-row').style.display = '';
                        document.getElementById('iw-rating').innerHTML = ratingHtml;
                    }
                } else {
                    document.getElementById('iw-rating-row').style.display = 'none';
                }

                //Gets a shortened version of the url
                if (place.website) {
                    var fullUrl = place.website;
                    // var hostnameRegexp = new RegExp('^https?://.+?/');
                    var website = RegExp('^https?://.+?/').exec(place.website);
                    if (website === null) {
                        website = 'http://' + place.website + '/';
                        fullUrl = website;
                    }
                    document.getElementById('iw-website-row').style.display = '';
                    document.getElementById('iw-website').textContent = website;
                } else {
                    document.getElementById('iw-website-row').style.display = 'none';
                }
            });
}