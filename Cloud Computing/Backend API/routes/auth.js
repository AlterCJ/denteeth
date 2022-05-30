const express = require('express');
const router = express.Router();
const { signIn, singUp } = require('../controllers/AuthController');

router.post('/auth/signin',signIn);
router.post('/auth/signup',singUp);

module.exports = router;
