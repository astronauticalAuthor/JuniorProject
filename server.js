var express = require('express')
var app = express()
var path = require('path')
var fs = require('fs')
var exec = require('child_process').exec
var bodyParser = require('body-parser')

app.use(bodyParser.urlencoded({extended: true}))
app.use(bodyParser.json())

app.use(express.static('static'))

app.get('/', function(req, res) {
	res.sendFile(path.join(__dirname + '/index.html'))
})

app.get('/analyze', function(req, res) {
	var cmd = 'java -jar DesignParser.jar'
	exec(cmd, function(err, stdout, stderr) {
		if (err) throw err
		
		fs.readFile('./input_output/JSONoutput.txt', function(err, data) {
			if (err) throw err

			console.log(data)
			res.writeHead(200, {"Content-Type": "text/plain"})
			res.write(data)

			cmd = 'C:/\"Program Files (x86)\"/Graphviz2.38/bin/dot.exe input_output/outputUML.dot -o UMLimage.png'
			exec(cmd, function(err, stdout, stderr) {
				if (err) throw err

				res.sendFile(path.join(__dirname + '/UMLimage.png'))
				res.end()
			})
		})
	})
})

app.get('/config', function(req, res) {
	//call the config creation executable
	console.log('Made it to the config server API endpoint')
	var cmd = 'java -jar Config.jar originalConfig.txt'
	exec(cmd, function(err, stdout, stderr) {
		if (err) throw err

		res.end()
	})
})

app.post('/updateUML', function(req, res) {
	console.log("Hit the updateUML API endpoint")
	var cmd = 'java -jar DesignParser.jar'
	exec(cmd, function(err, stdout, stderr) {
		if (err) throw err

		res.end()
	})
})

app.listen(8080)
