const express = require('express');
const router = express.Router();
const { getAllTest, addTest } = require('../controllers/TestController');
const { auth } = require('../middleware/auth');
const { uploadMiddleware } = require('../middleware/mullter');

router.get('/test', auth, getAllTest)
router.post('/test', auth, uploadMiddleware.single('image') ,addTest);

module.exports = router;
