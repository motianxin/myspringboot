var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');

/*var crypto = require('crypto');
// console.log(crypto.getCiphers());

var data = "123456789012";
console.log('Original cleartext: ' + data);
var algorithm = 'aes-128-gcm';
var key = '1234567890123456';
var clearEncoding = 'utf8';
var iv = "1234567890123456";
//var cipherEncoding = 'hex';
//If the next line is uncommented, the final cleartext is wrong.
var cipherEncoding = 'base64';
var cipher = crypto.createCipheriv(algorithm, key, iv);
var cipherChunks = [];
cipherChunks.push(cipher.update(data, clearEncoding, cipherEncoding));
cipherChunks.push(cipher.final(cipherEncoding));
console.log(cipherEncoding + ' ciphertext: ' + cipherChunks.join(''));

var decipher = crypto.createDecipheriv(algorithm, key, iv);
var plainChunks = [];
plainChunks.push(decipher.update(cipherChunks.join(''), cipherEncoding, clearEncoding));

plainChunks.push(decipher.final(clearEncoding));
console.log("UTF8 plaintext deciphered: " + plainChunks.join(''));*/


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error');
});

module.exports = app;
