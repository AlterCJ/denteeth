const express = require('express');
const router = express.Router();
const { addHistory,getAllHistory } = require('../controllers/historyController');
const { auth } = require('../middleware/auth');

router.post('/history',auth, addHistory);
router.get('/history',auth, getAllHistory);

module.exports = router;