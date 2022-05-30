const express = require('express');
const router = express.Router();
const { getBenners, addBanners, updateBenners, deleteBenners } = require('../controllers/bennerController');

router.get('/benners', getBenners);
router.post('/benners', addBanners);
router.put('/benners', updateBenners);
router.delete('/benners', deleteBenners);

module.exports = router;
