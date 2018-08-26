var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var session = require('express-session');
var socket_io = require('socket.io');
var SocketHandler = require('./socket/chatSocket');

var indexRouter = require('./routes/index');
var signRouter = require('./routes/signInUp');

var app = express();
var io = socket_io();

app.io = io;

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use('/public', express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'views')));

app.use(session({
  secret: 'testSecret',
  resave: false,
  saveUninitialized: true
}));

app.use('/', indexRouter);
app.use('/signInUp', signRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

app.io.on('connection', async function(socket){
  console.log('A user connected');

  socketHandler = new SocketHandler();
  socketHandler.connect();

  socket.on('disconnect', function(){
    console.log('A user disconnect');
  });

  socket.on('chat message', function(data){
    socketHandler.storeMessages(data);
    app.io.emit('chat message', data);
  });

});

module.exports = app;
