const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');

const indexRouter = require('./routes/index');
const authRouter = require('./routes/auth');
const testRouter = require('./routes/test');
const artilceRouter = require('./routes/article');
const profileRouter = require('./routes/profile');
const historyRouter = require('./routes/history');

const URL = "/api/v1";

const app = express();

var cors = require('cors');

app.use(cors());
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
app.use(URL, historyRouter);

module.exports = app;
