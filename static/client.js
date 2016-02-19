function updateUML() {
	var toSend = []
	$('.cb').map(function() {
		if ($(this).is(':checked')) {
			toSend.push($(this).val().trim())
		}
	})

	$.ajax({
		type: 'POST',
		url: '/updateUML',
		data: {"data": toSend.join("\n")}
		success: function(data) {
			refreshPicture()
		}
	})
}

function analyze() {
	$.ajax({
		type: 'GET',
		url: '/analyze',
		success: function(data) {
			var parsedData = JSON.parse(data)
			setupCheckboxes(parsedData)
			var arr = parsedData.Singleton + ' ' + parsedData.Decorator + ' ' + parsedData.Adapter + ' ' + parsedData.Composite
			// runLoadingBar(arr.split(' '))
			$('#setupScreen').addClass('inactive')
			$('#setupScreen').removeClass('setHeightSetup')
			$('#mainScreen').removeClass('inactive')
		}
	})
}

function setupCheckboxes(classes) {
	$('#classSelection').append('Singleton<br />')
	classes.Singleton.split(' ').forEach(function(str) {
			$('#classSelection').append('<input type="checkbox" class="cb" value="' + str + '" checked />' + str + '<br />')
	})

	$('#classSelection').append('Decorator<br />')
	classes.Decorator.split(' ').forEach(function(str) {
		$('#classSelection').append('<input type="checkbox" class="cb" value="' + str + '" checked />' + str + '<br />')
	})

	$('#classSelection').append('Adapter<br />')
	classes.Adapter.split(' ').forEach(function(str) {
		$('#classSelection').append('<input type="checkbox" class="cb" value="' + str + '" checked />' + str + '<br />')
	})

	$('#classSelection').append('Composite<br />')
	classes.Composite.split(' ').forEach(function(str) {
		$('#classSelection').append('<input type="checkbox" class="cb" value="' + str + '" checked />' + str + '<br />')
	})
}

function refreshPicture() {
	$('#pictureArea').empty()
	$('#pictureArea').append('<img src="UMLimage.png" />')
}

function config() {
	$.ajax({
		type: 'GET',
		url: '/config'
	})
}

function runLoadingBar(arr) {
	console.log(arr)
	var timeEach = 1000 / arr.length

	for (var i = 0; i < arr.length; i++) {
		console.log(arr[i])
		// $('#loadingBarText').empty()
		// $('#loadingBarText').append('<div>' + arr[i] + '</div>')
		$('#lbf').css('width', 10 * i + 'px')
		sleep(500)
	}
}

function sleep(delay) {
	var start = new Date().getTime();
	while (new Date().getTime() < start + delay);
}