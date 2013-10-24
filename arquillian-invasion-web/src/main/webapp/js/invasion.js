	var map;
	var geocoder;
	var mapMarkers = [];
	geocoder = new google.maps.Geocoder();
	function initialize() {

		var mapOptions = {
		    zoom: 4,
		    center: new google.maps.LatLng(40, -92),
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		  };
		  map = new google.maps.Map(document.getElementById('map-canvas'),
		      mapOptions);
		  loadData();
		}

		function loadData() {
			$.getJSON('/arquillian-invasion-services/rest/sighting', {
				format: "json"
			})
			.done( function(data){
				$("#stats").html("There have been "+data.length + " sightings reported");
				
				removeMarkers();
				
				$.each(data, function(key, val) {
					geocoder.geocode( { 'address': val.zip}, function(results, status) {
					    if (status == google.maps.GeocoderStatus.OK) {
					      //map.setCenter(results[0].geometry.location);
					      var marker = new google.maps.Marker({
					          map: map,
					          icon: 'img/arquillianLogo.png',
					          position: results[0].geometry.location,
					          optimized: false,
					          customInfo: val
					      });
					      mapMarkers.push(marker);
					      
					      var reportDate = new Date(val.dateReported);
					      var reportDateFormatted = (reportDate.getMonth()+1) + "/" + reportDate.getDate() + "/" + reportDate.getFullYear() + " " + (reportDate.getHours() < 10 ? "0" + reportDate.getHours() : reportDate.getHours()) + ":" + (reportDate.getMinutes() < 10 ? "0" + reportDate.getMinutes() : reportDate.getMinutes()) + ":" + (reportDate.getSeconds() < 10 ? "0" + reportDate.getSeconds() : reportDate.getSeconds());
					      
					      var contentString = '<div id="content" style="height:150px;width:250px"><table><tr><td><b>Description</b></td><td>' +val.description + '</td></tr><tr><td><b>Reported By</b></td><td>'+ val.reporter + '</td><tr><td><b>Date Reported</b></td><td>'+ reportDateFormatted + '</td></tr></table><br/><br><a href="#" class="deleteSighting" data-id="'+val.id+'">Delete</a></div>';    
					      var infowindow = new google.maps.InfoWindow({
					          content: contentString
					      });

					      google.maps.event.addListener(marker, 'click', function() {
					    	    infowindow.open(map,marker);
					      });
					      
					      
					    }
					});			

				});
				})
			.fail(function( jqxhr, textStatus, error ) {
				var err = textStatus + ", " + error;
				console.log( "Request Failed: " + err );
			});
		}
		
		function removeMarkers() {
			if(mapMarkers && mapMarkers.length !== 0){
	            for(var i = 0; i < mapMarkers.length; ++i){
	            	mapMarkers[i].setMap(null);
	            }
	        }
		}
		
		google.maps.event.addDomListener(window, 'load', initialize);


$(function() {
	
	$("#map-canvas").on("click", ".deleteSighting", function() {
		var id = $(this).data("id");
		var deleteUrl = '/arquillian-invasion-services/rest/sighting/id/'+id;
		
		$.ajax({
			type: 'DELETE',
			url: deleteUrl,
			success: function(data) { 
				loadData();
			},
			error: function(xhr, textStatus, errorThrown) { 
				alert("Delete Failed: "+errorThrown);
			},
		});
	});
		
	$("#invasionForm").submit(
			function(event) {

				event.preventDefault();
				
				var $form = $(this), url = $form.attr('action');

				$('input[type="submit"]').attr('disabled','disabled');
				
				var errors = false;
				
				var descriptionTxt = $('#descriptionTxt').val();
				var reporterTxt = $('#reporterTxt').val();
				var zipTxt = $('#zipTxt').val();
				
				if(descriptionTxt.length==0) {
					$('#descriptionError').text("Description is Required");
					errors = true;
				}
				else {
					$('#descriptionError').text("");
				}
	
				if(reporterTxt.length==0) {
					$('#reporterError').text("Reporter is Required");
					errors = true;
				}
				else {
					$('#reporterError').text("");
				}
				
				if(zipTxt.length==0) {
					$('#zipError').text("Zip Code is Required");
					errors = true;
				}
				else {
					$('#zipError').text("");
				}
				
				
				if(errors) {
					$('input[type="submit"]').removeAttr('disabled');
					return;
				}
				
				

				$.ajax({
					type: 'POST',
					url: url,
					data: JSON.stringify({reporter: reporterTxt,description: descriptionTxt,zip: zipTxt}),
					success: function(data) { 
						$('#reporterTxt').val('');
						$('#descriptionTxt').val('');
						$('#zipTxt').val('');
				        $('input[type="submit"]').removeAttr('disabled');
				        loadData();
					},
					error: function(xhr, textStatus, errorThrown) { 
				        $('input[type="submit"]').removeAttr('disabled');
				        alert("Submission Failed: "+errorThrown);
					},
					contentType: "application/json",
				});
			});
	
});