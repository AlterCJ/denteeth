const express = require('express');
const router = express.Router();
const { getAllArticle, addArticle, getDetailArticle, getListAllArticle } = require('../controllers/articleController');

router.get('/article', getAllArticle);
router.get('/article/list', getListAllArticle);
router.post('/article', addArticle);
router.get('/article/:id', getDetailArticle);

module.exports = router;
