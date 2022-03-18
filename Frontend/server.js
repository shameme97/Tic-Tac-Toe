var express = require('express');
var path = require('path');
var serveStatic = require('serve-static');
app = express();
app.use(serveStatic(__dirname + "/dist"));
var port = process.env.PORT || 8080;
app.listen(port);
console.log('server started '+ port);

// const http = require('http');
// const express = require('express');
// const path = require('path');
// const app = express();
// app.use(express.json());
// app.use(express.static("src"));

// app.use('/', function(req,res){
//     res.sendFile(path.join(__dirname+'/dist'));
//   });
// const server = http.createServer(app);

// var port = process.env.PORT || 8080;
// app.listen(port);
// console.log('server started '+ port);