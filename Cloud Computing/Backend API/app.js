const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');

const indexRouter = require('./routes/index');
const authRouter = require('./routes/auth');
const testRouter = require('./routes/test');
const artilceRouter = require('./routes/article');
const profileRouter = require('./routes/profile');
const bennerRouter = require('./routes/benner');

const URL = "/api/v1";

const app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use(URL, authRouter);
app.use(URL, testRouter);
app.use(URL, artilceRouter);
app.use(URL, profileRouter);
app.use(URL, bennerRouter);

module.exports = app;