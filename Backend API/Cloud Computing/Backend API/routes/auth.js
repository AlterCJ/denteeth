const express = require('express');
const router = express.Router();
const { signIn, signUp } = require('../controllers/AuthController');

router.post('/auth/signin',signIn);
router.post('/auth/signup',signUp);

module.exports = router;
