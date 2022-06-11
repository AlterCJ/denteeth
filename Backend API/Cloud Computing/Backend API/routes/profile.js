const express = require('express');
const router = express.Router();
const { getDetailProfile, createProfile, updateProfile } = require('../controllers/profileController');
const { auth } = require('../middleware/auth');

router.get('/profile',auth, getDetailProfile);
router.post('/profile', auth, createProfile);
router.put('/profile', auth, updateProfile);

module.exports = router;
