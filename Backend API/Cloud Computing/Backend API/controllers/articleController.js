const { Article } = require('../db/models');

module.exports = {
  getAllArticle: async(req, res, next) => {
    try {
      let limit = parseInt(req.query.record);
      let page = parseInt(req.query.page);
      let start = 0 + (page - 1) * limit;
      let end = page * limit;
      const article = await Article.findAndCountAll({
        limit: limit,
        offset: start
      });

      let countFiltered = article.count;
      let pagination = {}
      pagination.totalRow = article.count;
      pagination.totalPage = Math.ceil(countFiltered / limit);
      if(end < countFiltered) {
        pagination.next = {
          page: page + 1,
          limit
        }
      }

      if(start > 0){
        pagination.prev = {
          page: page - 1,
          limit
        }
      }
      

      res.status(200).json(article.rows)
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(null);
    }
  },

  getListAllArticle: async(req, res, next) => {
    try {
      const article = await Article.findAll();
      res.status(200).json({
        error: false,
        message: 'ok',
        data: article
      })
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(null);
    }
  },

  addArticle: async(req, res, next) => {
    try {
      const { image, title, description, detail } = req.body;
      const article = await Article.create({
        image, title, description, detail
      });
      res.status(200).json({
        error: false,
        message: 'ok',
        data: article
      })
    } catch (error) {
      res.status(500).json({
        error: true,
        message: `error ${error}`,
        data: null
      });
      next(null);
    }
  },

  getDetailArticle: async(req,res, next) => {
    try {
      const { id } = req.params;
      const checkId = await Article.findOne({
        where: { id : id}
      });
      if(checkId) {
        res.status(200).json({
          error: false,
          message: `success get data`,
          data: checkId
        });
      }else{
        res.status(404).json({
          error: true,
          message: 'data not found',
          data: null
        })
      }
    } catch (error) {
      res.status(500).json({
        error: `error ${error}`,
        message: 'failed get data',
        data: null
      });
      next(error);
    }
  }
}